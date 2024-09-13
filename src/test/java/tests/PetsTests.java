package tests;

import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class PetsTests {


    //@Test
    public void addPet(){
        JSONObject pet = new JSONObject();
        pet.put("category", new JSONObject(){{
                                put("id", 0);
                                put("name", "cat");
                            }});
        pet.put("name", "Boby");
        pet.put("status", "available");

        baseURI = "https://petstore.swagger.io/v2";
        given()
                .header("Content-Type", "application/json")
                .body(pet.toJSONString())
            .when()
                .post("/pet")
            .then()
                .statusCode(200)
            .log().body();
    }

    //@Test
    public void uploadPetImage(){
        File imageFile = new File("src/test/resources/petImage.jpg");

        baseURI = "https://petstore.swagger.io/v2";
        Response response = given()
                .header("Content-Type", "multipart/form-data")
                .pathParam("petId", 5)
                .multiPart("file", imageFile)
            .when()
                .post("/pet/{petId}/uploadImage")
            .then()
                .statusCode(200)
            .extract().response();

        System.out.println(String.format("Image upload %s for pet 5!", response.statusCode() == 200 ? "SUCCEEDED" : "FAILED"));
    }

    //@Test
    public void updateExistingPet(){
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

        baseURI = "https://petstore.swagger.io/v2";
        given()
                .header("Content-Type", "application/json")
                .body(pet.toJSONString())
            .when()
                .put("/pet")
            .then()
                .statusCode(200);
    }

    //@Test
    public void findPetsByStatus(){

        baseURI = "https://petstore.swagger.io/v2";



        given()
                .queryParam("status", new JSONArray(){{
                    add("sold");
                }})
        .when()
                .get("/pet/findByStatus")
                .then()
                .statusCode(200);
    }
}
