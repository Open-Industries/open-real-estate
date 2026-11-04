package org.openindustries.realestate.appraisal;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class AppraisalRuleResourceTest {

    @Test
    void activateThenEvaluateAllowed() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"residential-standard\",\"propertyType\":\"RESIDENTIAL\",\"maxValueMinor\":50000000,\"maxValuationGapPct\":10,\"priority\":1}")
                .when().post("/api/v1/appraisal-rules")
                .then().statusCode(201).body("status", is("DRAFT"))
                .extract().path("id");

        given().when().post("/api/v1/appraisal-rules/" + id + "/activate")
                .then().statusCode(200).body("status", is("ACTIVE"));

        given().contentType("application/json")
                .body("{\"propertyType\":\"RESIDENTIAL\",\"requiredMaxValuationGapPct\":12,\"requiredMaxValueMinor\":40000000}")
                .when().post("/api/v1/appraisal-rules/evaluate")
                .then().statusCode(200).body("decision", is("ALLOWED"));
    }

    @Test
    void evaluateWithoutRuleIsDenied() {
        given().contentType("application/json")
                .body("{\"propertyType\":\"LAND\",\"requiredMaxValuationGapPct\":12,\"requiredMaxValueMinor\":40000000}")
                .when().post("/api/v1/appraisal-rules/evaluate")
                .then().statusCode(200).body("decision", is("DENIED"));
    }
}
