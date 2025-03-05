import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class UserControllerApi extends BaseTest{

    private String accessToken; //  переменная токена

    @Test
    public void getUserIdPosts() {
        LoginTest loginTest = new LoginTest();
        loginTest.successLogin(); // Логинимся
        accessToken = loginTest.getAccessToken();

        // Выполняем GET-запрос для получения информации о пользователе
        Response response = getRequestWithToken("/api/me", 200, accessToken);

        // Получаем userId из ответа
        String userId = response.jsonPath().getString("id");

        // Выполняем GET-запрос для получения постов пользователя
        Response postsResponse = getRequestWithToken("/api/users/" + userId + "/posts", 200, accessToken);

        System.out.println("Posts Response:" + postsResponse);
        assertNotNull(postsResponse);
        assertEquals(200, postsResponse.getStatusCode());

        // Используем метод asString() для получения тела ответа в виде строки
        String responseBody = postsResponse.getBody().asString();

        assertNotNull(responseBody, "Response body should not be null");
        assertFalse(responseBody.isEmpty(), "Response body should not be empty");

        System.out.println("User Posts Response: " + responseBody);
    }




    @Test
    public void putUserPasswordUpdate() {
        // 1. Логинимся и получаем access token
        LoginTest loginTest = new LoginTest();
        loginTest.successLogin();
        accessToken = loginTest.getAccessToken();

        // 2. Создаём объект запроса с паролями (старый и новый пароль)
        PasswordUpdateRequest passwordUpdateRequest = new PasswordUpdateRequest(
                "Admintest2",  // Старый пароль
                "Admintest1",  // Новый пароль
                "Admintest1"   // Подтверждение нового пароля
        );

        // 3. Отправляем PUT-запрос на обновление пароля
        Response response = putRequestWithToken("/api/user/password/update", 200, passwordUpdateRequest, accessToken);

        // Логируем статус-код и тело ответа для отладки
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();
        System.out.println("Status code: " + statusCode);
        System.out.println("Response body: '" + responseBody + "'");  // Выводим кавычки, чтобы увидеть, если тело пустое

        // 4. Проверяем, что ответ не пустой
        assertNotNull(response); // Проверка, что объект Response не null
        assertEquals(200, statusCode, "Status code should be 200");

        // 5. Проверяем, что тело ответа не пустое
//        assertNotNull(responseBody, "Response body should not be null");
//        assertFalse(responseBody.isEmpty(), "Response body should not be empty");
   }




    @Test
    public void putApiUsers() {

        // 1. Логинимся и получаем access token
        LoginTest loginTest = new LoginTest();
        loginTest.successLogin();
        accessToken = loginTest.getAccessToken();

        // 2. Получаем информацию о пользователе (например, ID)
        Response response = getRequestWithToken("/api/me", 200, accessToken);
        String userId = response.path("id");
        assertNotNull(userId, "User ID should not be null");

        // 3. Создаём объект запроса с обновлёнными данными пользователя
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest(
                "Taras",                   // Имя
                "Taras",                   // Логин
                "Surname",                 // Фамилия
                "1999-02-19T11:15:49.875Z", // Дата рождения
                "+45758887777",            // Телефон
                "MALE",                    // Пол
                "string",                  // Другое поле
                true);                     // Дополнительное поле

        // 4. Отправляем PUT-запрос для обновления данных пользователя
        Response updateResponse = putRequestWithToken("/api/users/" + userId, 200, userUpdateRequest, accessToken);

        // 5. Проверяем статус-код ответа
        assertNotNull(String.valueOf(updateResponse), "Update response should not be null");
        int statusCode = updateResponse.getStatusCode();
        assertEquals(200, statusCode, "Expected status code 200 but got " + statusCode);

        // 6. Логируем ответ для отладки
        String responseBody = updateResponse.getBody().asString();
        System.out.println("Updated User Response: " + responseBody);

        // 7. Проверяем тело ответа
        assertNotNull(responseBody, "Response body should not be null");
        assertFalse(responseBody.isEmpty(), "Response body should not be empty");
    }

    }










