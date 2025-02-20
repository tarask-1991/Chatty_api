import io.restassured.RestAssured;
import io.restassured.response.Response;

public class MeApiTest extends LoginTest {

    // URL for the main API request
    private static final String API_URL = "http://chatty.telran-edu.de:8989/api/me";

    public static void main(String[] args) {
        try {
            // Get the authentication token from the parent class
            String token = getAuthToken();
            System.out.println("Token: " + token);  // Print token for verification

            // Perform the API request with the obtained token
            String response = sendGetRequest(API_URL, token);
            System.out.println("API Response: " + response);
        } catch (Exception e) {
            // If an error occurs, print it
            System.err.println("Request failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to send a GET request with the provided token
    private static String sendGetRequest(String url, String token) {
        // Use Rest Assured to send the GET request with the token in the Authorization header
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(url);

        // Check if the request was successful
        if (response.statusCode() == 200) {
            return response.body().asString();
        } else {
            throw new RuntimeException("Request failed: " + response.statusCode() + " " + response.body().asString());
        }
    }
}
