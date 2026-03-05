import config.VideoGameConfig;
import config.VideoGameEndPoints;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class MyFirstTest extends VideoGameConfig {

    @Test
    public void myFirstTest() {
        given()
                .relaxedHTTPSValidation()
                .log().all()
        .when()
                .get("videogame")
        .then()
                .log().all();

    }

    @Test
    public void firstTestWithEndPoints() {
         given()
                .relaxedHTTPSValidation()
                .log().all()
         .when()
                .get(VideoGameEndPoints.ALL_VIDEO_GAMES)
        .then()
                 .log().all();

    }
    /* HTTP Verbs
       GET -> Retrieve data from an endpoint
    *  POST -> Send data to an endpoint
       PUT -> Update a resource
       DELETE -> Delete a resource
    * */

}
