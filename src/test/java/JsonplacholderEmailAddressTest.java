import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class JsonplacholderEmailAddressTest {
    @Test
    public void jsonplacholderCheckEmailsNotEndWithPL()
    {
        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        List<String> emails = json.getList("email");
        assertEquals(10,emails.size());

        List<String> emailsEndingWithPL = emails.stream()
                .filter(email -> email.endsWith(".pl"))
                .collect(Collectors.toList());
        assertTrue(emailsEndingWithPL.isEmpty());


    }
}
