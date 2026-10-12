Feature: Verify Event Hub Functionality

@test
Scenario: Verify create event 
Given I have valid EventHub login credentials
When I send a POST request to the login endpoint
And  I create a new event using "EventAPI" with valid details
Then the event should be created successfully and visible in the event list
And the status code should be 201
And the response should contain the "data.id"


@test
Scenario: Verify retrieve vevent 
Given I have valid EventHub login credentials
When I send a POST request to the login endpoint
And  I retrieve all events using "EventAPI"
Then All the events should be visible in the event list
And the status code should be 200
And the response should contain the list of "data.id"

@tests
Scenario: Verify retrieve event by id 
Given I have valid EventHub login credentials
When I send a POST request to the login endpoint
And  I retrieve all events using "EventAPIById" with valid "id" and "285"
Then the respective events id should be visible in the event list
And the status code should be 200
And the response should contain the "data.id"