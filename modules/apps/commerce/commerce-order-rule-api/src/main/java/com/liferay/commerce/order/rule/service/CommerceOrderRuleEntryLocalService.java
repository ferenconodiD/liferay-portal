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

package com.liferay.commerce.order.rule.service;

import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for CommerceOrderRuleEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Luca Pellizzon
 * @see CommerceOrderRuleEntryLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommerceOrderRuleEntryLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.order.rule.service.impl.CommerceOrderRuleEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce order rule entry local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommerceOrderRuleEntryLocalServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Adds the commerce order rule entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderRuleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderRuleEntry the commerce order rule entry
	 * @return the commerce order rule entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public CommerceOrderRuleEntry addCommerceOrderRuleEntry(
		CommerceOrderRuleEntry commerceOrderRuleEntry);

	@Indexable(type = IndexableType.REINDEX)
	public CommerceOrderRuleEntry addCommerceOrderRuleEntry(
			String externalReferenceCode, long userId, boolean active,
			String description, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, String name,
			int priority, String type, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException;

	public void checkCommerceOrderRuleEntries() throws PortalException;

	/**
	 * Creates a new commerce order rule entry with the primary key. Does not add the commerce order rule entry to the database.
	 *
	 * @param commerceOrderRuleEntryId the primary key for the new commerce order rule entry
	 * @return the new commerce order rule entry
	 */
	@Transactional(enabled = false)
	public CommerceOrderRuleEntry createCommerceOrderRuleEntry(
		long commerceOrderRuleEntryId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Deletes the commerce order rule entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderRuleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderRuleEntry the commerce order rule entry
	 * @return the commerce order rule entry that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceOrderRuleEntry deleteCommerceOrderRuleEntry(
			CommerceOrderRuleEntry commerceOrderRuleEntry)
		throws PortalException;

	/**
	 * Deletes the commerce order rule entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderRuleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderRuleEntryId the primary key of the commerce order rule entry
	 * @return the commerce order rule entry that was removed
	 * @throws PortalException if a commerce order rule entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public CommerceOrderRuleEntry deleteCommerceOrderRuleEntry(
			long commerceOrderRuleEntryId)
		throws PortalException;

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> T dslQuery(DSLQuery dslQuery);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int dslQueryCount(DSLQuery dslQuery);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DynamicQuery dynamicQuery();

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.order.rule.model.impl.CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end);

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.order.rule.model.impl.CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrderRuleEntry fetchCommerceOrderRuleEntry(
		long commerceOrderRuleEntryId);

	/**
	 * Returns the commerce order rule entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce order rule entry's external reference code
	 * @return the matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrderRuleEntry
		fetchCommerceOrderRuleEntryByExternalReferenceCode(
			long companyId, String externalReferenceCode);

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchCommerceOrderRuleEntryByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrderRuleEntry fetchCommerceOrderRuleEntryByReferenceCode(
		long companyId, String externalReferenceCode);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrderRuleEntry>
		getAccountEntryAndCommerceChannelAndCommerceOrderTypeCORuleEntries(
			long companyId, long accountEntryId, long commerceChannelId,
			long commerceOrderTypeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrderRuleEntry>
		getAccountEntryAndCommerceChannelCORuleEntries(
			long companyId, long accountEntryId, long commerceChannelId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrderRuleEntry>
		getAccountEntryAndCommerceOrderTypeCORuleEntries(
			long companyId, long accountEntryId, long commerceOrderTypeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrderRuleEntry> getAccountEntryCORuleEntries(
		long companyId, long accountEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrderRuleEntry>
		getAccountGroupsAndCommerceChannelAndCommerceOrderTypeCORuleEntries(
			long companyId, long[] accountGroupIds, long commerceChannelId,
			long commerceOrderTypeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrderRuleEntry>
		getAccountGroupsAndCommerceChannelCORuleEntries(
			long companyId, long[] accountGroupIds, long commerceChannelId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrderRuleEntry>
		getAccountGroupsAndCommerceOrderTypeCORuleEntries(
			long companyId, long[] accountGroupIds, long commerceOrderTypeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrderRuleEntry> getAccountGroupsCORuleEntries(
		long companyId, long[] accountGroupIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrderRuleEntry>
		getCommerceChannelAndCommerceOrderTypeCORuleEntries(
			long companyId, long commerceChannelId, long commerceOrderTypeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrderRuleEntry> getCommerceChannelCORuleEntries(
		long companyId, long commerceChannelId);

	/**
	 * Returns a range of all the commerce order rule entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.order.rule.model.impl.CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of commerce order rule entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrderRuleEntry> getCommerceOrderRuleEntries(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrderRuleEntry> getCommerceOrderRuleEntries(
		long companyId, boolean active, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrderRuleEntry> getCommerceOrderRuleEntries(
		long companyId, boolean active, String type, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrderRuleEntry> getCommerceOrderRuleEntries(
		long companyId, String type, int start, int end);

	/**
	 * Returns the number of commerce order rule entries.
	 *
	 * @return the number of commerce order rule entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceOrderRuleEntriesCount();

	/**
	 * Returns the commerce order rule entry with the primary key.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the commerce order rule entry
	 * @return the commerce order rule entry
	 * @throws PortalException if a commerce order rule entry with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrderRuleEntry getCommerceOrderRuleEntry(
			long commerceOrderRuleEntryId)
		throws PortalException;

	/**
	 * Returns the commerce order rule entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce order rule entry's external reference code
	 * @return the matching commerce order rule entry
	 * @throws PortalException if a matching commerce order rule entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrderRuleEntry
			getCommerceOrderRuleEntryByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrderRuleEntry> getCommerceOrderTypeCORuleEntries(
		long companyId, long commerceOrderTypeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * @throws PortalException
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Updates the commerce order rule entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderRuleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderRuleEntry the commerce order rule entry
	 * @return the commerce order rule entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public CommerceOrderRuleEntry updateCommerceOrderRuleEntry(
		CommerceOrderRuleEntry commerceOrderRuleEntry);

	@Indexable(type = IndexableType.REINDEX)
	public CommerceOrderRuleEntry updateCommerceOrderRuleEntry(
			long userId, long commerceOrderRuleEntryId, boolean active,
			String description, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, String name,
			int priority, String typeSettings, ServiceContext serviceContext)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommerceOrderRuleEntry updateStatus(
			long userId, long commerceOrderRuleEntryId, int status,
			ServiceContext serviceContext)
		throws PortalException;

}