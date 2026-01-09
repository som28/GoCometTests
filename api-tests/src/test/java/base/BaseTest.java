package base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    @BeforeClass
    public void setup() {

        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("Accept", "application/json")
                .setRelaxedHTTPSValidation()
                .build();

        RestAssured.requestSpecification = requestSpec;
    }
}
