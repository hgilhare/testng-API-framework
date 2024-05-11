 package parctice1;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import testapipojos.addplaceResponsepojo;
import testapipojos.addplacepojo;

import testapipojos.locationchild;
import testapipojos.updatedatapojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.javafaker.Faker;

import helper.base;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class testapi {

	 
	 Faker f= new Faker();
	 String fname= f.name().firstName();
	 String fphone= f.phoneNumber().phoneNumber();
	 String faddress= f.address().fullAddress();
	 
	 
	 String placeid; 
	 
	 
	 

	@Test(priority = 0,description="add place",invocationCount=1)
	public void posttesting() throws IOException {

		// https://rahulshettyacademy.com/maps/api/place/add/json?key=qaclick123

		/// post
		
		addplacepojo p=new addplacepojo();
		locationchild c= new locationchild();
		
		
		c.setLat(f.number().randomNumber());
		c.setLng(33.427362);
		
		p.setLocation(c);
		p.setAccuracy(50);
		
		
		p.setName(fname);
		p.setPhone_number(fphone);
		p.setAddress(faddress);
		List<String> typeslist= new ArrayList<String>();
		typeslist.add("shoe park");
		typeslist.add("shop");
		
		p.setTypes(typeslist);
		p.setWebsite("http://google.com");
		p.setLanguage("hindi");
		

		
	
		//setter method
		
//	Response	 postresponse = given().log().all().queryParam("key", "qaclick123")
	//			.header("Content-Type", "application/json")
		//		.body(p)
			//	.when().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200).extract()
	//			.response();
	
//	addplaceResponsepojo res=postresponse.as(addplaceResponsepojo.class);

///
		//String actualstatus=res.getStatus();
	//	Assert.assertEquals(actualstatus, "OK");
		//String actualplaceid=res.getPlace_id();
	//	System.out.println(actualplaceid);
		//String actualscope=res.getScope();
	//	Assert.assertEquals(actualscope, "APP");
		
		
		
		
		
		
	// standard method	
		Response postresponse = given().log().all().spec(base.setup())
				.body(p)
				.when().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200).extract()
				.response();
		
		String response = postresponse.asString();

		JsonPath js = new JsonPath(response);
		String actualstatus = js.getString("status");

		Assert.assertEquals(actualstatus, "OK");

		String actualscope = js.getString("scope");
	 Assert.assertEquals(actualscope, "APP");

	placeid = js.getString("place_id");
		

	}
	

	/// GET
	@Test(priority = 1,description="get place",invocationCount=1)
		public void gettesting() {
		RestAssured.baseURI="https://rahulshettyacademy.com";

		Response getresponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid)
					.when().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract()
					.response();

		String getreponse1 = getresponse.asString();
			JsonPath js1 = new JsonPath(getreponse1);
			String actualname = js1.getString("name");
			Assert.assertEquals(actualname, fname);
				String actualphonenumber = js1.getString("phone_number");
				Assert.assertEquals(actualphonenumber, fphone);
				String actualaddress = js1.getString("address");
				Assert.assertEquals(actualaddress, faddress);
			String actualtypes = js1.getString("types");
			Assert.assertEquals(actualtypes, "shoe park,shop");
			String actualwebsite = js1.getString("website");
			Assert.assertEquals(actualwebsite, "http://google.com");
			String actuallanguage = js1.getString("language");
			Assert.assertEquals(actuallanguage, "hindi");
	}

	/// update or put
	@Test(priority = 2,description="update place",invocationCount=1)
	public void updatetesting() {
		
		updatedatapojo u= new updatedatapojo();
		u.setPlace_id(placeid);
		u.setAddress(f.address().fullAddress());
		u.setKey("qaclick123");

			Response updateresponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid)
					.header("Content-Type", "application/json")
							.body(u)
		.when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200).extract()
			.response();

			String updateresponse1 = updateresponse.asString();

			JsonPath js2 = new JsonPath(updateresponse1);

			String updatedaddress = js2.getString("msg");
			Assert.assertEquals(updatedaddress, "Address successfully updated");
	}

	/// delete
	@Test(priority = 3,description="delete place",invocationCount=1)
	public void deletetesting() {

		Response delresponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid)
				.header("Content-Type", "application/json")
				.body("{\r\n" + "\r\n" + "    \"place_id\":\"" + placeid + "\"\r\n" + "}").when()
				.delete("maps/api/place/delete/json").then().log().all().assertThat().statusCode(200).extract()
				.response();

		String delresponse1 = delresponse.asString();

		JsonPath js3 = new JsonPath(delresponse1);
		String delmsg = js3.getString("status");

		Assert.assertEquals(delmsg, "OK");

	}

}
