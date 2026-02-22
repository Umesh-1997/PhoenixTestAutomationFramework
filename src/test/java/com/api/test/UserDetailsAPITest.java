package com.api.test;

import static com.api.utils.AuthTokenProvider.getToken;
import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static com.api.constant.Role.*;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

public class UserDetailsAPITest {
	
	@Test
	public void userDetailsAPITest() throws IOException {
		
		
		Header authHeader = new Header("Authorization",getToken(FD));
		
		
		given()
			.baseUri(getProperty("BASE_URI"))
			.contentType(ContentType.JSON)
			.and()
			.header(authHeader)
			.log().uri()
			.log().method()
		
		.when()
			.get("userdetails")
			
		.then()
			.statusCode(200)
			.log().all()
			.and()
			.body("message",Matchers.equalTo("Success"))
			.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
		
	}

}
