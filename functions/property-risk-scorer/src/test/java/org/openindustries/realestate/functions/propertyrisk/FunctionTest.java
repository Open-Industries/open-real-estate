package org.openindustries.realestate.functions.propertyrisk;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void healthyIsNormal() {
        given().contentType("application/json")
                .body("{\"propertyId\":\"pr-1\",\"titleIssueIndex\":2,\"volatilityIndex\":12,\"conditionIndex\":9}")
                .when().post("/propertyRiskScorer")
                .then().statusCode(200).body("decision", is("NORMAL")).body("score", is(0));
    }

    @Test
    void stackedSignalsAreCritical() {
        given().contentType("application/json")
                .body("{\"propertyId\":\"pr-2\",\"titleIssueIndex\":79,\"volatilityIndex\":71,\"conditionIndex\":66}")
                .when().post("/propertyRiskScorer")
                .then().statusCode(200).body("decision", is("CRITICAL")).body("score", is(100));
    }
}
