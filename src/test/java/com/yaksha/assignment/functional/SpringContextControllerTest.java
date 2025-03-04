package com.yaksha.assignment.functional;

import static com.yaksha.assignment.utils.TestUtils.businessTestFile;
import static com.yaksha.assignment.utils.TestUtils.currentTest;
import static com.yaksha.assignment.utils.TestUtils.testReport;
import static com.yaksha.assignment.utils.TestUtils.yakshaAssert;

import java.io.IOException;

import org.junit.After;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

import com.yaksha.assignment.TestBean;
import com.yaksha.assignment.utils.XMLParserUtils;

public class SpringContextControllerTest {

	private MockMvc mockMvc;

	@After
	public void afterAll() {
		testReport();
	}

	@Test
	public void testClassPathXmlContextLoadsBeanAndNameCorrectly() throws IOException {
		// Load the context from the classpath-based XML configuration
		ApplicationContext contextClasspath = new GenericXmlApplicationContext("file:src/main/resources/applicationContext.xml");

		// Retrieve the bean from the context
		TestBean testBean = contextClasspath.getBean("testBean", TestBean.class);

		// Assert that the bean is correctly instantiated
		boolean beanNotNull = testBean != null;

		// Assert that the 'name' property is set correctly
		boolean beanPresent = "Spring Boot Bean Example".equals(testBean.getName());

		// Use yakshaAssert to validate the test result
		yakshaAssert(currentTest(), beanNotNull && beanPresent, businessTestFile);
	}

	@Test
	public void testFileSystemXmlContextLoadsBeanAndNameCorrectly() throws IOException {
		// Load the context from the file system-based XML configuration
		ApplicationContext contextFileSystem = new GenericXmlApplicationContext(
				"file:src/main/resources/external-config/fileSystemContext.xml");

		// Retrieve the bean from the context
		TestBean testBeanFromFileSystem = contextFileSystem.getBean("testBeanFromFileSystem", TestBean.class);

		// Assert that the bean is correctly instantiated
		boolean beanNotNull = testBeanFromFileSystem != null;

		// Assert that the 'name' property is set correctly
		boolean beanPresent = "File System Bean".equals(testBeanFromFileSystem.getName());

		// Use yakshaAssert to validate the test result
		yakshaAssert(currentTest(), beanNotNull && beanPresent, businessTestFile);
	}

	@Test
	public void testApplicationContextXMLContainsRequiredBeanAndProperty() throws IOException {
		String filePath = "src/main/resources/applicationContext.xml"; // Path to the first XML file
		boolean result = XMLParserUtils.checkXMLStructure(filePath, "testBean", // The expected bean id in
				// applicationContext.xml
				"name", // The expected property name in the bean
				"Spring Boot Bean Example"); // The expected property value

		// Using yakshaAssert to validate the test result
		yakshaAssert(currentTest(), result, businessTestFile);
	}

	@Test
	public void testFileSystemContextXMLContainsRequiredBeanAndProperty() throws IOException {
		String filePath = "src/main/resources/external-config/fileSystemContext.xml"; // Path to the second XML file
		boolean result = XMLParserUtils.checkXMLStructure(filePath, "testBeanFromFileSystem", // The expected bean id in
				// fileSystemContext.xml
				"name", // The expected property name in the bean
				"File System Bean"); // The expected property value

		// Using yakshaAssert to validate the test result
		yakshaAssert(currentTest(), result, businessTestFile);
	}

	@Test
	public void testMissingPropertyInXMLShouldFailTest() throws IOException {
		String filePath = "src/main/resources/applicationContext.xml"; // Path to XML file
		boolean result = XMLParserUtils.checkXMLStructure(filePath, "testBean", // The expected bean id
				"name", // The expected property name
				"Non-Existent Value"); // A value that does not exist

		// Using yakshaAssert to validate the test result
		yakshaAssert(currentTest(), result, businessTestFile);
	}
}
