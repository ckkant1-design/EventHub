package clients;

import auth.TokenManager;
import utils.AllureUtils;
import utils.RequestSpecFactory;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BaseClient {

	protected RequestSpecification request() 
	{

		RequestSpecification specification = given().spec(RequestSpecFactory.getRequestSpec());

		String token = TokenManager.getToken();

		if (token != null && !token.isBlank()) {

			specification.header("Authorization", "Bearer " + token);
		}

		return specification;
	}

	protected Response post(String endpoint, Object requestBody) 
	{
		RequestSpecification specification = request();
		attachCurl("POST", endpoint, specification);
		attachRequest(requestBody);

		Response response = request().body(requestBody).when().post(endpoint).then().extract().response();

		attachResponse(response);

		return response;
	}

	protected Response get(String endpoint) 
	{
		RequestSpecification specification = request();
		Response response = request().when().get(endpoint).then().extract().response();
		attachCurl("GET", endpoint, specification);
		attachResponse(response);

		return response;
	}

	protected Response put(String endpoint, Object requestBody) 
	{

		attachRequest(requestBody);
		
		Response response = request().body(requestBody).when().put(endpoint).then().extract().response();

		attachResponse(response);

		return response;
	}

	protected Response delete(String endpoint) 
	{

		Response response = request().when().delete(endpoint).then().extract().response();

		attachResponse(response);

		return response;
	}

	private void attachRequest(Object requestBody) 
	{

		if (requestBody != null) {

			// String body = requestBody.toString();

			// AllureUtils.attach("Request Body", body);
			AllureUtils.attachJson("Request Body", requestBody);
		}
	}

	private void attachResponse(Response response) 
	{

		if (response != null) {
			AllureUtils.attachJson("Response Body", response.asPrettyString());
		}
	}

	private void attachResponses(Response response) 
	{
		AllureUtils.attachJson("Response Body", response.asPrettyString());

		// AllureUtils.attach("Response Body", response.asPrettyString());

		// AllureUtils.attach("Response Headers", response.getHeaders().toString());

		// AllureUtils.attach("Response Status", String.valueOf(response.statusCode()));
	}

	public void validateSchema(Response response, String schemaPath) 
	{

		response.then().assertThat()
				.body(io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
	}

	private void attachCurl(String method, String endpoint, RequestSpecification specification) {

		StringBuilder curl = new StringBuilder();

		curl.append("curl --location --request ").append(method).append(" '").append(endpoint).append("'");

		// Headers
		if (specification instanceof io.restassured.internal.RequestSpecificationImpl reqSpec) {

			Map<String, String> headers = reqSpec.getHeaders().asList().stream()
					.collect(Collectors.toMap(Header::getName, Header::getValue, (a, b) -> b));

			for (Map.Entry<String, String> header : headers.entrySet()) {

				String value = header.getValue();

				// Mask token in Allure
				if ("Authorization".equalsIgnoreCase(header.getKey())) {
					value = "Bearer ********";
				}

				curl.append(" \\\n  --header '").append(header.getKey()).append(": ").append(value).append("'");
			}
		}

		AllureUtils.attach("cURL", curl.toString());
	}
	
	protected Response getPathParam(String endpoint, String key, String value) 
	{
		RequestSpecification specification = request();
		Response response = request().pathParam(key, value).when().get(endpoint).then().extract().response();
		attachCurl("GET", endpoint, specification);
		attachResponse(response);

		return response;
	}

	public String getJsonPathString(Response response, String key) {
		JsonPath js = new JsonPath(response.asString());
		String value = js.getString(key);
		return value;
	}

	public Integer getJsonPathInt(Response response, String key) {
		JsonPath js = new JsonPath(response.asString());
		int value = js.getInt(key);
		return value;
	}
	
	public List getJsonPathList(Response response, String key, Class<Integer> class1) {
		JsonPath js = new JsonPath(response.asString());
		List<Object> value = js.getList(key);
		return value;
	}

}
