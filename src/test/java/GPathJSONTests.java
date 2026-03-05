import config.FootballAPIConfig;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class GPathJSONTests extends FootballAPIConfig {
    @Test
    public void extractMapOfElementsWithFind() {
        Response res =
                given()
                        .relaxedHTTPSValidation()
                .when()
                        .get("competitions/2021/teams");
        Map<String, ?> allTeamDataForSingleTeam = res.path("teams.find { t -> t.name == 'Hamburger SV'}");

        System.out.println("Map of all teams data \n" + allTeamDataForSingleTeam);
    }
    @Test
    public void extractSingleValueWithFind() {
        Response res =
                given()
                        .relaxedHTTPSValidation()
                .when()
                        .get("teams/57");
        String certianPlayer = res.path("squad.find { p -> p.id == 4832 }.name");
        System.out.println("Name of the player : "+certianPlayer);
    }
    @Test
    public void extractEveryValueWithFindAll() {
        Response res =
                given()
                        .relaxedHTTPSValidation()
                .when()
                        .get("teams/57");
        List<String> players = res.path("squad.findAll { e -> e.id > 2000 }.name");
        System.out.println("Names of the player ...\n"+players);
    }
    @Test
    public void extractSingleValueWithHighestNumber() {
        Response res =
                given()
                        .relaxedHTTPSValidation()
                        .when()
                        .get("teams/57");
        String pname = res.path("squad.max { i -> i.id }.name");
        System.out.println("Player with the higheset id :: "+pname);
    }
    @Test
    public void extractMultipleValueAndSumThem() {
        Response res =
                given()
                        .relaxedHTTPSValidation()
                        .when()
                        .get("teams/57");
        int sumOfIds = res.path("squad.collect { i -> i.id }.sum()");
        System.out.println("Sum of the All Id's :: "+sumOfIds);
    }
    @Test
    public void findPlayerWithCertainPositionAndNationality() {
        String pos = "Offence";
        String nationality = "England";
        Response res =
                given()
                        .relaxedHTTPSValidation()
                        .when()
                        .get("teams/57");
        Map<String, ?> playerOfCertainPosition = res.path("" +
                "squad.findAll { p -> p.position == '%s' }.find { i -> i.nationality == '%s' }",pos, nationality);
        System.out.println("\n\nName of the player :: "+playerOfCertainPosition);
    }

    @Test
    public void allPlayerWithCertainPositionAndNationality() {
        String pos = "Offence";
        String nationality = "England";
        Response res =
                given()
                        .relaxedHTTPSValidation()
                        .when()
                        .get("teams/57");
        ArrayList<Map<String, ?>> playerOfCertainPosition = res.path("" +
                "squad.findAll { p -> p.position == '%s' }.findAll { i -> i.nationality == '%s' }",pos, nationality);
        System.out.println("\n\nName of the player :: "+playerOfCertainPosition);
    }
    }


