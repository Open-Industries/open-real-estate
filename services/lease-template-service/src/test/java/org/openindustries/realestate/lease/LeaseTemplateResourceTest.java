package org.openindustries.realestate.lease;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class LeaseTemplateResourceTest {

    @Test
    void createAndTransition() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"Residential Standard 12\",\"templateType\":\"RESIDENTIAL\",\"termMonths\":12}")
                .when().post("/api/v1/lease-templates")
                .then().statusCode(201)
                .body("status", is("DRAFT"))
                .body("templateType", is("RESIDENTIAL"))
                .extract().path("id");

        given().when().post("/api/v1/lease-templates/" + id + "/activate")
                .then().statusCode(200).body("status", is("ACTIVE"));
    }
}
