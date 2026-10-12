package clients;

import java.util.List;

import org.testng.Assert;

import io.restassured.response.Response;
import utils.APIResources;

public class RetrieveEvent extends BaseClient{
	
	
	public Response retrieveEvent(String resource) 
	{
		// Implement the logic to retrieve events using the provided resource
		
		APIResources resourceAPI = APIResources.valueOf(resource);
		
		Response response = get(resourceAPI.getResource());
		
		return response;
		
	}
	
	public void validateResponseSchema(Response response, String schemaPath) 
	{
		validateSchema(response, schemaPath);
	}
	
	public void idValidation(Response response, String Id) 
	{
		List<Integer> actualId = getJsonPathList(response, Id, Integer.class);
		Assert.assertNotNull(actualId, "ID is null in the response");
		
	}
	
	public Response retrieveEventById(String resource, String id, String idValue) 
	{
		// Implement the logic to retrieve events using the provided resource
		
		APIResources resourceAPI = APIResources.valueOf(resource);
		
		Response response = getPathParam(resourceAPI.getResource(), id, idValue);
		
		return response;
		
	}

}
