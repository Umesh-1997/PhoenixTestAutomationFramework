package com.api.test;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Model;
import com.api.constant.OEM;
import com.api.constant.Platform;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.constant.Role;
import com.api.constant.ServiceLocation;
import com.api.constant.Warranty_Status;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.utils.DateTimeUtil;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobAPITest {
	// Creating CreateJobPayload Object
	
	private CreateJobPayload createJobPayload;
	
	@BeforeMethod(description="Creating createJob api request payload")
	public void setup()
	{
		Customer customer = new Customer("Umesh", "Parab", "9769082745", "", "umesh.parabcr10@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("B 999","Vasant Galaxy","Bangur Nagar", "InOrbit Mall","Mumbai", "500032","India","Maharashtra");
		CustomerProduct customerProduct = new CustomerProduct(DateTimeUtil.getTimeWithDaysAgo(10), "25593655465247", "25593655465247", "25593655465247", DateTimeUtil.getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		Problems problems=new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery Issues");
		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);
		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), Warranty_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer, customerAddress, customerProduct, problemList);
	}
	
	@Test(description="Verifying if create job api is able to create Inwarrant jobs",groups= {"api","regression","smoke"})
	public void createAPITest()
	{
		
//		Customer customer = new Customer("Umesh", "Parab", "9769082745", "", "umesh.parabcr10@gmail.com", "");
//		CustomerAddress customerAddress = new CustomerAddress("B 999","Vasant Galaxy","Bangur Nagar", "InOrbit Mall","Mumbai", "500032","India","Maharashtra");
//		CustomerProduct customerProduct = new CustomerProduct(DateTimeUtil.getTimeWithDaysAgo(10), "25593655465247", "25593655465247", "25593655465247", DateTimeUtil.getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
//		Problems problems=new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery Issues");
//		List<Problems> problemList = new ArrayList<Problems>();
//		problemList.add(problems);
//		CreateJobPayload createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), Warranty_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer, customerAddress, customerProduct, problemList);
//		
		
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
