package clients;




import org.testng.Assert;

import auth.TokenManager;
import config.ConfigManager;
import models.LoginRequest;
import models.LoginResponse;
import utils.APIResources;
import io.restassured.response.Response;

public class AuthClient extends BaseClient 
{
	
	public APIResources resourceAPI;
	
	

    public Response login(LoginRequest loginRequest) 
    {

        return post(ConfigManager.get("auth.login.endpoint"),  loginRequest);
    }

    public String loginAndGetToken(LoginRequest loginRequest) 
    {

        Response response = login(loginRequest);

        String token = response.jsonPath().getString("token");

        TokenManager.setToken(token);

        return token;
    }
    
    public Response loginAndValidate(LoginRequest loginRequest) 
    {

        Response response = login(loginRequest);

        validateSchema(response, "schemas/login-response-schema.json");

        return response;
    }
    
    public Response authenticatedUser(String resource) 
    {
    	
    	resourceAPI = APIResources.valueOf(resource);

    	Response response = get(resourceAPI.getResource());

		return response;
	}
    
    public void validateUserId() 
    {
		
		Response response = authenticatedUser("GetAuthAPI");
		int userid = getJsonPathInt(response, "user.userId");
		Assert.assertNotNull(userid, "User ID is null");
		
		
	}
	

}

