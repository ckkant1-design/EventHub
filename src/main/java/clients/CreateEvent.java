package clients;

import java.io.IOException;

import org.testng.Assert;

import io.restassured.response.Response;
import utils.APIResources;
import utils.JsonUtils;

public class CreateEvent extends BaseClient {
	
	public APIResources resourceAPI;
	public JsonUtils jsonUtils;
	
	public Response createEvent(String resource) throws IOException 
	{
		resourceAPI = APIResources.valueOf(resource);
		jsonUtils = new JsonUtils();
		String payload  = jsonUtils.readJson("createEvent.json");

		Response response = post(resourceAPI.getResource(), payload);

		return response;
	}
	
	public void validateResponseSchema(Response response, String schemaPath) 
	{
		validateSchema(response, schemaPath);
	}
	
	public void validateStatusCode(Response response, int expectedStatusCode) 
	{
		response.then().statusCode(expectedStatusCode);
	}
	
	public void idValidation(Response response, String Id) 
	{
		Integer actualId = getJsonPathInt(response, Id);
		Assert.assertNotNull(actualId, "ID is null in the response");
		
	}

	
	
	

}
