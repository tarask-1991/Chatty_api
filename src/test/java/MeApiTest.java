import io.restassured.response.Response;
import org.junit.Test;


public class MeApiTest extends BaseTest {
    private static String accessToken;;

    @Test
    public void testGetMe() {
        LoginTest loginTest = new LoginTest();
        loginTest.successLogin(); // Вызываем логин
        accessToken = loginTest.getAccessToken(); // Получаем токен
        Response response = getRequestWithToken("/api/me", 200, accessToken);

    }
}