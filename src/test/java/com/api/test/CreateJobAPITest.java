package com.api.test;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobAPITest {
	// Creating CreateJobPayload Object
	
	
	@Test
	public void createAPITest()
	{
		
		Customer customer = new Customer("Umesh", "Parab", "9769082745", "", "umesh.parabcr10@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("B 999","Vasant Galaxy","Bangur Nagar", "InOrbit Mall","Mumbai", "500032","India","Maharashtra");
		CustomerProduct customerProduct = new CustomerProduct("2025-10-12T18:30:00.000Z", "25593658722847", "25593658722847", "25593658722847", "2025-10-12T18:30:00.000Z", 1, 1);
		Problems problems=new Problems(1, "Battery Issues");
		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemList);
		
		
		given()
//		.baseUri(ConfigManager.getProperty("BASE_URI"))
//		.and()
//		.contentType(ContentType.JSON)
//		.and()
//		.header("Authorization",AuthTokenProvider.getToken(Role.FD))
//		.and()
//		.body(createJobPayload)
		.spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload))
		
		
		.when()
			.post("job/create")
			
		.then()
			.spec(SpecUtil.responseSpec_JSON(200))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
			.body("message",Matchers.equalTo("Job created successfully. "))
			.body("data.id",Matchers.not(Matchers.empty()))
			.body("data.mst_service_location_id",Matchers.equalTo(1))
			.body("data.job_number", Matchers.startsWith("JOB_"));
	}
	
}
