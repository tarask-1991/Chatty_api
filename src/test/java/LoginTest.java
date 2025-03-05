
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest extends BaseTest {

    private static String accessToken;


    @Test
    public void successLogin() {

        // Создание объекта запроса с логином и паролем
        LoginRequest requestBody = new LoginRequest("Krychk@gmail.com", "Admintest1");
        // Отправка POST-запроса
        Response response = postRequest("/api/auth/login", 200, requestBody );
        response.then().statusCode(200);
        // токен
        LoginResponse loginResponse = response.as(LoginResponse.class);
        accessToken = loginResponse.getAccessToken();

    }
    public String getAccessToken() {
        return accessToken;
    }
}