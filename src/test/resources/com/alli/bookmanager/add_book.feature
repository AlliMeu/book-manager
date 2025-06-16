Feature: Add a book

  Scenario: create and retrieve
    Given I have a new book with title "1984" and author "Orwell"
    When I save it
    Then I can load it by its ID
