package steps;

import dto.User;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import static io.restassured.RestAssured.given;


public class Steps extends BaseSteps {
    private static final String URL = "https://gorest.co.in/public/v2/users";
    private static final String TOKEN = "fd45f8777888f99aba67f10b791e42171c3de1bbb7be6e3325f187a3ad5c7473";
    private static Response response;
    private static String userId;
    private static final String status = "active";
    private static String requestBody;

    @When("creating new active user with name {string}, {string} gender and email {string}")
    public void createNewUser(String userName, String userGender, String userEmail) {

        requestBody = new User(userName, userGender, userEmail, status).toString();

        response = prepareRequest().post(URL);

        JsonPath jsonPath = response.jsonPath();
        userId = Integer.toString(jsonPath.get("id"));
    }

    @Then("we get {int} response code")
    public void validateCodeResponse(int expectedStatusCode) {
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @And("update user name {string}, {string} gender and email {string}")
    public void updateUser(String userName, String userGender, String userEmail) {

         requestBody = new User(userName, userGender, userEmail, status).toString();

        response = prepareRequest().patch(URL + "/" + userId);
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

    private RequestSpecification prepareRequest() {
        return given().
                headers(
                        "Authorization",
                        "Bearer " + TOKEN,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON).
                body(requestBody).
                when();
    }
}
