import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ApiTest {

    @Test
    void listUsersTest() {
        given()
                .log().uri()
                .log().method()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(12))
                .body("data[0].id", is(7))
                .body("data[1].first_name", is("Lindsay"));
    }

    @Test
    void listResourceTest() {
        given()
                .log().uri()
                .log().method()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data[0].year", is(2000))
                .body("data[2].color", is("#BF1932"));
    }

    @Test
    void createTest() {
        given()
                .log().uri()
                .log().method()
                .body("{\n" + "\"name\": \"morpheus\",\n" + "\"job\": \"leader\"\n" + "}")
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    void successfulRegisterTest() {
        given()
                .log().uri()
                .log().method()
                .body("{\n" + "\"email\": \"eve.holt@reqres.in\",\n" + "\"password\": \"pistol\"\n" + "}")
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void unsuccessfulRegisterTest() {
        given()
                .log().uri()
                .log().method()
                .body("{\n" + "\"email\": \"sydney@fife\"\n" + "}")
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
