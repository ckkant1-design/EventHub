package stepdefinitions;


import auth.TokenManager;
import clients.AuthClient;
import config.ConfigManager;
import models.LoginRequest;
import models.LoginResponse;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

public class AuthSteps {

    private final AuthClient authClient = new AuthClient();

    private LoginRequest loginRequest;

    private Response response;

    @Given("I have valid EventHub login credentials")
    public void i_have_valid_event_hub_login_credentials() {

        loginRequest = new LoginRequest(ConfigManager.get("email"), ConfigManager.get("password"));

        assertThat(loginRequest.getEmail(), not(emptyOrNullString()));

        assertThat(loginRequest.getPassword(), not(emptyOrNullString()));
    }

    @When("I send a POST request to the login endpoint")
    public void i_send_a_post_request_to_the_login_endpoint() {

        //response = authClient.login(loginRequest);
    	String token =  authClient.loginAndGetToken(loginRequest);


        assertThat(token, notNullValue());

        assertThat(token, not(emptyString()));

        TokenManager.setToken(token);
    }

    @Then("the login response status code should be {int}")
    public void the_login_response_status_code_should_be(Integer expectedStatusCode) {

        assertThat(response.statusCode(), is(expectedStatusCode));
    }

    @Then("the login response schema should be valid")
    public void the_login_response_schema_should_be_valid() 
    {
    	
    	authClient.validateSchema(response, "schemas/login-response-schema.json");
    }

    @Then("the login response should contain a token")
    public void verifyToken() {

               
       //  String token = response.jsonPath().getString("token");
        //String userId = response.jsonPath().getString("userId");
        
       String token =  authClient.loginAndGetToken(loginRequest);


        assertThat(token, notNullValue());

        assertThat(token, not(emptyString()));

        TokenManager.setToken(token);
    }
    
    @Given("I have login credentials {string} and {string}")
    public void loginCredentials(String email, String password) 
    {

        loginRequest = new LoginRequest(email, password);
    }
    
    
    @When("User retrieves {string} authicated user using GET request")
    public void user_retrieves_authicated_user_using_get_request(String resource) {
    	
    	response = authClient.authenticatedUser(resource);
    	
    }
    @Then("User id should be displayed in the response")
    public void user_id_should_be_displayed_in_the_response() {
    	authClient.validateUserId();
        
    }

}

