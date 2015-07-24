/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.device.rules.lar;

import com.liferay.mobile.device.rules.constants.MDRPortletKeys;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Layout;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.exportimport.lar.BasePortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerBoolean;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelType;
import com.liferay.portlet.exportimport.xstream.XStreamAliasRegistryUtil;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.model.impl.MDRActionImpl;
import com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupImpl;
import com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceImpl;
import com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleImpl;
import com.liferay.portlet.mobiledevicerules.service.MDRActionLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.permission.MDRPermission;

import java.util.List;

import javax.portlet.PortletPreferences;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + MDRPortletKeys.MOBILE_DEVICE_SITE_ADMIN
	},
	service = PortletDataHandler.class
)
public class MDRPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "mobile_device_rules";

	@Activate
	protected void activate() {
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(MDRAction.class, Layout.class),
			new StagedModelType(MDRRule.class),
			new StagedModelType(MDRRuleGroup.class),
			new StagedModelType(MDRRuleGroupInstance.class, Layout.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "rules", true, false, null, MDRRule.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "actions", true, false, null,
				MDRAction.class.getName(), Layout.class.getName()));
		setImportControls(getExportControls());
		setPublishToLiveByDefault(
			PropsValues.MOBILE_DEVICE_RULES_PUBLISH_TO_LIVE_BY_DEFAULT);

		XStreamAliasRegistryUtil.register(MDRActionImpl.class, "MDRAction");
		XStreamAliasRegistryUtil.register(MDRRuleImpl.class, "MDRRule");
		XStreamAliasRegistryUtil.register(
			MDRRuleGroupImpl.class, "MDRRuleGroup");
		XStreamAliasRegistryUtil.register(
			MDRRuleGroupInstanceImpl.class, "MDRRuleGroupInstance");
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				MDRPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		MDRRuleGroupLocalServiceUtil.deleteRuleGroups(
			portletDataContext.getGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortletPermissions(MDRPermission.RESOURCE_NAME);

		Element rootElement = addExportDataRootElement(portletDataContext);

		if (portletDataContext.getBooleanParameter(NAMESPACE, "rules")) {
			ActionableDynamicQuery rulesActionableDynamicQuery =
				MDRRuleLocalServiceUtil.getExportActionableDynamicQuery(
					portletDataContext);

			rulesActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "actions")) {
			ExportActionableDynamicQuery actionsExportActionableDynamicQuery =
				MDRActionLocalServiceUtil.getExportActionableDynamicQuery(
					portletDataContext);

			actionsExportActionableDynamicQuery.setStagedModelType(
				new StagedModelType(
					PortalUtil.getClassNameId(MDRAction.class),
					StagedModelType.REFERRER_CLASS_NAME_ID_ALL));

			actionsExportActionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortletPermissions(
			MDRPermission.RESOURCE_NAME);

		if (portletDataContext.getBooleanParameter(NAMESPACE, "rules")) {
			Element rulesElement = portletDataContext.getImportDataGroupElement(
				MDRRule.class);

			List<Element> ruleElements = rulesElement.elements();

			for (Element ruleElement : ruleElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, ruleElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "actions")) {
			Element actionsElement =
				portletDataContext.getImportDataGroupElement(MDRAction.class);

			List<Element> actionElements = actionsElement.elements();

			for (Element actionElement : actionElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, actionElement);
			}
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ExportActionableDynamicQuery actionsExportActionableDynamicQuery =
			MDRActionLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		actionsExportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				MDRAction.class.getName(), Layout.class.getName()));

		actionsExportActionableDynamicQuery.performCount();

		ActionableDynamicQuery rulesActionableDynamicQuery =
			MDRRuleLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		rulesActionableDynamicQuery.performCount();

		ActionableDynamicQuery ruleGroupsActionableDynamicQuery =
			MDRRuleGroupLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		ruleGroupsActionableDynamicQuery.performCount();

		ExportActionableDynamicQuery
			ruleGroupInstancesExportActionableDynamicQuery =
				MDRRuleGroupInstanceLocalServiceUtil.
					getExportActionableDynamicQuery(portletDataContext);

		ruleGroupInstancesExportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				MDRRuleGroupInstance.class.getName(), Layout.class.getName()));

		ruleGroupInstancesExportActionableDynamicQuery.performCount();
	}

	@Reference(target = "(original.bean=*)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
	}

}