package org.openindustries.realestate.escrow;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class EscrowResourceTest {

    @Test
    void consumptionDrawsDown() {
        String id = given().contentType("application/json")
                .body("{\"propertyId\":\"11111111-1111-1111-1111-111111111111\",\"escrowAmountMinor\":2500000}")
                .when().post("/api/v1/escrow")
                .then().statusCode(201).body("escrowAmountMinor", is(2500000))
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"releaseType\":\"DEPOSIT\",\"amountMinor\":500000}")
                .when().post("/api/v1/escrow/" + id + "/releases")
                .then().statusCode(200)
                .body("remainingAfter", is(2000000));
    }

    @Test
    void overdrawIsRejected() {
        String id = given().contentType("application/json")
                .body("{\"propertyId\":\"11111111-1111-1111-1111-111111111111\",\"escrowAmountMinor\":2500000}")
                .when().post("/api/v1/escrow")
                .then().statusCode(201)
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"releaseType\":\"DEPOSIT\",\"amountMinor\":9000000}")
                .when().post("/api/v1/escrow/" + id + "/releases")
                .then().statusCode(422).body("status", is(422));

        given().when().get("/api/v1/escrow/" + id)
                .then().statusCode(200).body("escrowAmountMinor", is(2500000));
    }
}
