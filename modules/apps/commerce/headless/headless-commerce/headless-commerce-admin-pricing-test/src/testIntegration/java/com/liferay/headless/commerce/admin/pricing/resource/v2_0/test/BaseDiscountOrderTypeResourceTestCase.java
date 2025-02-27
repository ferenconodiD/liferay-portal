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

package com.liferay.headless.commerce.admin.pricing.resource.v2_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.DiscountOrderType;
import com.liferay.headless.commerce.admin.pricing.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Page;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.DiscountOrderTypeResource;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.DiscountOrderTypeSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public abstract class BaseDiscountOrderTypeResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_discountOrderTypeResource.setContextCompany(testCompany);

		DiscountOrderTypeResource.Builder builder =
			DiscountOrderTypeResource.builder();

		discountOrderTypeResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		DiscountOrderType discountOrderType1 = randomDiscountOrderType();

		String json = objectMapper.writeValueAsString(discountOrderType1);

		DiscountOrderType discountOrderType2 = DiscountOrderTypeSerDes.toDTO(
			json);

		Assert.assertTrue(equals(discountOrderType1, discountOrderType2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		DiscountOrderType discountOrderType = randomDiscountOrderType();

		String json1 = objectMapper.writeValueAsString(discountOrderType);
		String json2 = DiscountOrderTypeSerDes.toJSON(discountOrderType);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		DiscountOrderType discountOrderType = randomDiscountOrderType();

		discountOrderType.setDiscountExternalReferenceCode(regex);
		discountOrderType.setOrderTypeExternalReferenceCode(regex);

		String json = DiscountOrderTypeSerDes.toJSON(discountOrderType);

		Assert.assertFalse(json.contains(regex));

		discountOrderType = DiscountOrderTypeSerDes.toDTO(json);

		Assert.assertEquals(
			regex, discountOrderType.getDiscountExternalReferenceCode());
		Assert.assertEquals(
			regex, discountOrderType.getOrderTypeExternalReferenceCode());
	}

	@Test
	public void testDeleteDiscountOrderType() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLDeleteDiscountOrderType() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetDiscountByExternalReferenceCodeDiscountOrderTypesPage()
		throws Exception {

		String externalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountOrderTypesPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountOrderTypesPage_getIrrelevantExternalReferenceCode();

		Page<DiscountOrderType> page =
			discountOrderTypeResource.
				getDiscountByExternalReferenceCodeDiscountOrderTypesPage(
					externalReferenceCode, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantExternalReferenceCode != null) {
			DiscountOrderType irrelevantDiscountOrderType =
				testGetDiscountByExternalReferenceCodeDiscountOrderTypesPage_addDiscountOrderType(
					irrelevantExternalReferenceCode,
					randomIrrelevantDiscountOrderType());

			page =
				discountOrderTypeResource.
					getDiscountByExternalReferenceCodeDiscountOrderTypesPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscountOrderType),
				(List<DiscountOrderType>)page.getItems());
			assertValid(page);
		}

		DiscountOrderType discountOrderType1 =
			testGetDiscountByExternalReferenceCodeDiscountOrderTypesPage_addDiscountOrderType(
				externalReferenceCode, randomDiscountOrderType());

		DiscountOrderType discountOrderType2 =
			testGetDiscountByExternalReferenceCodeDiscountOrderTypesPage_addDiscountOrderType(
				externalReferenceCode, randomDiscountOrderType());

		page =
			discountOrderTypeResource.
				getDiscountByExternalReferenceCodeDiscountOrderTypesPage(
					externalReferenceCode, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discountOrderType1, discountOrderType2),
			(List<DiscountOrderType>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetDiscountByExternalReferenceCodeDiscountOrderTypesPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountOrderTypesPage_getExternalReferenceCode();

		DiscountOrderType discountOrderType1 =
			testGetDiscountByExternalReferenceCodeDiscountOrderTypesPage_addDiscountOrderType(
				externalReferenceCode, randomDiscountOrderType());

		DiscountOrderType discountOrderType2 =
			testGetDiscountByExternalReferenceCodeDiscountOrderTypesPage_addDiscountOrderType(
				externalReferenceCode, randomDiscountOrderType());

		DiscountOrderType discountOrderType3 =
			testGetDiscountByExternalReferenceCodeDiscountOrderTypesPage_addDiscountOrderType(
				externalReferenceCode, randomDiscountOrderType());

		Page<DiscountOrderType> page1 =
			discountOrderTypeResource.
				getDiscountByExternalReferenceCodeDiscountOrderTypesPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<DiscountOrderType> discountOrderTypes1 =
			(List<DiscountOrderType>)page1.getItems();

		Assert.assertEquals(
			discountOrderTypes1.toString(), 2, discountOrderTypes1.size());

		Page<DiscountOrderType> page2 =
			discountOrderTypeResource.
				getDiscountByExternalReferenceCodeDiscountOrderTypesPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscountOrderType> discountOrderTypes2 =
			(List<DiscountOrderType>)page2.getItems();

		Assert.assertEquals(
			discountOrderTypes2.toString(), 1, discountOrderTypes2.size());

		Page<DiscountOrderType> page3 =
			discountOrderTypeResource.
				getDiscountByExternalReferenceCodeDiscountOrderTypesPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				discountOrderType1, discountOrderType2, discountOrderType3),
			(List<DiscountOrderType>)page3.getItems());
	}

	protected DiscountOrderType
			testGetDiscountByExternalReferenceCodeDiscountOrderTypesPage_addDiscountOrderType(
				String externalReferenceCode,
				DiscountOrderType discountOrderType)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetDiscountByExternalReferenceCodeDiscountOrderTypesPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetDiscountByExternalReferenceCodeDiscountOrderTypesPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDiscountByExternalReferenceCodeDiscountOrderType()
		throws Exception {

		DiscountOrderType randomDiscountOrderType = randomDiscountOrderType();

		DiscountOrderType postDiscountOrderType =
			testPostDiscountByExternalReferenceCodeDiscountOrderType_addDiscountOrderType(
				randomDiscountOrderType);

		assertEquals(randomDiscountOrderType, postDiscountOrderType);
		assertValid(postDiscountOrderType);
	}

	protected DiscountOrderType
			testPostDiscountByExternalReferenceCodeDiscountOrderType_addDiscountOrderType(
				DiscountOrderType discountOrderType)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetDiscountIdDiscountOrderTypesPage() throws Exception {
		Long id = testGetDiscountIdDiscountOrderTypesPage_getId();
		Long irrelevantId =
			testGetDiscountIdDiscountOrderTypesPage_getIrrelevantId();

		Page<DiscountOrderType> page =
			discountOrderTypeResource.getDiscountIdDiscountOrderTypesPage(
				id, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantId != null) {
			DiscountOrderType irrelevantDiscountOrderType =
				testGetDiscountIdDiscountOrderTypesPage_addDiscountOrderType(
					irrelevantId, randomIrrelevantDiscountOrderType());

			page =
				discountOrderTypeResource.getDiscountIdDiscountOrderTypesPage(
					irrelevantId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscountOrderType),
				(List<DiscountOrderType>)page.getItems());
			assertValid(page);
		}

		DiscountOrderType discountOrderType1 =
			testGetDiscountIdDiscountOrderTypesPage_addDiscountOrderType(
				id, randomDiscountOrderType());

		DiscountOrderType discountOrderType2 =
			testGetDiscountIdDiscountOrderTypesPage_addDiscountOrderType(
				id, randomDiscountOrderType());

		page = discountOrderTypeResource.getDiscountIdDiscountOrderTypesPage(
			id, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discountOrderType1, discountOrderType2),
			(List<DiscountOrderType>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetDiscountIdDiscountOrderTypesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetDiscountIdDiscountOrderTypesPage_getId();

		DiscountOrderType discountOrderType1 = randomDiscountOrderType();

		discountOrderType1 =
			testGetDiscountIdDiscountOrderTypesPage_addDiscountOrderType(
				id, discountOrderType1);

		for (EntityField entityField : entityFields) {
			Page<DiscountOrderType> page =
				discountOrderTypeResource.getDiscountIdDiscountOrderTypesPage(
					id, null,
					getFilterString(entityField, "between", discountOrderType1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discountOrderType1),
				(List<DiscountOrderType>)page.getItems());
		}
	}

	@Test
	public void testGetDiscountIdDiscountOrderTypesPageWithFilterDoubleEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DOUBLE);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetDiscountIdDiscountOrderTypesPage_getId();

		DiscountOrderType discountOrderType1 =
			testGetDiscountIdDiscountOrderTypesPage_addDiscountOrderType(
				id, randomDiscountOrderType());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		DiscountOrderType discountOrderType2 =
			testGetDiscountIdDiscountOrderTypesPage_addDiscountOrderType(
				id, randomDiscountOrderType());

		for (EntityField entityField : entityFields) {
			Page<DiscountOrderType> page =
				discountOrderTypeResource.getDiscountIdDiscountOrderTypesPage(
					id, null,
					getFilterString(entityField, "eq", discountOrderType1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discountOrderType1),
				(List<DiscountOrderType>)page.getItems());
		}
	}

	@Test
	public void testGetDiscountIdDiscountOrderTypesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetDiscountIdDiscountOrderTypesPage_getId();

		DiscountOrderType discountOrderType1 =
			testGetDiscountIdDiscountOrderTypesPage_addDiscountOrderType(
				id, randomDiscountOrderType());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		DiscountOrderType discountOrderType2 =
			testGetDiscountIdDiscountOrderTypesPage_addDiscountOrderType(
				id, randomDiscountOrderType());

		for (EntityField entityField : entityFields) {
			Page<DiscountOrderType> page =
				discountOrderTypeResource.getDiscountIdDiscountOrderTypesPage(
					id, null,
					getFilterString(entityField, "eq", discountOrderType1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discountOrderType1),
				(List<DiscountOrderType>)page.getItems());
		}
	}

	@Test
	public void testGetDiscountIdDiscountOrderTypesPageWithPagination()
		throws Exception {

		Long id = testGetDiscountIdDiscountOrderTypesPage_getId();

		DiscountOrderType discountOrderType1 =
			testGetDiscountIdDiscountOrderTypesPage_addDiscountOrderType(
				id, randomDiscountOrderType());

		DiscountOrderType discountOrderType2 =
			testGetDiscountIdDiscountOrderTypesPage_addDiscountOrderType(
				id, randomDiscountOrderType());

		DiscountOrderType discountOrderType3 =
			testGetDiscountIdDiscountOrderTypesPage_addDiscountOrderType(
				id, randomDiscountOrderType());

		Page<DiscountOrderType> page1 =
			discountOrderTypeResource.getDiscountIdDiscountOrderTypesPage(
				id, null, null, Pagination.of(1, 2), null);

		List<DiscountOrderType> discountOrderTypes1 =
			(List<DiscountOrderType>)page1.getItems();

		Assert.assertEquals(
			discountOrderTypes1.toString(), 2, discountOrderTypes1.size());

		Page<DiscountOrderType> page2 =
			discountOrderTypeResource.getDiscountIdDiscountOrderTypesPage(
				id, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscountOrderType> discountOrderTypes2 =
			(List<DiscountOrderType>)page2.getItems();

		Assert.assertEquals(
			discountOrderTypes2.toString(), 1, discountOrderTypes2.size());

		Page<DiscountOrderType> page3 =
			discountOrderTypeResource.getDiscountIdDiscountOrderTypesPage(
				id, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				discountOrderType1, discountOrderType2, discountOrderType3),
			(List<DiscountOrderType>)page3.getItems());
	}

	@Test
	public void testGetDiscountIdDiscountOrderTypesPageWithSortDateTime()
		throws Exception {

		testGetDiscountIdDiscountOrderTypesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, discountOrderType1, discountOrderType2) -> {
				BeanUtils.setProperty(
					discountOrderType1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetDiscountIdDiscountOrderTypesPageWithSortDouble()
		throws Exception {

		testGetDiscountIdDiscountOrderTypesPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, discountOrderType1, discountOrderType2) -> {
				BeanUtils.setProperty(
					discountOrderType1, entityField.getName(), 0.1);
				BeanUtils.setProperty(
					discountOrderType2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetDiscountIdDiscountOrderTypesPageWithSortInteger()
		throws Exception {

		testGetDiscountIdDiscountOrderTypesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, discountOrderType1, discountOrderType2) -> {
				BeanUtils.setProperty(
					discountOrderType1, entityField.getName(), 0);
				BeanUtils.setProperty(
					discountOrderType2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetDiscountIdDiscountOrderTypesPageWithSortString()
		throws Exception {

		testGetDiscountIdDiscountOrderTypesPageWithSort(
			EntityField.Type.STRING,
			(entityField, discountOrderType1, discountOrderType2) -> {
				Class<?> clazz = discountOrderType1.getClass();

				String entityFieldName = entityField.getName();

				java.lang.reflect.Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						discountOrderType1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						discountOrderType2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						discountOrderType1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						discountOrderType2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						discountOrderType1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						discountOrderType2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetDiscountIdDiscountOrderTypesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, DiscountOrderType, DiscountOrderType, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetDiscountIdDiscountOrderTypesPage_getId();

		DiscountOrderType discountOrderType1 = randomDiscountOrderType();
		DiscountOrderType discountOrderType2 = randomDiscountOrderType();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, discountOrderType1, discountOrderType2);
		}

		discountOrderType1 =
			testGetDiscountIdDiscountOrderTypesPage_addDiscountOrderType(
				id, discountOrderType1);

		discountOrderType2 =
			testGetDiscountIdDiscountOrderTypesPage_addDiscountOrderType(
				id, discountOrderType2);

		for (EntityField entityField : entityFields) {
			Page<DiscountOrderType> ascPage =
				discountOrderTypeResource.getDiscountIdDiscountOrderTypesPage(
					id, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(discountOrderType1, discountOrderType2),
				(List<DiscountOrderType>)ascPage.getItems());

			Page<DiscountOrderType> descPage =
				discountOrderTypeResource.getDiscountIdDiscountOrderTypesPage(
					id, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(discountOrderType2, discountOrderType1),
				(List<DiscountOrderType>)descPage.getItems());
		}
	}

	protected DiscountOrderType
			testGetDiscountIdDiscountOrderTypesPage_addDiscountOrderType(
				Long id, DiscountOrderType discountOrderType)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDiscountIdDiscountOrderTypesPage_getId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDiscountIdDiscountOrderTypesPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDiscountIdDiscountOrderType() throws Exception {
		DiscountOrderType randomDiscountOrderType = randomDiscountOrderType();

		DiscountOrderType postDiscountOrderType =
			testPostDiscountIdDiscountOrderType_addDiscountOrderType(
				randomDiscountOrderType);

		assertEquals(randomDiscountOrderType, postDiscountOrderType);
		assertValid(postDiscountOrderType);
	}

	protected DiscountOrderType
			testPostDiscountIdDiscountOrderType_addDiscountOrderType(
				DiscountOrderType discountOrderType)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void assertContains(
		DiscountOrderType discountOrderType,
		List<DiscountOrderType> discountOrderTypes) {

		boolean contains = false;

		for (DiscountOrderType item : discountOrderTypes) {
			if (equals(discountOrderType, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			discountOrderTypes + " does not contain " + discountOrderType,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		DiscountOrderType discountOrderType1,
		DiscountOrderType discountOrderType2) {

		Assert.assertTrue(
			discountOrderType1 + " does not equal " + discountOrderType2,
			equals(discountOrderType1, discountOrderType2));
	}

	protected void assertEquals(
		List<DiscountOrderType> discountOrderTypes1,
		List<DiscountOrderType> discountOrderTypes2) {

		Assert.assertEquals(
			discountOrderTypes1.size(), discountOrderTypes2.size());

		for (int i = 0; i < discountOrderTypes1.size(); i++) {
			DiscountOrderType discountOrderType1 = discountOrderTypes1.get(i);
			DiscountOrderType discountOrderType2 = discountOrderTypes2.get(i);

			assertEquals(discountOrderType1, discountOrderType2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DiscountOrderType> discountOrderTypes1,
		List<DiscountOrderType> discountOrderTypes2) {

		Assert.assertEquals(
			discountOrderTypes1.size(), discountOrderTypes2.size());

		for (DiscountOrderType discountOrderType1 : discountOrderTypes1) {
			boolean contains = false;

			for (DiscountOrderType discountOrderType2 : discountOrderTypes2) {
				if (equals(discountOrderType1, discountOrderType2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				discountOrderTypes2 + " does not contain " + discountOrderType1,
				contains);
		}
	}

	protected void assertValid(DiscountOrderType discountOrderType)
		throws Exception {

		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (discountOrderType.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (discountOrderType.getDiscountExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("discountId", additionalAssertFieldName)) {
				if (discountOrderType.getDiscountId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountOrderTypeId", additionalAssertFieldName)) {

				if (discountOrderType.getDiscountOrderTypeId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("orderType", additionalAssertFieldName)) {
				if (discountOrderType.getOrderType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"orderTypeExternalReferenceCode",
					additionalAssertFieldName)) {

				if (discountOrderType.getOrderTypeExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("orderTypeId", additionalAssertFieldName)) {
				if (discountOrderType.getOrderTypeId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("priority", additionalAssertFieldName)) {
				if (discountOrderType.getPriority() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<DiscountOrderType> page) {
		boolean valid = false;

		java.util.Collection<DiscountOrderType> discountOrderTypes =
			page.getItems();

		int size = discountOrderTypes.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.headless.commerce.admin.pricing.dto.v2_0.
						DiscountOrderType.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		DiscountOrderType discountOrderType1,
		DiscountOrderType discountOrderType2) {

		if (discountOrderType1 == discountOrderType2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)discountOrderType1.getActions(),
						(Map)discountOrderType2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discountOrderType1.getDiscountExternalReferenceCode(),
						discountOrderType2.
							getDiscountExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("discountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountOrderType1.getDiscountId(),
						discountOrderType2.getDiscountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountOrderTypeId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discountOrderType1.getDiscountOrderTypeId(),
						discountOrderType2.getDiscountOrderTypeId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("orderType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountOrderType1.getOrderType(),
						discountOrderType2.getOrderType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"orderTypeExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discountOrderType1.getOrderTypeExternalReferenceCode(),
						discountOrderType2.
							getOrderTypeExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("orderTypeId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountOrderType1.getOrderTypeId(),
						discountOrderType2.getOrderTypeId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priority", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountOrderType1.getPriority(),
						discountOrderType2.getPriority())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		Stream<java.lang.reflect.Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			java.lang.reflect.Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_discountOrderTypeResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_discountOrderTypeResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		java.util.Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField ->
				Objects.equals(entityField.getType(), type) &&
				!ArrayUtil.contains(
					getIgnoredEntityFieldNames(), entityField.getName())
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator,
		DiscountOrderType discountOrderType) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					discountOrderType.getDiscountExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("discountId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountOrderTypeId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("orderType")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("orderTypeExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					discountOrderType.getOrderTypeExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("orderTypeId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priority")) {
			sb.append(String.valueOf(discountOrderType.getPriority()));

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected DiscountOrderType randomDiscountOrderType() throws Exception {
		return new DiscountOrderType() {
			{
				discountExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				discountId = RandomTestUtil.randomLong();
				discountOrderTypeId = RandomTestUtil.randomLong();
				orderTypeExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				orderTypeId = RandomTestUtil.randomLong();
				priority = RandomTestUtil.randomInt();
			}
		};
	}

	protected DiscountOrderType randomIrrelevantDiscountOrderType()
		throws Exception {

		DiscountOrderType randomIrrelevantDiscountOrderType =
			randomDiscountOrderType();

		return randomIrrelevantDiscountOrderType;
	}

	protected DiscountOrderType randomPatchDiscountOrderType()
		throws Exception {

		return randomDiscountOrderType();
	}

	protected DiscountOrderTypeResource discountOrderTypeResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseDiscountOrderTypeResourceTestCase.class);

	private static BeanUtilsBean _beanUtilsBean = new BeanUtilsBean() {

		@Override
		public void copyProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {

			if (value != null) {
				super.copyProperty(bean, name, value);
			}
		}

	};
	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.commerce.admin.pricing.resource.v2_0.
		DiscountOrderTypeResource _discountOrderTypeResource;

}