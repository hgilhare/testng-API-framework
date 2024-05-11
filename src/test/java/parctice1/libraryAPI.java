package parctice1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import org.hamcrest.core.IsEqual;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.Book;
import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class libraryAPI {
	
	Faker f= new Faker();
	String bname=f.name().fullName();
	
	@Test(priority = 0,description="new book",invocationCount=1)
	public void posttestAPI() {
		
	int	a=Integer.parseInt(f.number().digits(3));
String b= "xyz";
		// new book

		// https://rahulshettyacademy.com/Library/Addbook.php?ID=bcd227
		librarypojoclass pojo=new librarypojoclass();
		
		pojo.setName(bname);
		pojo.setIsbn("xyz");
		pojo.setAisle(a);
		pojo.setAuthor("robert");

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		Response postres = given().log().all().queryParam("ID", "bcd228").header("Content-Type", "application/json")
				.body(pojo)
				.when().post("Library/Addbook.php").then().log().all().assertThat().statusCode(200).extract()
				.response();

		String postreponse = postres.asString();

		JsonPath js = new JsonPath(postreponse);

		String actualmsg = js.getString("Msg");
		Assert.assertEquals(actualmsg, "successfully added");
		String actualID = js.getString("ID");
		Assert.assertEquals(actualID, b+a);

	}

	@Test(priority = 1,description="existing book",invocationCount=1)
	public void posttestapi() {

		// existing

		Response postres = given().log().all().queryParam("ID", "bcd227").header("Content-Type", "application/json")
				.body("{\r\n" + "\r\n" + "\"name\":\"Himanshu gilhare book\",\r\n" + "\"isbn\":\"xyz\",\r\n"
						+ "\"aisle\":\"118\",\r\n" + "\"author\":\"Himanshu\"\r\n" + "}\r\n" + "")
				.when().post("Library/Addbook.php").then().log().all().assertThat().statusCode(200).extract()
				.response();

		String postreponse = postres.asString();
		JsonPath js = new JsonPath(postreponse);
		String actualmsg = js.getString("Msg");
		Assert.assertEquals(actualmsg, "Book Already Exists");
		String actualID = js.getString("ID");
		Assert.assertEquals(actualID, "xyz118");
	}

	@Test(priority = 2,description="get book by ID",invocationCount=1)
	public void gettestAPI() {

		/// get book by ID

		Response getres = given().log().all().queryParam("ID", "xyl895").when().get("Library/GetBook.php").then().assertThat()
				.body("book_name",equalTo(bname)).log().all().statusCode(200).extract().response();

		String getreponse = getres.asString();
		JsonPath js = new JsonPath(getreponse);

		String actualname = js.getString("book_name");
		

		// Assert.assertEquals(actualname, "jungle book");

		// String actualisbn = js.getString("isbn");
		// Assert.assertEquals(actualisbn, "xyz");
		// String actualaisle = js.getString("aisle");
		// Assert.assertEquals(actualaisle, "188");
		// String actualauthor = js.getString("author");
		// Assert.assertEquals(actualauthor, "rudyard");

	}

	@Test(priority = 3,description="get book by author",invocationCount=1)
	public void gettestAPI2() {

		// get book by author

		Response getres = given().log().all().queryParam("AuthorName", "rudyard").when().get("Library/GetBook.php")
				.then().assertThat().log().all().statusCode(200).extract().response();

		String getresponse = getres.asString();
		JsonPath js = new JsonPath(getresponse);
		String actualname = js.getString("book_name");
		Assert.assertEquals(actualname, "jungle book");
		String actualisbn = js.getString("isbn");
		Assert.assertEquals(actualisbn, "xyz");
		String actualaisle = js.getString("aisle");
		Assert.assertEquals(actualaisle, "178");

	}
}
