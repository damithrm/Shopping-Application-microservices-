package com.microservices.product;

import io.restassured.RestAssured;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.shaded.org.hamcrest.Matchers;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");
//            .withExposedPorts(27017);

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static {
        mongoDBContainer.start();
//        System.setProperty("spring.data.mongodb.uri", mongoDBContainer.getReplicaSetUrl());
//        System.setProperty("spring.data.mongodb.database", "productdb");
//        System.setProperty("spring.data.mongodb.port", mongoDBContainer.getFirstMappedPort().toString());
    }

    @Test
    void shouldCreateProduct() {
        String requestBody = """
                {
                    "name": "iphone_16",
                    "description" : "Iphone 16 is a smartphone from Apple",
                    "price" : 350000
                }
                """;

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/product")
                .then()
                .statusCode(201);
//                .body("id", (Matcher<?>) Matchers.notNullValue())
//                .body("name", (Matcher<?>) Matchers.equalTo("iphone_16"))
//                .body("description", (Matcher<?>) Matchers.equalTo("Iphone 16 is a smartphone from Apple"))
//                .body("price", (Matcher<?>) Matchers.equalTo(350000.0f));
    }

}
