Feature: EventHub Authentication API

  @smoke
  @login 
  Scenario: Successful login with valid credentials
    Given I have valid EventHub login credentials
    When I send a POST request to the login endpoint
    Then the login response status code should be 200
    And the login response schema should be valid
    And the login response should contain a token

    
    

  @regression
  Scenario Outline: Login with invalid credentials
    Given I have login credentials "<email>" and "<password>"
    When I send a POST request to the login endpoint
    Then the login response status code should be <status>

    Examples:
      | email               | password  | status |
      | invalid@example.com | wrong123   | 401    |
      | student@example.com | wrong123   | 401    |

   @test   
Scenario: Retrieve authenticate user
    Given I have valid EventHub login credentials
    When I send a POST request to the login endpoint
    And User retrieves "GetAuthAPI" authicated user using GET request
    Then  User id should be displayed in the response
    