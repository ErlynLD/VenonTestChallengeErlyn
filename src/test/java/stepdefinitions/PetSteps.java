package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PetSteps {

    private String baseURL = "https://petstore.swagger.io/v2";
    private Response response;
    private String endpoint;

    @Given("I set the endpoint to {string}")
    public void iSetTheEndpointTo(String endpoint) {
        this.endpoint = endpoint;
    }

    @When("I send the add pet post request")
    public void iSendTheAddPetPostRequest() {
        JSONObject pet = new JSONObject();
        pet.put("category", new JSONObject(){{
                                put("id", 0);
                                put("name", "cat");
                            }});
        pet.put("name", "Boby");
        pet.put("status", "available");


        response = given()
                    .header("Content-Type", "application/json")
                    .body(pet.toJSONString())
                .when()
                    .post(baseURL + endpoint)
                .then()
                    .extract().response();
    }

    @Then("I verify status code is {int} {string}")
    public void iVerifyStatusCodeIs(int statusCode, String errorMessage) {
        Assert.assertEquals(response.getStatusCode(), statusCode, errorMessage);
    }

    @When("I send the upload pet image post request")
    public void iSendTheUploadPetImagePostRequest() {
        File imageFile = new File("src/test/resources/petImage.jpg");

        response =
            given()
                .header("Content-Type", "multipart/form-data")
                .pathParam("petId", 5)
                .multiPart("file", imageFile)
            .when()
                .post(baseURL + endpoint)
            .then()
                .extract().response();
    }

    @When("I send the update pet put request")
    public void iSendTheUpdatePetPutRequest() {
        JSONObject pet = new JSONObject();
        pet.put("id", 0);
        pet.put("category", new JSONObject(){{
            put("id", 0);
            put("name", "dog");
        }});
        pet.put("name", "Boby");
        pet.put("tags", new JSONArray(){{
            add(new JSONObject(){{
                put("id", 0);
                put("name", "Bulldog");
            }});
        }});
        pet.put("status", "available");

        response = given()
                .header("Content-Type", "application/json")
                .body(pet.toJSONString())
                .when()
                .put(baseURL + endpoint)
                .then()
                .extract().response();
    }

    @When("I filter pets in {string} status")
    public void iFilterPetsInStatus(String status) {
        JSONArray statusArray = new JSONArray();

        String statuses[] = status.trim().split(",");

        for(int i = 0; i < statuses.length; i++){
            statusArray.add(statuses[i].trim());
        }

        response = given()
                .queryParam("status", statusArray)
            .when()
                .get(baseURL + endpoint)
            .then()
                .extract().response();
    }
}
