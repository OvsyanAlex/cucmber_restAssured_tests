package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;

import static io.restassured.RestAssured.given;


public class Steps extends BaseSteps {
    private static final String URL = "https://gorest.co.in/public/v2/users";
    private static final String TOKEN = "bbf587408bd85d8ff70d7fce2c9aaf953ab402bd3cb53f9e1869dbef9b36de9d";
    private static Response response;
    private static String userId;


    @When("creating new active user with name {string}, {string} gender and email {string}" )
    public void createNewUser(String userName, String userGender, String userEmail) {

        String requestBody = new JSONObject().
                put("name", userName).
                put("gender", userGender).
                put("email", userEmail).
                put("status", "active" ).toString();

        response =
                given().
                        headers(
                                "Authorization",
                                "Bearer " + TOKEN,
                                "Content-Type",
                                ContentType.JSON,
                                "Accept",
                                ContentType.JSON).
                        body(requestBody).
                        when().
                        post(URL);

        JsonPath jsonPath = response.jsonPath();
        userId = Integer.toString(jsonPath.get("id" ));
    }

    @Then("we get {int} response code" )
    public void validateCodeResponse(int expectedStatusCode) {
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @And("update user name {string}, {string} gender and email {string}" )
    public void updateUser(String userName, String userGender, String userEmail) {

        String requestBody = new JSONObject().
                put("name", userName).
                put("gender", userGender).
                put("email", userEmail).
                put("status", "active" ).toString();
        response =
                given().
                        headers(
                                "Authorization",
                                "Bearer " + TOKEN,
                                "Content-Type",
                                ContentType.JSON,
                                "Accept",
                                ContentType.JSON).
                        body(requestBody).
                        when().
                        patch(URL + "/" + userId);
    }

    @And("delete user")
    public void deleteUser() {
        response =
                given().
                        headers(
                                "Authorization",
                                "Bearer " + TOKEN).

                        when().
                        delete(URL + "/" + userId);
    }
}
