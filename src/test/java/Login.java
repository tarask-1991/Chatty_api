import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

public class Login {

    // URL для основного API запроса
    private static final String API_URL = "http://chatty.telran-edu.de:8989/api/me";

    // URL для получения токена (логин)
    private static final String AUTH_URL = "http://chatty.telran-edu.de:8989/api/auth/login";

    // Логин (электронная почта) и пароль для получения токена
    private static final String EMAIL = "Krychkovski@gmail.com";  // Ваша электронная почта
    private static final String PASSWORD = "Admintest1";           // Ваш пароль

    public static void main(String[] args) {
        try {
            // Получаем новый токен
            String token = getAuthToken();
            System.out.println("Token: " + token);  // Выводим токен для проверки

            // Выполняем основной запрос с полученным токеном
            String response = sendGetRequest(API_URL, token);
            System.out.println("Response: " + response);
        } catch (Exception e) {
            // Если возникла ошибка, выводим ее
            System.err.println("Request failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Метод для получения токена
    private static String getAuthToken() {
        // Создаем JSON объект с электронной почтой и паролем
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("email", EMAIL);  // Вставляем вашу электронную почту
        jsonBody.put("password", PASSWORD);  // Вставляем ваш пароль

        // Используем Rest Assured для отправки POST запроса
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonBody.toString()) // Отправляем JSON тело запроса
                .when()
                .post(AUTH_URL);

        // Выводим ответ на получение токена для диагностики
        System.out.println("Auth Response: " + response.body().asString());

        // Проверяем, успешно ли получен токен
        if (response.statusCode() == 200) {
            // Получаем правильный токен из поля "accessToken"
            return response.jsonPath().getString("accessToken");
        } else {
            throw new RuntimeException("Failed to authenticate: " + response.statusCode() + " " + response.body().asString());
        }
    }

    // Метод для отправки GET-запроса с токеном
    private static String sendGetRequest(String url, String token) {
        // Используем Rest Assured для отправки GET запроса с авторизацией
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)  // Добавляем токен в заголовок
                .when()
                .get(url);

        // Проверяем, был ли запрос успешным
        if (response.statusCode() == 200) {
            return response.body().asString();
        } else {
            throw new RuntimeException("Request failed: " + response.statusCode() + " " + response.body().asString());
        }
    }
}
