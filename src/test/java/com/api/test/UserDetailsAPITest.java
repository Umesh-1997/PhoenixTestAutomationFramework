package com.api.test;

import static com.api.utils.AuthTokenProvider.getToken;
import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

import static com.api.constant.Role.*;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

public class UserDetailsAPITest {
	
	@Test
	public void userDetailsAPITest() throws IOException {
		
		
		
		
		
		given()
			.spec(SpecUtil.requestSpecWithAuth(FD))
		
		.when()
			.get("userdetails")
			
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.and()
			.body("message",Matchers.equalTo("Success"))
			.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
		
	}

}
