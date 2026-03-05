import config.VideoGameConfig;
import config.VideoGameEndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.matcher.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import objects.VideoGame;
import org.junit.Test;
import org.hamcrest.*;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.lessThan;

/*
* HTTP Verbs
* GET -> Retrieve data from an endpoint
* POST -> Send data to an endpoint
* PUT -> Update a resource
* DELETE -> Delete a resource
* */
public class VideoGameTests extends VideoGameConfig {

    String gameBody = "{\n" +
            "  \"category\": \"Platform\",\n" +
            "  \"name\": \"Mario\",\n" +
            "  \"rating\": \"Mature\",\n" +
            "  \"releaseDate\": \"2012-05-04\",\n" +
            "  \"reviewScore\": 85\n" +
            "}";

    @Test
    public void getAllGames() {
        given()
                .relaxedHTTPSValidation()
        .when()
                .get(VideoGameEndPoints.ALL_VIDEO_GAMES)
        .then();
    }
    @Test
    public void createNewGameByJSON() {

        given()
                .relaxedHTTPSValidation()
                .body(gameBody)
        .when()
                .post(VideoGameEndPoints.ALL_VIDEO_GAMES)
        .then();
    }

    @Test
    public void createNewGameByXML() {
        String gameBodyXml = "<VideoGame>\n" +
                "    <category>Platform</category>\n" +
                "    <name>Mario</name>\n" +
                "    <rating>Mature</rating>\n" +
                "    <releaseDate>2012-05-04</releaseDate>\n" +
                "    <reviewScore>85</reviewScore>\n" +
                "</VideoGame>";
        given()
                .relaxedHTTPSValidation()
                .body(gameBodyXml)
        .when()
                .post(VideoGameEndPoints.ALL_VIDEO_GAMES)
        .then();

    }

    @Test
    public void updateGame() {
        given()
                .relaxedHTTPSValidation()
                .body(gameBody)
        .when()
                .put("videogame/7")
        .then();
    }

    @Test
    public void deleteGame() {
        given()
                .relaxedHTTPSValidation()
                .accept("text/plain")
        .when()
                .delete("videogame/2")
        .then();

    }
// Getting a data of a single video-game using path params
    @Test
    public void getSingleVideGame() {
        given()
                .relaxedHTTPSValidation()
                .pathParam("videoGameId",4)
        .when()
                .get(VideoGameEndPoints.SINGLE_VIDEO_GAME)
        .then();
    }
    // Videogame Serialization
    @Test
    public void testVideoGameSerializationByJSON() {
        VideoGame videoGame = new VideoGame(
                "Shooter",
                "MyAwesomeGame",
                "PG-13",        // or whatever your API expects
                "2018-04-04",
                99
        );
        given()
                .relaxedHTTPSValidation()
                .body(videoGame)
        .when()
                .post(VideoGameEndPoints.ALL_VIDEO_GAMES)
        .then();
    }
    //Deserialization
    @Test
    public void testVideoGameDeserializationByObject() {
        Response res =
                given()
                        .relaxedHTTPSValidation()
                        .pathParams("videoGameId",4)
                .when()
                        .get(VideoGameEndPoints.SINGLE_VIDEO_GAME);
        VideoGame videogame = res.getBody().as(VideoGame.class);

        System.out.println(videogame.toString());

    }
    @Test
    public void getAllDataAndCheck() {
        Response res =
                given()
                        .relaxedHTTPSValidation()
                        .pathParams("videoGameId",5)
                .when()
                        .get(VideoGameEndPoints.SINGLE_VIDEO_GAME)
                .then()
                        .contentType(ContentType.JSON)
                        .extract().response();
        String resAsStr = res.toString();
        System.out.println(resAsStr);
    }

    @Test
    public void getListOfGames() {
        Response res =
                given()
                        .relaxedHTTPSValidation()
                .when()
                        .get(VideoGameEndPoints.ALL_VIDEO_GAMES)
                .then()
                        .extract().response();
        List<String> games = res.path("name");
        for(String game : games)
            System.out.println(game);
    }
    @Test
    public void videoGameSerialization() {
        VideoGame videogame = new VideoGame(
                "shootergame",
                "MySuperBestGame",
                "MG-69",
                "2019-06-05",
                99
        );
        given()
                .relaxedHTTPSValidation()
                .body(videogame)
        .when()
                .put("videogame/8")
        .then();
    }
    //Deserialization - Assessment
    //Schema Validation - Pending
//    @Test
//    public void testSchemaValidation() {
//        given()
//                .relaxedHTTPSValidation()
//                .pathParams("videoGameId",5)
//        .when()
//                .get(VideoGameEndPoints.)
//    }
    @Test
    public void getResponseTime() {
        Long resTime = given()
                .relaxedHTTPSValidation()
        .when()
                .get(VideoGameEndPoints.ALL_VIDEO_GAMES).time();
        System.out.println(resTime);
    }
    @Test
    public void assertResponseTime() {
        given()
                .relaxedHTTPSValidation()
        .when()
                .get(VideoGameEndPoints.ALL_VIDEO_GAMES)
        .then()
                .time(lessThan(1_000l));
    }
    @Test
    public void testVideoGameSchemaXML() {
        given()
                .relaxedHTTPSValidation()
                .pathParams("videoGameId",6)
                .accept("application/xml")
        .when()
                .get(VideoGameEndPoints.SINGLE_VIDEO_GAME)
        .then()
                .body(RestAssuredMatchers.matchesXsdInClasspath("VideoGameXSD.xsd"));

    }
    @Test
    public void testVideoGameSchemaJSON() {
        given()
                .relaxedHTTPSValidation()
                .pathParams("videoGameId", 8)
                .accept("application/json")
        .when()
                .get(VideoGameEndPoints.SINGLE_VIDEO_GAME)
        .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("VideoGameJSONSchema.json"));
    }

    @Test
    public void captureResponseTime() {
        long resTime =
                given()
                        .relaxedHTTPSValidation()
                .when()
                        .get(VideoGameEndPoints.ALL_VIDEO_GAMES)
                        .time();
        System.out.println("Time Taken (MS) :: "+resTime);
    }
    @Test
    public void assertResponseTiming() {
        given()
                .relaxedHTTPSValidation()
        .when()
                .get(VideoGameEndPoints.ALL_VIDEO_GAMES)
                .then().time(lessThan(10000L));
    }

}
