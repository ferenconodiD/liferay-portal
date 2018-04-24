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

package com.liferay.wsrp.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
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

import com.liferay.wsrp.model.WSRPProducer;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for WSRPProducer. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see WSRPProducerLocalServiceUtil
 * @see com.liferay.wsrp.service.base.WSRPProducerLocalServiceBaseImpl
 * @see com.liferay.wsrp.service.impl.WSRPProducerLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface WSRPProducerLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WSRPProducerLocalServiceUtil} to access the wsrp producer local service. Add custom service methods to {@link com.liferay.wsrp.service.impl.WSRPProducerLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public WSRPProducer addWSRPProducer(long userId, long groupId,
		java.lang.String name, java.lang.String version,
		java.lang.String portletIds, ServiceContext serviceContext)
		throws PortalException;

	public WSRPProducer addWSRPProducer(long userId, java.lang.String name,
		java.lang.String version, java.lang.String portletIds,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Adds the wsrp producer to the database. Also notifies the appropriate model listeners.
	*
	* @param wsrpProducer the wsrp producer
	* @return the wsrp producer that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public WSRPProducer addWSRPProducer(WSRPProducer wsrpProducer);

	/**
	* Creates a new wsrp producer with the primary key. Does not add the wsrp producer to the database.
	*
	* @param wsrpProducerId the primary key for the new wsrp producer
	* @return the new wsrp producer
	*/
	@Transactional(enabled = false)
	public WSRPProducer createWSRPProducer(long wsrpProducerId);

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	/**
	* Deletes the wsrp producer with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param wsrpProducerId the primary key of the wsrp producer
	* @return the wsrp producer that was removed
	* @throws PortalException if a wsrp producer with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public WSRPProducer deleteWSRPProducer(long wsrpProducerId)
		throws PortalException;

	/**
	* Deletes the wsrp producer from the database. Also notifies the appropriate model listeners.
	*
	* @param wsrpProducer the wsrp producer
	* @return the wsrp producer that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(action = SystemEventConstants.ACTION_SKIP, type = SystemEventConstants.TYPE_DELETE)
	public WSRPProducer deleteWSRPProducer(WSRPProducer wsrpProducer)
		throws PortalException;

	public void deleteWSRPProducers(long companyId) throws PortalException;

	public DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wsrp.model.impl.WSRPProducerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wsrp.model.impl.WSRPProducerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WSRPProducer fetchWSRPProducer(long wsrpProducerId);

	/**
	* Returns the wsrp producer matching the UUID and group.
	*
	* @param uuid the wsrp producer's UUID
	* @param groupId the primary key of the group
	* @return the matching wsrp producer, or <code>null</code> if a matching wsrp producer could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WSRPProducer fetchWSRPProducerByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	* Returns the wsrp producer with the primary key.
	*
	* @param wsrpProducerId the primary key of the wsrp producer
	* @return the wsrp producer
	* @throws PortalException if a wsrp producer with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WSRPProducer getWSRPProducer(long wsrpProducerId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WSRPProducer getWSRPProducer(java.lang.String wsrpProducerUuid)
		throws PortalException;

	/**
	* Returns the wsrp producer matching the UUID and group.
	*
	* @param uuid the wsrp producer's UUID
	* @param groupId the primary key of the group
	* @return the matching wsrp producer
	* @throws PortalException if a matching wsrp producer could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WSRPProducer getWSRPProducerByUuidAndGroupId(java.lang.String uuid,
		long groupId) throws PortalException;

	/**
	* Returns a range of all the wsrp producers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wsrp.model.impl.WSRPProducerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wsrp producers
	* @param end the upper bound of the range of wsrp producers (not inclusive)
	* @return the range of wsrp producers
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WSRPProducer> getWSRPProducers(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WSRPProducer> getWSRPProducers(long companyId, int start,
		int end);

	/**
	* Returns all the wsrp producers matching the UUID and company.
	*
	* @param uuid the UUID of the wsrp producers
	* @param companyId the primary key of the company
	* @return the matching wsrp producers, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WSRPProducer> getWSRPProducersByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of wsrp producers matching the UUID and company.
	*
	* @param uuid the UUID of the wsrp producers
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of wsrp producers
	* @param end the upper bound of the range of wsrp producers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching wsrp producers, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WSRPProducer> getWSRPProducersByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<WSRPProducer> orderByComparator);

	/**
	* Returns the number of wsrp producers.
	*
	* @return the number of wsrp producers
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getWSRPProducersCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getWSRPProducersCount(long companyId);

	public WSRPProducer updateWSRPProducer(long wsrpProducerId,
		java.lang.String name, java.lang.String version,
		java.lang.String portletIds) throws PortalException;

	/**
	* Updates the wsrp producer in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param wsrpProducer the wsrp producer
	* @return the wsrp producer that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public WSRPProducer updateWSRPProducer(WSRPProducer wsrpProducer);
}