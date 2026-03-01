 package com.api.test;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class MasterAPITest {
	
	@Test(description="Verifying if Master API is giving correct response",groups= {"api","regression","smoke"})
	public void masterAPITest() {
		
		given()
//			.baseUri(ConfigManager.getProperty("BASE_URI"))
//			.contentType(ContentType.JSON)
//			.header("Authorization",AuthTokenProvider.getToken(Role.FD))
			.spec(SpecUtil.requestSpecWithAuth(Role.FD))
			.and()
			.contentType("")
			.log().all()
	
	
		.when()
		.post("master")
		
		.then()
//		.log().all()
//		.statusCode(200)
//		.time(Matchers.lessThan(1000L))
		.spec(SpecUtil.responseSpec_OK())
		.body("message",Matchers.equalTo("Success"))
		.body("data",Matchers.notNullValue())
		.body("data",Matchers.hasKey("mst_oem"))
		.body("data",Matchers.hasKey("mst_model"))
		.body("data.mst_oem.size()",Matchers.greaterThan(0))
		.body("data.mst_model.size()",Matchers.equalTo(3))
		.body("data.mst_oem.id",Matchers.everyItem(Matchers.greaterThanOrEqualTo(0)))
		.body("$",Matchers.hasKey("message"))
		.body("$",Matchers.hasKey("data"))
		.body("data.mst_oem.name",Matchers.everyItem(Matchers.notNullValue()))
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));
		
	}
	
	@Test(description="Verifying if Master API is giving correct status code for invalid token",groups= {"api","negative","regression","smoke"})
	public void inValidTokenMasterAPITest()
	{
		given()
//		.baseUri(ConfigManager.getProperty("BASE_URI"))
//		.contentType(ContentType.JSON)
//		.header("Authorization","")
		.spec(SpecUtil.requestSpec())

	.when()
	.post("master")
	
	.then()
//	.log().all()
//	.statusCode(401);
	.spec(SpecUtil.responseSpec_TEXT(401));
	}

}
