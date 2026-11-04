package org.openindustries.realestate.property;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class PropertyResourceTest {

    @Test
    void createAndFetch() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"Casa Loma Verde\",\"propertyType\":\"RESIDENTIAL\",\"landRegistryRef\":\"FR-2026-334455\"}")
                .when().post("/api/v1/properties")
                .then().statusCode(201)
                .body("status", is("LISTED"))
                .body("landRegistryRefMasked", endsWith("4455"))
                .extract().path("id");

        given().when().get("/api/v1/properties/" + id)
                .then().statusCode(200).body("status", is("LISTED"));
    }

    @Test
    void transitionWithoutBody() {
        // Lifecycle transitions take no body and must not require a Content-Type (no 415).
        String id = given().contentType("application/json")
                .body("{\"name\":\"Casa Loma Verde\",\"propertyType\":\"RESIDENTIAL\",\"landRegistryRef\":\"FR-2026-334455\"}")
                .when().post("/api/v1/properties")
                .then().statusCode(201)
                .extract().path("id");

        given().when().post("/api/v1/properties/" + id + "/reserve")
                .then().statusCode(200).body("status", is("RESERVED"));
    }
}
