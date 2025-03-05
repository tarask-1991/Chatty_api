import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseTest {

    static String BASE_URI = "http://chatty.telran-edu.de:8989";

    static RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .setContentType(ContentType.JSON)
            .build();


    public static Response postRequest(String endPoint, Integer expectedStatusCode, Object body) {
        Response response = given()
                .spec(requestSpecification)
                .body(body)
                .when()
                .log().all()
                .post(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }



    // GET
    public static Response getRequest(String endPoint, Integer expectedStatusCode, String token) {
        Response response = given()
                .spec(requestSpecification)
                .header("Authorization", "Bearer " + token)
                .when()
                .log().all()
                .get(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    // PUT
    public static Response putRequest(String endPoint, Integer expectedStatusCode, Object body, String token) {
        Response response = given()
                .spec(requestSpecification)
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .log().all()
                .put(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    // DELETE
    public static Response deleteRequest(String endPoint, Integer expectedStatusCode, String token) {
        Response response = given()
                .spec(requestSpecification)
                .header("Authorization", "Bearer " + token)
                .when()
                .log().all()
                .delete(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }


    public static Response getRequestWithToken(String endPoint, Integer expectedStatusCode, String token) {
        Response response = given()
                .spec(requestSpecification)
                .when()
                .header("Authorization", "Bearer " + token)
                .log().all()
                .get(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();

        return response;
    }


    // Метод для PUT-запроса с токеном
    public static Response putRequestWithToken(String endPoint, Integer expectedStatusCode, Object body,String token){
        Response response = given()
                .spec(requestSpecification)
                .body(body)  // Тело запроса
                .when()
                .header("Authorization", "Bearer " + token)  // Заголовок с токеном
                .log().all()
                .put(endPoint)  //  PUT-запрос
                .then()
                .log().all()  // Логируем ответ
                .statusCode(expectedStatusCode)
                .extract().response();  // Извлекаем и возвращаем ответ
        return response;
    }
}