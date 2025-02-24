import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

public class UserPosts extends LoginTest {

    // URL для основного запроса API
    private static final String API_URL = "http://chatty.telran-edu.de:8989/api/me";
    private static final String POSTS_API_BASE_URL = "http://chatty.telran-edu.de:8989/api/users/";

    public static void main(String[] args) {
        try {
            // Получаем токен аутентификации
            String token = LoginTest.getAuthToken();
            System.out.println("Token: " + token);

            // Вызываем API /me для получения информации о пользователе
            String meResponse = sendGetRequest(API_URL, token);
            System.out.println("User Info Response: " + meResponse);

            // Парсим JSON-ответ для получения userId
            JSONObject jsonResponse = new JSONObject(meResponse);
            String userId = jsonResponse.getString("id");  // Получаем userId
            System.out.println("User ID: " + userId);

            // Вызываем API для получения постов пользователя
            String postsResponse = getUserPosts(userId, token);
            System.out.println("User Posts Response: " + postsResponse);

        } catch (Exception e) {
            // Если возникла ошибка, выводим её
            System.err.println("Запрос не выполнен: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Метод для получения постов пользователя по его id
    private static String getUserPosts(String userId, String token) {
        // Формируем URL для запроса постов пользователя
        String url = POSTS_API_BASE_URL + userId + "/posts";

        // Используем RestAssured для отправки GET-запроса с токеном в заголовке
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(url);

        // Проверяем, успешен ли запрос
        if (response.statusCode() == 200) {
            return response.body().asString();
        } else {
            throw new RuntimeException("Не удалось получить посты пользователя: " + response.statusCode() + " " + response.body().asString());
        }
    }

    // Метод для получения информации о пользователе
    private static String sendGetRequest(String url, String token) {
        // Используем RestAssured для отправки GET-запроса с токеном в заголовке
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(url);

        // Проверяем, успешен ли запрос
        if (response.statusCode() == 200) {
            return response.body().asString();
        } else {
            throw new RuntimeException("Request failed: " + response.statusCode() + " " + response.body().asString());
        }
    }
}
