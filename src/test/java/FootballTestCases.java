import config.FootballAPIConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class FootballTestCases extends FootballAPIConfig {

    @Test
    public void getDetailsOfOneArea() {
        given()
                .relaxedHTTPSValidation()
                .queryParam("areas",2040)
        .when()
                .get("/areas")
        .then();
    }

    @Test
    public void getAllDetailsMultipleIds() {
        String areaIds = "2026,2027,2028";
        given()
                .relaxedHTTPSValidation()
                .urlEncodingEnabled(true)
                .queryParam("areas",areaIds)
        .when()
                .get("/areas");
    }
// Asserting the data
    @Test
    public void getDateFounded() {
        given()
                .relaxedHTTPSValidation()
        .when()
                .get("teams/57")
        .then()
                .body("founded",equalTo(1886));
    }

    @Test
    public void getFirstTeamName() {
        given()
                .relaxedHTTPSValidation()
        .when()
                .get("competitions/2021/teams")
        .then()
                .body("teams[0].name",equalTo("Arsenal FC"));
    }

    // Extract the body of the HTTP Response
    @Test
    public void getAllTeamData() {
        String responseBody =
                given()
                        .relaxedHTTPSValidation()
                        .get("teams/57")
                        .asString();
        System.out.println(responseBody);
    }

    @Test
    public void getAllTeamData_DoCheckFirst() {
        Response res =
                given()
                        .relaxedHTTPSValidation()
                .when()
                        .get("teams/57")
                .then()
                        .contentType(ContentType.JSON)
                        .extract().response();
        String resStr = res.asString();
        System.out.println(resStr);
    }
    // Extracting headers from HTTP Response
    @Test
    public void getHeaders() {
        Response res =
                given()
                        .relaxedHTTPSValidation()
                .when()
                        .get("teams/57")
                .then()
                        .extract().response();

        String conTypeHeader = res.getContentType();
        System.out.println(conTypeHeader);
        String apiVersionHeader = res.getHeader("X-API-Version");
        System.out.println(apiVersionHeader);
    }

    // Extract explicit data from HTTP Response
    // Using REST Assured JSON DataPath
    @Test
    public void extractFirstTeamName() {
        String firstTeamName =
                given()
                        .relaxedHTTPSValidation()
                .when()
                        .get("competitions/2021/teams")
                        .jsonPath().getString("teams.name[0]");
        System.out.println(firstTeamName);
    }
    @Test
    public void extractAllTeamNames() {
        Response res =
                given()
                        .relaxedHTTPSValidation()
                .when()
                        .get("competitions/2021/teams")
                .then()
                        .extract().response();
        List<String> teamNames = res.path("teams.name");

        for(String teamName : teamNames)
            System.out.println(teamName);

    }
}
