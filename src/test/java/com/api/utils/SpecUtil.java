package com.api.utils;

import org.hamcrest.Matchers;

import com.api.constant.Role;
import com.api.request.model.UserCredentials;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification; 

public class SpecUtil {

	
	
	// Get -- DEL
	public static  RequestSpecification requestSpec()
	{
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(ConfigManager.getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.log(LogDetail.URI)
		.log(LogDetail.METHOD)
		.log(LogDetail.HEADERS)
		.log(LogDetail.BODY)
		.build();
		return requestSpecification;
	}
	
	// POST-PUT-PATCH {BODY}
	
	public static  RequestSpecification requestSpec(Object userCreds)
	{
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(ConfigManager.getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.setBody(userCreds)
		.log(LogDetail.URI)
		.log(LogDetail.METHOD)
		.log(LogDetail.HEADERS)
		.log(LogDetail.BODY)
		.build();
		return requestSpecification;
	}
	
	public static RequestSpecification requestSpecWithAuth(Role role) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri(ConfigManager.getProperty("BASE_URI"))
				.setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON)
				.addHeader("Authorization",AuthTokenProvider.getToken(role))
				.log(LogDetail.URI)
				.log(LogDetail.METHOD)
				.log(LogDetail.HEADERS)
				.log(LogDetail.BODY)
				.build();
				return requestSpecification;
	}
	
	public static RequestSpecification requestSpecWithAuth(Role role,Object payload) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri(ConfigManager.getProperty("BASE_URI"))
				.setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON)
				.addHeader("Authorization",AuthTokenProvider.getToken(role))
				.setBody(payload)
				.log(LogDetail.URI)
				.log(LogDetail.METHOD)
				.log(LogDetail.HEADERS)
				.log(LogDetail.BODY)
				.build();
				return requestSpecification;
	}
	
	public static ResponseSpecification responseSpec_OK()
	{
		ResponseSpecification responseSpecification = new ResponseSpecBuilder()
		.expectContentType(ContentType.JSON)
		.expectStatusCode(200)
		.expectResponseTime(Matchers.lessThan(5000L))
		.log(LogDetail.ALL)
		.build();
		return responseSpecification;
	}
	
	public static ResponseSpecification responseSpec_JSON(int StatusCode)
	{
		ResponseSpecification responseSpecification = new ResponseSpecBuilder()
		.expectContentType(ContentType.JSON)
		.expectStatusCode(StatusCode)
		.expectResponseTime(Matchers.lessThan(5000L))
		.log(LogDetail.ALL)
		.build();
		return responseSpecification;
	}
	
	public static ResponseSpecification responseSpec_TEXT(int StatusCode)
	{
		ResponseSpecification responseSpecification = new ResponseSpecBuilder()
		.expectStatusCode(StatusCode)
		.expectResponseTime(Matchers.lessThan(5000L))
		.log(LogDetail.ALL)
		.build();
		return responseSpecification;
	}
}
