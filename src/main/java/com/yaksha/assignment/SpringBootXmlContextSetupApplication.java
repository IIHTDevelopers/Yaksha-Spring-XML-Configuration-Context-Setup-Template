package com.yaksha.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

@SpringBootApplication
public class SpringBootXmlContextSetupApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootXmlContextSetupApplication.class, args);

		// Manually load Spring XML configuration using GenericXmlApplicationContext
		ApplicationContext contextClasspath = new GenericXmlApplicationContext("classpath:applicationContext.xml");
		TestBean testBean = contextClasspath.getBean(TestBean.class);
		System.out.println("ClassPath Bean Name: " + testBean.getName());

		ApplicationContext contextFileSystem = new GenericXmlApplicationContext(
				"file:src/main/resources/external-config/fileSystemContext.xml");
		TestBean testBeanFromFileSystem = contextFileSystem.getBean(TestBean.class);
		System.out.println("FileSystem Bean Name: " + testBeanFromFileSystem.getName());
	}
}
