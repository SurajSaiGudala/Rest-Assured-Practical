package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.BeforeClass;

public class VideoGameConfig {
    @BeforeClass
    public static void setup() {
//        RestAssured.baseURI = "https://videogamedb.uk/";
//        RestAssured.basePath = "api/v2/";
        //Actions to included in every HTTP Request
        /* Headers
        * Cookies
        * Form parameters
        * BaseURL */
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://videogamedb.uk/")
                .setBasePath("api/v2/")
                .setContentType("application/xml")
                .addHeader("Accept","application/xml")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
        //Actions to take AFTER every HTTPS Request
        /*Check Status Code
        * Check content type
        * Response time
        * check headers*/
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();


    }
}
