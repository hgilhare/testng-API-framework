package helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import org.apache.http.client.methods.RequestBuilder;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

public class base {

	RequestSpecification req;

	public static RequestSpecification setup() throws IOException {

		
		PrintStream log=new PrintStream(new FileOutputStream("Logg.txt"));
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri(getconfigdata("baseUri"))
				.addQueryParam("key", "qaclick123").addHeader("Content-Type", "application/json")
				.addFilter(RequestLoggingFilter.logRequestTo(log)).addFilter(ResponseLoggingFilter.logResponseTo(log)).build();
		return req;

	}

	public static String getconfigdata(String key) throws IOException {

		FileInputStream file = new FileInputStream(
				System.getProperty("user.dir") + "/src/test/java/resources/data.properties");
		Properties prop = new Properties();
		prop.load(file);

		return prop.getProperty(key);
	}
}
