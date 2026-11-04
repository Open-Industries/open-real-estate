package org.openindustries.realestate.functions.portfolio;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void compliant() {
        given().contentType("application/json")
                .body("{\"propertyId\":\"pr-1\",\"measuredVacancyDays\":10,\"targetVacancyDays\":30,\"measuredOccupancyPct\":96.0,\"targetOccupancyPct\":95.0}")
                .when().post("/portfolioSlaEvaluator")
                .then().statusCode(200).body("compliant", is(true)).body("findings.size()", is(0));
    }

    @Test
    void breachIsReported() {
        given().contentType("application/json")
                .body("{\"propertyId\":\"pr-2\",\"measuredVacancyDays\":75,\"targetVacancyDays\":30,\"measuredOccupancyPct\":96.0,\"targetOccupancyPct\":95.0}")
                .when().post("/portfolioSlaEvaluator")
                .then().statusCode(200).body("compliant", is(false)).body("findings.size()", is(1));
    }
}
