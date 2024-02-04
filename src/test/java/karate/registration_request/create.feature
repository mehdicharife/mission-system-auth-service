Feature: Create a registration request ...

    Background:
        * url 'http://localhost:8080'
        * def registrationRequestBase = '/account-creation-requests'
    
    Scenario: Create a registration request

        Given path registrationRequestBase
        And request {username: 'tester', password: 'tester', rolesNames: ['ADMIN']}
        And header Accept = 'application/json'
        When method post
        Then status 201
        And match response == {id: '#number', username:'tester', rolesNames: ['ADMIN']} 
        



