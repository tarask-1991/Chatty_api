import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

public class Login {

    // URL для основного API запиту
    private static final String API_URL = "http://chatty.telran-edu.de:8989/api/me";

    // URL для отримання токена (логін)
    private static final String AUTH_URL = "http://chatty.telran-edu.de:8989/api/auth/login";

    // Логін (електронна пошта) та пароль для отримання токена
    private static final String EMAIL = "Krychkovski@gmail.com";  // Ваша електронна пошта
    private static final String PASSWORD = "Admintest1";           // Ваш пароль

    public static void main(String[] args) {
        try {
            // Отримуємо новий токен
            String token = getAuthToken();
            System.out.println("Token: " + token);  // Виводимо токен для перевірки

            // Виконуємо основний запит з отриманим токеном
            String response = sendGetRequest(API_URL, token);
            System.out.println("Response: " + response);
        } catch (Exception e) {
            // Якщо виникла помилка, виводимо її
            System.err.println("Request failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Метод для отримання токена
    private static String getAuthToken() {
        // Створюємо JSON об'єкт з електронною поштою і паролем
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("email", EMAIL);  // Вставляємо вашу електронну пошту
        jsonBody.put("password", PASSWORD);  // Вставляємо ваш пароль

        // Використовуємо Rest Assured для надсилання POST запиту
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonBody.toString()) // Відправляємо JSON тіло запиту
                .when()
                .post(AUTH_URL);

        // Виводимо відповідь на отримання токена для діагностики
        System.out.println("Auth Response: " + response.body().asString());

        // Перевіряємо, чи успішно отримано токен
        if (response.statusCode() == 200) {
            // Отримуємо правильний токен з поля "accessToken"
            return response.jsonPath().getString("accessToken");
        } else {
            throw new RuntimeException("Failed to authenticate: " + response.statusCode() + " " + response.body().asString());
        }
    }

    // Метод для надсилання GET-запиту з токеном
    private static String sendGetRequest(String url, String token) {
        // Використовуємо Rest Assured для надсилання GET запиту з авторизацією
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)  // Додаємо токен в заголовок
                .when()
                .get(url);

        // Перевіряємо, чи запит був успішним
        if (response.statusCode() == 200) {
            return response.body().asString();
        } else {
            throw new RuntimeException("Request failed: " + response.statusCode() + " " + response.body().asString());
        }
    }
}
