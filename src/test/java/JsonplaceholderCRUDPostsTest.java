import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class JsonplaceholderCRUDPostsTest {

    private String BASE_URL = "https://jsonplaceholder.typicode.com";
    private String POSTS = "posts";
    private static Faker faker;
    private Integer fakeUserId;
    private String fakeTitle;
    private String fakeBody;

    @BeforeAll
    public static void beforeAll() {
        faker = new Faker();
    }

    @BeforeEach
    public void beforeEach()
    {
        fakeUserId = faker.number().randomDigit();
        fakeTitle = faker.lorem().sentence();
        fakeBody = faker.lorem().sentence();
    }

    //Read all posts - GET
    @Test
    public void jsonplaceholderGETAllPosts() {
        Response response = given()
                .when()
                .get(BASE_URL + "/" + POSTS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<Integer> posts = json.getList("id");
        Assertions.assertEquals(100,posts.size());
    }

    @Test
    public void jsonplaceholderGETOnePost() {
        Response response = given()
                .pathParam("id", 1)
                .when()
                .get(BASE_URL + "/" + POSTS + "/" + "{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        System.out.println(response.asString());
        Assertions.assertEquals(1, (Integer) json.get("userId"));
        Assertions.assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", json.get("title"));
        Assertions.assertEquals("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto", json.get("body"));
    }

    //Create post
    @Test
    public void jsonplaceholderPOSTPost() {
        JSONObject post = new JSONObject();
        post.put("userId",fakeUserId);
        post.put("title",fakeTitle);
        post.put("body",fakeBody);
        Response response = given().
                contentType("application/json")
                .body(post.toString())
                .when()
                .post(BASE_URL + "/" + POSTS)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(fakeUserId, json.get("userId"));
        Assertions.assertEquals(fakeTitle, json.get("title"));
        Assertions.assertEquals(fakeBody, json.get("body"));
    }

    // Update post - PUT
    @Test
    public void jsnoplaceholderPUTPostTest() {

        JSONObject post = new JSONObject();
        post.put("userId",fakeUserId);
        post.put("title",fakeTitle);
        post.put("body",fakeBody);

        Response response = given()
                .pathParam("id", 1)
                .contentType("application/json")
                .body(post.toString())
                .when()
                .put(BASE_URL + "/" + POSTS + "/" + "{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        System.out.println(response.asString());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(fakeUserId, json.get("userId"));
        Assertions.assertEquals(fakeTitle, json.get("title"));
        Assertions.assertEquals(fakeBody, json.get("body"));
    }

    // Update post - PATCH
    @Test
    public void jsnoplaceholderPATCHPostTest() {
        JSONObject post = new JSONObject();
        post.put("userId",fakeUserId);
        post.put("title",fakeTitle);
        post.put("body",fakeBody);

        Response response = given()
                .pathParam("id", 1)
                .contentType("application/json")
                .body(post.toString())
                .when()
                .patch(BASE_URL + "/" + POSTS + "/" + "{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(fakeUserId, json.get("userId"));
        Assertions.assertEquals(fakeTitle, json.get("title"));
        Assertions.assertEquals(fakeBody, json.get("body"));
    }
    // Delete Album
    @Test
    public void jsnoplaceholderDELETEPostTests() {
        Response response = given()
                .pathParam("id", 1)
                .when()
                .delete(BASE_URL + "/" + POSTS + "/" + "{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
    }
}
