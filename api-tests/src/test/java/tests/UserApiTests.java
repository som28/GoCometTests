package tests;

import base.BaseTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;

@Feature("API Tests")
public class UserApiTests extends BaseTest {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    @Story("Get single user - positive")
    public void getSingleUser() {
        Response response =
                given()
                .when()
                    .get("/api/users/1")
                .then()
                    .statusCode(200)
                    .extract().response();

        Assert.assertEquals(response.jsonPath().getInt("data.id"), 1);
    }

    @Test
    @Story("Get non-existing user - negative")
    public void getUserNotFound() {
        given()
        .when()
            .get("/api/users/999")
        .then()
            .statusCode(404);
    }

    @Test
    @Story("Create user - positive")
    public void createUser() throws IOException {

        File payloadFile = new File("src/test/resources/testdata/createUser.json");
        Map<String, Object> payload = mapper.readValue(payloadFile, Map.class);

        Response response =
                given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                .when()
                    .post("/api/users")
                .then()
                    .statusCode(201)
                    .extract().response();

        Assert.assertNotNull(response.jsonPath().getString("id"));
        Assert.assertEquals(response.jsonPath().getString("name"), payload.get("name"));
    }

    @Test
    @Story("Update user - positive")
    public void updateUser() {

        String payload = """
            {
              "name": "Jane Doe",
              "job": "Product Manager"
            }
        """;

        Response response =
                given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                .when()
                    .put("/api/users/1")
                .then()
                    .statusCode(200)
                    .extract().response();

        Assert.assertEquals(response.jsonPath().getString("name"), "Jane Doe");
    }

    @Test
    @Story("Delete user - edge case")
    public void deleteUser() {
        given()
        .when()
            .delete("/api/users/1")
        .then()
            .statusCode(204);
    }
}

