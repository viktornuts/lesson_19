package apitests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


public class HomeWork19 {

    @Test
    public void chekListUsers() {

        given()
                .filter(withCustomTemplates())
                .log().all()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .statusCode(200)
                .body("total", is(12));
    }

    @Test
    public void chekSingleUser() {

        given()
                .filter(withCustomTemplates())
                .log().all()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.first_name", is("Janet"))
                .body("support.text", is("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test
    public void notFoundSingleUser() {

        given()
                .filter(withCustomTemplates())
                .log().all()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    public void chekListResource() {

        given()
                .filter(withCustomTemplates())
                .log().all()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().all()
                .statusCode(200)
                .body("total", is(12))
                .body("total_pages", is(2))
                .body("data.name[1]", is("fuchsia rose"))
                .body("data.year[1]", is(2001));
    }

    @Test
    public void chekSingleResource() {

        given()
                .filter(withCustomTemplates())
                .when()
                .log().all()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.color", is("#C74375"));
    }

    @Test
    public void singleResourceNotFound() {

        given()
                .filter(withCustomTemplates())
                .log().all()
                .when()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    public void createUser() {

        Map<String, String> user = new HashMap<>();
        user.put("name", "morpheus");
        user.put("job", "leader");

        given()
                .filter(withCustomTemplates())
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    public void updateUser() {

        Map<String, String> user = new HashMap<>();
        user.put("name", "morpheus");
        user.put("job", "zion resident");

        given()
                .filter(withCustomTemplates())
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    public void updateUser2() {

        Map<String, String> user = new HashMap<>();
        user.put("name", "morpheus");
        user.put("job", "zion resident");

        given()
                .filter(withCustomTemplates())
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    public void deleteUser() {

        given()
                .filter(withCustomTemplates())
                .log().all()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    public void registerSuccessful() {

        Map<String, String> user = new HashMap<>();
        user.put("email", "eve.holt@reqres.in");
        user.put("password", "pistol");

        given()
                .filter(withCustomTemplates())
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }


    @Test
    public void registerUnsuccessful() {

        Map<String, String> user = new HashMap<>();
        user.put("email", "sydney@fife");
        user.put("password", "");

        given()
                .filter(withCustomTemplates())
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    public void loginSuccessful() {

        Map<String, String> user = new HashMap<>();
        user.put("email", "eve.holt@reqres.in");
        user.put("password", "cityslicka");

        given()
                .filter(withCustomTemplates())
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void loginUnsuccessful() {

        Map<String, String> user = new HashMap<>();
        user.put("email", "peter@klaven");
        user.put("password", "");

        given()
                .filter(withCustomTemplates())
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    public void delayedResponse() {


        given()
                .filter(withCustomTemplates())
                .log().all()
                .when()
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .log().all()
                .statusCode(200)
                .body("per_page", is(6))
                .body("data.first_name[2]", is("Emma"))
                .body("data.email[2]", is("emma.wong@reqres.in"));
    }

}
