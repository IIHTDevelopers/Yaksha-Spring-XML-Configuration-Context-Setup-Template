package com.yaksha.assignment.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParserUtils {

	/**
	 * Parses the given XML file and checks if the required beans and properties are
	 * present.
	 *
	 * @param filePath              Full path to the XML configuration file (e.g.,
	 *                              "src/main/resources/applicationContext.xml").
	 * @param expectedBeanId        The expected bean id to be found in the XML.
	 * @param expectedProperty      The expected property name in the bean (e.g.,
	 *                              name).
	 * @param expectedPropertyValue The expected value for the property (e.g.,
	 *                              "Spring Boot Bean Example").
	 * @return true if all checks pass, false otherwise.
	 */
	public static boolean checkXMLStructure(String filePath, String expectedBeanId, String expectedProperty,
			String expectedPropertyValue) {

		try {
			// Parse the XML file
			File xmlFile = new File(filePath);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);
			document.getDocumentElement().normalize();

			// Check for the required bean ID
			NodeList beanNodes = document.getElementsByTagName("bean");
			List<String> beanIds = new ArrayList<>();
			for (int i = 0; i < beanNodes.getLength(); i++) {
				Node beanNode = beanNodes.item(i);
				if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) beanNode;
					String beanId = element.getAttribute("id");
					beanIds.add(beanId);

					// Check for the expected property in the bean
					if (beanId.equals(expectedBeanId)) {
						NodeList propertyNodes = element.getElementsByTagName("property");
						for (int j = 0; j < propertyNodes.getLength(); j++) {
							Node propertyNode = propertyNodes.item(j);
							if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
								Element propertyElement = (Element) propertyNode;
								String propertyName = propertyElement.getAttribute("name");
								String propertyValue = propertyElement.getAttribute("value");

								// Check if the expected property and value match
								if (propertyName.equals(expectedProperty)
										&& propertyValue.equals(expectedPropertyValue)) {
									return true;
								}
							}
						}
					}
				}
			}

			// If no matching bean or property is found, return false
			return false;

		} catch (Exception e) {
			System.out.println("Error parsing the XML file: " + filePath);
			e.printStackTrace();
			return false;
		}
	}
}
