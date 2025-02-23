import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class MeApiTest extends LoginTest {

    private static final String API_URL = "http://chatty.telran-edu.de:8989/api/me";

    @Test
    public static void main(String[] args) {
        try {
            // Получение токена аутентификации
            String token = getAuthToken();
            System.out.println("Token: " + token);

            // Выполнение API-запроса с полученным токеном
            String response = sendGetRequest(API_URL, token);
            System.out.println("API Response: " + response);

        } catch (Exception e) {
            System.err.println("Запрос не выполнен: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String sendGetRequest(String url, String token) {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(url);

        if (response.statusCode() == 200) {
            // Возвращение необработанного ответа API в виде строки
            return response.body().asString();
        } else {
            throw new RuntimeException("Запрос не выполнен: " + response.statusCode() + " " + response.body().asString());
        }
    }
}
