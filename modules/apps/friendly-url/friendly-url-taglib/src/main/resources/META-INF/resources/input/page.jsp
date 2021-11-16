<%--
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
--%>

<%@ include file="/input/init.jsp" %>

<%
int friendlyURLMaxLength = (int)request.getAttribute("liferay-friendly-url:input:friendlyURLMaxLength");
String name = (String)request.getAttribute("liferay-friendly-url:input:name");
String value = (String)request.getAttribute("liferay-friendly-url:input:value");
%>

<liferay-friendly-url:history
	className='<%= (String)request.getAttribute("liferay-friendly-url:input:className") %>'
	classPK='<%= (long)request.getAttribute("liferay-friendly-url:input:classPK") %>'
	elementId="<%= portletDisplay.getNamespace() + name %>"
/>

<div class="form-group friendly-url">
	<label for="<portlet:namespace /><%= name %>">
		<liferay-ui:message key="friendly-url" />

		<liferay-ui:icon-help message='<%= LanguageUtil.format(request, "there-is-a-limit-of-x-characters-in-encoded-format-for-friendly-urls-(e.g.-x)", new String[] {String.valueOf(friendlyURLMaxLength), "<em>/news</em>"}, false) %>' />
	</label>

	<c:choose>
		<c:when test='<%= (boolean)request.getAttribute("liferay-friendly-url:input:localizable") %>'>
			<liferay-ui:input-localized
				defaultLanguageId="<%= LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()) %>"
				ignoreRequestValue="<%= SessionErrors.isEmpty(request) %>"
				inputAddon='<%= (String)request.getAttribute("liferay-friendly-url:input:inputAddon") %>'
				name="<%= name %>"
				xml="<%= HttpUtil.decodeURL(value) %>"
			/>
		</c:when>
		<c:otherwise>
			<div class="form-text">
				<%= (String)request.getAttribute("liferay-friendly-url:input:inputAddon") %>
			</div>

			<aui:input cssClass="input-medium" disabled='<%= (boolean)request.getAttribute("liferay-friendly-url:input:disabled") %>' ignoreRequestValue="<%= true %>" label="" name="<%= name %>" type="text" value='<%= value %>' />
		</c:otherwise>
	</c:choose>
</div>