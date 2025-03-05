
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class RegistrationTest {
    @Test
    public void successRegistration() {
        RegistrationRequest requestBody = new RegistrationRequest("p.julia.vv094@gmail.com", "NewUsers007!","NewUsers007!","user");


        SuccessRegistration response = given()
                .baseUri("http://chatty.telran-edu.de:8989")
                .when().log().all()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("api/auth/register")
                .then().log().all()
                .statusCode(201)
                .extract().body().jsonPath().getObject("", SuccessRegistration.class);
        // Проверки
        System.out.println("Email: " + requestBody.getEmail());
        System.out.println("Password: " + requestBody.getPassword());
        System.out.println("Confirm Password: " + requestBody.getConfirmPassword());
        System.out.println("Role: " + requestBody.getRole());

        //Check that token is not empty
        assertFalse(response.getAccessToken().isEmpty());
    }
}
