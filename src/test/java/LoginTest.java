import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

public class LoginTest {

    // URL for authentication API
    private static final String AUTH_URL = "http://chatty.telran-edu.de:8989/api/auth/login";

    // Login credentials (email and password)
    private static final String EMAIL = "Krychkovski@gmail.com";
    private static final String PASSWORD = "Admintest1";

    // Method to get the authentication token
    public static void main(String[] args) {
        try {
            String token = getAuthToken();  // Get the token
            System.out.println("Token: " + token);  // Print the token for verification
        } catch (Exception e) {
            System.err.println("Failed to get the token: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to get the authentication token
    public static String getAuthToken() {
        // Create a JSON object with email and password
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("email", EMAIL);
        jsonBody.put("password", PASSWORD);

        // Use Rest Assured to send the POST request
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonBody.toString())
                .when()
                .post(AUTH_URL);

        // Print response for debugging
        System.out.println("Auth Response: " + response.body().asString());

        // Check if token is successfully obtained
        if (response.statusCode() == 200) {
            // Extract and return the token from the "accessToken" field
            return response.jsonPath().getString("accessToken");
        } else {
            throw new RuntimeException("Failed to authenticate: " + response.statusCode() + " " + response.body().asString());
        }
    }
}
