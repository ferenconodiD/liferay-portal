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

package com.liferay.portal.tools;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.xml.Dom4jUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CSVUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.xml.SAXReaderFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.TreeMap;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.SAXReader;

/**
 * @author Peter Shin
 */
public class SPDXBuilder {

	public static void main(String[] args) throws IOException {
		String xmls = null;

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(System.in))) {

			xmls = bufferedReader.readLine();
		}

		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String rdfFileName = ArgumentsUtil.getString(
			arguments, "spdx.file", null);
		String licenseReportPropertiesFileName = ArgumentsUtil.getString(
			arguments, "license.report.override.properties.file", null);

		new SPDXBuilder(
			StringUtil.split(xmls), rdfFileName,
			licenseReportPropertiesFileName);
	}

	public SPDXBuilder(
		String[] xmls, String rdfFileName,
		String licenseReportPropertiesFileName) {

		try {
			System.setProperty("line.separator", StringPool.NEW_LINE);

			Document document = _getDocument(
				xmls, rdfFileName, licenseReportPropertiesFileName);
			File rdfFile = new File(rdfFileName);

			_write(
				new File(rdfFile.getParentFile(), "versions-spdx.xml"),
				Dom4jUtil.toString(document));

			_write(
				new File(rdfFile.getParentFile(), "versions-spdx.csv"),
				_toCSV(document));

			TransformerFactory transformerFactory =
				TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer(
				new StreamSource(
					new File(rdfFile.getParentFile(), "versions.xsl")));

			File versionHtmlFile = new File(
				rdfFile.getParentFile(), "versions-spdx.html");

			transformer.transform(
				new DocumentSource(document),
				new StreamResult(new FileOutputStream(versionHtmlFile)));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private List<Element> _createLibraryElements(
		Element packageElement, Properties licenseProperties) {

		List<Element> libraryElements = new ArrayList<>();

		String downloadLocation = packageElement.elementText(
			_getQName("downloadLocation"));
		String name = packageElement.elementText(_getQName("name"));
		String versionInfo = packageElement.elementText(
			_getQName("versionInfo"));

		Element hasFileElement = packageElement.element(_getQName("hasFile"));

		List<Element> fileElements = hasFileElement.elements(_getQName("File"));

		for (Element fileElement : fileElements) {
			String fileName = fileElement.elementText(_getQName("fileName"));

			String dirName = fileName.substring(0, fileName.indexOf('/') + 1);

			if (dirName.endsWith("portal/") || dirName.endsWith("portal-ee/")) {
				fileName = fileName.substring(dirName.length());
			}

			Element libraryElement = DocumentHelper.createElement("library");

			Element fileNameElement = libraryElement.addElement("file-name");

			fileNameElement.addText(fileName);

			Element versionElement = libraryElement.addElement("version");

			versionElement.addText(versionInfo);

			Element projectNameElement = libraryElement.addElement(
				"project-name");

			projectNameElement.addText(name);

			if ((downloadLocation != null) &&
				downloadLocation.startsWith("http")) {

				Element element = libraryElement.addElement("project-url");

				element.addText(downloadLocation);
			}

			String licenseName = _getLicenseName(
				packageElement, fileName, licenseProperties);

			if (licenseName != null) {
				Element licensesElement = libraryElement.addElement("licenses");

				Element licenseElement = licensesElement.addElement("license");

				Element element = licenseElement.addElement("license-name");

				element.addText(licenseName);

				String licenseURL = _getLicenseURL(
					packageElement, fileName, licenseProperties);

				if (licenseURL != null) {
					element = licenseElement.addElement("license-url");

					element.addText(licenseURL);
				}
			}

			libraryElements.add(libraryElement);
		}

		return libraryElements;
	}

	private String _encode(Node node) {
		if (node == null) {
			return StringPool.BLANK;
		}

		String text = node.getText();

		text = StringUtil.trim(text.replaceAll("\\s+", " "));

		return CSVUtil.encode(text);
	}

	@SuppressWarnings("unchecked")
	private Document _getDocument(
			String[] xmls, String rdfFileName,
			String licenseReportPropertiesFileName)
		throws Exception {

		Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;

		Map<String, Element> libraryElementMap = new TreeMap<>(comparator);

		SAXReader saxReader = SAXReaderFactory.getSAXReader(null, false, false);

		for (String xml : xmls) {
			Document xmlDocument = saxReader.read(new File(xml));

			List<Node> fileNameNodes = xmlDocument.selectNodes("//file-name");

			for (Node fileNameNode : fileNameNodes) {
				Element libraryElement = fileNameNode.getParent();

				String key = _getKey("portal", libraryElement);

				libraryElementMap.put(key, libraryElement);
			}
		}

		Properties licenseProperties = _getLicenseProperties(
			licenseReportPropertiesFileName);

		Document spdxDocument = saxReader.read(new File(rdfFileName));

		Element spdxRootElement = spdxDocument.getRootElement();

		Element spdxDocumentElement = spdxRootElement.element(
			_getQName("SpdxDocument"));

		List<Element> elements = spdxDocumentElement.elements(
			_getQName("relationship"));

		for (Element element : elements) {
			Element relationshipElement = element.element(
				_getQName("Relationship"));

			Element relatedSPDXElement = relationshipElement.element(
				_getQName("relatedSpdxElement"));

			Element packageElement = relatedSPDXElement.element(
				_getQName("Package"));

			List<Element> libraryElements = _createLibraryElements(
				packageElement, licenseProperties);

			for (Element libraryElement : libraryElements) {
				String key = _getKey("spdx", libraryElement);

				libraryElementMap.put(key, libraryElement);
			}
		}

		Document document = DocumentHelper.createDocument();

		Map<String, String> args = new HashMap<>();

		args.put("href", "versions.xsl");
		args.put("type", "text/xsl");

		document.addProcessingInstruction("xml-stylesheet", args);

		Element versionsElement = document.addElement("versions");

		Element versionElement = versionsElement.addElement("version");

		Element librariesElement = versionElement.addElement("libraries");

		for (Element libraryElement : libraryElementMap.values()) {
			librariesElement.add(libraryElement.detach());
		}

		return document;
	}

	private String _getKey(String type, Element libraryElement) {
		StringBuilder sb = new StringBuilder();

		sb.append(StringUtil.upperCase(type));
		sb.append(StringPool.COLON);

		Node fileNameNode = libraryElement.selectSingleNode("file-name");

		sb.append(fileNameNode.getText());

		sb.append(StringPool.COLON);

		Node versionNode = libraryElement.selectSingleNode("version");

		sb.append(versionNode.getText());

		return sb.toString();
	}

	private String _getLicenseName(
		Element packageElement, String fileName, Properties licenseProperties) {

		String key = "license.name[" + fileName + "]";

		if (licenseProperties.containsKey(key)) {
			return licenseProperties.getProperty(key);
		}

		Element licenseConcludedElement = packageElement.element(
			_getQName("licenseConcluded"));

		if (licenseConcludedElement == null) {
			return null;
		}

		String resource = licenseConcludedElement.attributeValue(
			_getQName("resource"));

		if (resource.startsWith("http://spdx.org/licenses/")) {
			return resource.substring(25);
		}

		if (resource.startsWith("LicenseRef-")) {
			return resource.substring(11);
		}

		return null;
	}

	private Properties _getLicenseProperties(
			String licenseReportPropertiesFileName)
		throws IOException {

		Properties licenseProperties = new Properties();

		if (Validator.isNull(licenseReportPropertiesFileName)) {
			return licenseProperties;
		}

		File file = new File(licenseReportPropertiesFileName);

		if (file.exists()) {
			licenseProperties.load(new FileInputStream(file));
		}

		return licenseProperties;
	}

	private String _getLicenseURL(
		Element packageElement, String fileName, Properties licenseProperties) {

		String key = "license.url[" + fileName + "]";

		if (licenseProperties.containsKey(key)) {
			return licenseProperties.getProperty(key);
		}

		Element licenseConcludedElement = packageElement.element(
			_getQName("licenseConcluded"));

		String resource = licenseConcludedElement.attributeValue(
			_getQName("resource"));

		if (!resource.startsWith("http")) {
			return null;
		}

		return resource;
	}

	private QName _getQName(String name) {
		if (!_qNameMap.containsKey(name)) {
			QName qName = new QName(name, _NAMESPACE_SPDX, "spdx:" + name);

			if (Objects.equals(name, "resource")) {
				qName = new QName(name, _NAMESPACE_RDF, "rdf:" + name);
			}

			_qNameMap.put(name, qName);
		}

		return _qNameMap.get(name);
	}

	@SuppressWarnings("unchecked")
	private String _toCSV(Document document) {
		StringBuilder sb = new StringBuilder();

		sb.append("File Name,Version,Project,License,Comments");

		List<Node> libraryNodes = document.selectNodes("//library");

		for (Node libraryNode : libraryNodes) {
			sb.append('\n');

			sb.append(_encode(libraryNode.selectSingleNode("file-name")));
			sb.append(',');
			sb.append(_encode(libraryNode.selectSingleNode("version")));
			sb.append(',');
			sb.append(_encode(libraryNode.selectSingleNode("project-name")));
			sb.append(',');

			String[] licenses = new String[0];

			List<Node> licenseNameNodes = libraryNode.selectNodes(
				"./licenses/license/license-name");

			if (!licenseNameNodes.isEmpty()) {
				String text = StringPool.BLANK;

				if (licenseNameNodes.get(0) != null) {
					Node node = licenseNameNodes.get(0);

					text = node.getText();

					text = StringUtil.trim(text.replaceAll("\\s+", " "));
				}

				licenses = ArrayUtil.append(licenses, text);
			}

			sb.append(CSVUtil.encode(licenses));

			sb.append(',');
			sb.append(_encode(libraryNode.selectSingleNode("comments")));
		}

		return sb.toString();
	}

	private void _write(File file, String s) throws Exception {
		Files.write(file.toPath(), s.getBytes(StandardCharsets.UTF_8));
	}

	private static final Namespace _NAMESPACE_RDF = new Namespace(
		"rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");

	private static final Namespace _NAMESPACE_SPDX = new Namespace(
		"spdx", "http://spdx.org/rdf/terms#");

	private static final Map<String, QName> _qNameMap = new HashMap<>();

}