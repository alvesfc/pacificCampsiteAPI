package org.pacific.campsite.rest;

import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import io.restassured.module.webtestclient.specification.WebTestClientRequestSpecBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.pacific.campsite.PacificCampsiteApiApplication;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(classes = PacificCampsiteApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureRestDocs
public abstract class ContractVerifierBase {

    @BeforeEach
    public void setup() {


        WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8079").build();
        RestAssuredWebTestClient.requestSpecification = new WebTestClientRequestSpecBuilder()
                .setWebTestClient(webTestClient).build();
    }
}
