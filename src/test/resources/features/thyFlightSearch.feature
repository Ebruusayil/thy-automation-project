Feature: THY Flight Search

  Scenario: Verify selected business flight flow
    Given user navigates to Turkish Airlines website
    When user searches one way flight from Istanbul to London for April 15
    Then flight selection page should be opened
    And selected flight information should be correct
    When user stores first flight data from the first result
    And user opens first flight details
    Then expanded flight details should match stored values
    When user opens first flight business package area
    Then BusinessFly price should match selected business price
    When user selects BusinessFly package
    Then next page flight details should match stored values