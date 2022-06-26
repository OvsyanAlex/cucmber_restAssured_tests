Feature: End to end test for gorest.co.in User

  Scenario: Создаем, измеянем, получаем и удалем пользователя
    When creating new active user with name "Alex", "Male" gender and email "uniquValue1721sd1@mail.tv"
    Then we get 201 response code
    And update user name "Mike", "Male" gender and email "uniquValue1vs2ge13@mail.tv"
    Then we get 200 response code
    And delete user
    Then we get 204 response code