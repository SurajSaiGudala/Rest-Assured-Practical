import config.VideoGameConfig;
import config.VideoGameEndPoints;
import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.Test;
import io.restassured.path.xml.element.Node;

import java.util.List;

import static io.restassured.RestAssured.*;

public class GPathXMLTests extends VideoGameConfig {
    @Test
    public void getFirstGameInList() {
        Response res =
                given()
                        .relaxedHTTPSValidation()
                .when()
                        .get(VideoGameEndPoints.ALL_VIDEO_GAMES);
        String name = res.path("List.item.name[0]");

        System.out.println("Name of the first-game :: "+name);

    }
    @Test
    public void getAttribute() {
        Response res =
                given()
                        .relaxedHTTPSValidation()
                .when()
                        .get(VideoGameEndPoints.ALL_VIDEO_GAMES);
        String category = res.path("List.item[0].@category");
        System.out.println(category);
    }
    @Test
    public void getListOfXmlNodes() {
        String resAsStr =
                given()
                        .relaxedHTTPSValidation()
                .when()
                        .get(VideoGameEndPoints.ALL_VIDEO_GAMES)
                .then()
                        .extract()
                        .asString();
        List<Node> allResults = XmlPath.from(resAsStr).get(
                "List.item.findAll { e -> return e }"
        );
        System.out.println(allResults);
        System.out.println(allResults.get(1).get("name").toString());
    }
    @Test
    public void extractNodesWithAttribute() {
        String resAsStr =
                given()
                        .relaxedHTTPSValidation()
                .when()
                        .get(VideoGameEndPoints.ALL_VIDEO_GAMES)
                .then()
                        .extract()
                        .asString();
        List<Node> allDrivingGames = XmlPath.from(resAsStr).get(
                "List.item.findAll { game -> def category = game.@category; category == 'Driving' }"
        );

        System.out.println(allDrivingGames.get(1).get("name").toString());

    }
    @Test
    public void getSingleNode() {
        String resAsStr =
                given()
                        .relaxedHTTPSValidation()
                .when()
                        .get(VideoGameEndPoints.ALL_VIDEO_GAMES)
                .then()
                        .extract()
                        .asString();
        Node videoGame = XmlPath.from(resAsStr).get(
                "List.item.find { game -> def name = game.name; name == 'Tetris' }"
        );
        System.out.println(videoGame.get("name").toString());
    }
    @Test
    public void getSingleElementDepthFirstSearch() {
        String resAsStr =
                given()
                        .relaxedHTTPSValidation()
                .when()
                        .get(VideoGameEndPoints.ALL_VIDEO_GAMES)
                .then()
                        .extract()
                        .asString();
        int reviewScore = XmlPath.from(resAsStr).getInt(
                "**.find { n -> n.name == 'Gran Turismo 3' }.reviewScore"
        );
        System.out.println("ReviewScore ::: "+reviewScore);
    }
    @Test
    public void getAllNodesBasedOnCondition() {
        String resAsStr =
                given()
                        .relaxedHTTPSValidation()
                .when()
                        .get(VideoGameEndPoints.ALL_VIDEO_GAMES)
                .then()
                        .extract()
                        .asString();
        int reviewScore = 90;
        List<Node> allVideoGamesOverCertainScore = XmlPath.from(resAsStr).get(
                "List.item.findAll { score -> score.reviewScore.toFloat() >= "+reviewScore+" }"
        );
        for(Node n : allVideoGamesOverCertainScore)
            System.out.println(n);
    }
}
