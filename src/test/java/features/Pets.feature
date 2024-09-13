Feature: Testing the Swagger Petstore pets API endpoints

  @Smoke @AddPet
  Scenario: Adding a pet to the store
    Given I set the endpoint to "/pet"
    When I send the add pet post request
    Then I verify status code is 200 "Pet creation failed!"

  @Smoke @UploadPetImage
  Scenario: Upload pet image
    Given I set the endpoint to "/pet/{petId}/uploadImage"
    When I send the upload pet image post request
    Then I verify status code is 200 "Error uploading pet image!"

  @Smoke @UpdateExistingPet
  Scenario: Update a pet info
    Given I set the endpoint to "/pet"
    When I send the update pet put request
    Then I verify status code is 200 "Error updating pet information"

  @Smoke @FindPetsByStatus
  Scenario: Search pets in a certaing status
    Given I set the endpoint to "/pet/findByStatus"
    When I filter pets in "sold, available" status
    Then I verify status code is 200 "Error getting list of pets."
