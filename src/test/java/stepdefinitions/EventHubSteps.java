package stepdefinitions;

import java.io.IOException;

import clients.CreateEvent;
import clients.RetrieveEvent;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class EventHubSteps {
	
	public CreateEvent createEvent = new CreateEvent();
	RetrieveEvent retrieveEvent = new RetrieveEvent();
	Response response;
	
	@When("I create a new event using {string} with valid details")
	public void i_create_a_new_event_using_with_valid_details(String resource) throws IOException {
	    response = createEvent.createEvent(resource);
	}

	@Then("the event should be created successfully and visible in the event list")
	public void the_event_should_be_created_successfully_and_visible_in_the_event_list() {
	    createEvent.validateResponseSchema(response, "schemas/createEventResponseSchema.json");
	}
	
	@Then("the status code should be {int}")
	public void the_status_code_should_be(Integer num) {
	    createEvent.validateStatusCode(response, num);
	}

	@Then("the response should contain the {string}")
	public void the_response_should_contain_the(String id) {
	    createEvent.idValidation(response, id);
	}
	
	@When("I retrieve all events using {string}")
	public void i_retrieve_al_events_using(String resource) {
	    response = retrieveEvent.retrieveEvent(resource);
	}
	
	@Then("All the events should be visible in the event list")
	public void all_the_events_should_be_visible_in_the_event_list() {
	    retrieveEvent.validateResponseSchema(response, "schemas/RetrieveEventSchema.json");
	}
	
	@Then("the response should contain the list of {string}")
	public void the_response_should_contain_the_list_of(String id) {
		retrieveEvent.idValidation(response, id);
	}
	
	@When("I retrieve all events using {string} with valid {string} and {string}")
	public void i_retrieve_all_events_using_with_valid_and(String resource, String id, String idValue) {
	    response = retrieveEvent.retrieveEventById(resource, id, idValue);
	}
	
	@Then("the respective events id should be visible in the event list")
	public void the_respective_events_id_should_be_visible_in_the_event_list() {
		retrieveEvent.validateResponseSchema(response, "schemas/RetrieveEventByIdSchema.json");
	}



}
