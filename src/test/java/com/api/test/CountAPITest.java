package com.api.test;

import org.apache.http.entity.mime.Header;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

import static com.api.constant.Role.FD;
import static com.api.utils.AuthTokenProvider.getToken;
import static io.restassured.RestAssured.*;

public class CountAPITest {

	
	@Test
	public void verifyCountAPIResponse()
	{
		given()
//		.baseUri(ConfigManager.getProperty("BASE_URI"))
//		.and()
//		.header("Authorization",AuthTokenProvider.getToken(FD))
//		.log().uri()
//		.log().headers()
//		.log().method()
		.spec(SpecUtil.requestSpecWithAuth(FD))
		
		
		.when()
		.get("/dashboard/count")
		
		.then()
//		.log().all()
//		.statusCode(200)
//		.body("message",equalTo("Success"))
//		.time(lessThan(1000L))
		.spec(SpecUtil.responseSpec_OK())
		.body("data",notNullValue())
		.body("data.size()",equalTo(3))
		.body("data.count",everyItem(greaterThanOrEqualTo(0)))
		.body("data.label",everyItem(not(blankOrNullString())))
		.body("data.key",containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"))
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema.json"));
	}
	
	@Test
	public void countAPITest_MissingAuthToken() {
		given()
//		.baseUri(ConfigManager.getProperty("BASE_URI"))
//		.and()
//		.log().uri()
//		.log().method()
//		.log().headers()
		.spec(SpecUtil.requestSpec())
		
		.when()
		.get("/dashboard/count")
		
		.then()
//		.log().all()
//		.statusCode(401);
		.spec(SpecUtil.responseSpec_TEXT(401));
	}
}
