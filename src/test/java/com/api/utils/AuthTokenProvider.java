package com.api.utils;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import com.api.constant.Role;
import static com.api.constant.Role.*;
import com.api.pojo.UserCredentials;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class AuthTokenProvider {

	private AuthTokenProvider()
	{
		
	}
	public static String getToken(Role role)  {
		
		UserCredentials userCredentias=null;
		if(role == FD) {
			userCredentias=new UserCredentials("iamfd","password");
		}
		
		else if(role == SUP) {
			userCredentias=new UserCredentials("iamsup","password");
		}
		
		else if(role == ENG) {
			userCredentias=new UserCredentials("iameng","password");
		}
		
		else if(role == QC) {
			userCredentias=new UserCredentials("iamqc","password");
		}
		
		
		
		
		
	String token = 	given()
		.baseUri(ConfigManager.getProperty("BASE_URI"))
		.contentType(ContentType.JSON)
		.body(userCredentias)
		
		.when()
		.post("login")
		
		.then()
		.log().ifValidationFails()
		.body("message",equalTo("Success"))
		.extract().body()
		.jsonPath().getString("data.token");
	
	System.out.println("Token for " + role + " is "+token);
	
	return token;
		
		

	}

}
