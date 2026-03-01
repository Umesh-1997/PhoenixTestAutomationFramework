package com.api.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.utils.SpecUtil;

import static com.api.utils.ConfigManager.*;



import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.ResponseSpecification;

public class LoginAPITest {
	
	
	UserCredentials userCredentials;
	@BeforeMethod(description="Create Payload for Login API")
	public void setup()
	{
		userCredentials= new UserCredentials("iamfd","password");
	}
	
	
	@Test(description="Verify if login api is working for FD user",groups= {"api","regression","smoke"})
	public void loginAPITest() throws IOException {
		
		
		
		given()
//		.baseUri(getProperty("BASE_URI"))  
//		.and()
//		.contentType(ContentType.JSON)
//		.and()
//		.accept(ContentType.JSON)
//		.and()
		.spec(SpecUtil.requestSpec(userCredentials))
//		.body(userCredentials)
//		.log().uri()
//		.log().method()
//		.log().headers()
//		.log().body()
//		
		
		
		.when()
			.post("login")
			
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.and()
			.body("message",equalTo("Success"))
			.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
			
		
	}

}
