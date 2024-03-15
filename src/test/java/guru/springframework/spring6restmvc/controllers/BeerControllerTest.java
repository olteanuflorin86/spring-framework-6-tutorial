package guru.springframework.spring6restmvc.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import guru.springframework.spring6restmvc.model.BeerDTO;

@SpringBootTest
@AutoConfigureWebTestClient
public class BeerControllerTest {

	@Autowired
	WebTestClient webTestClient;
	
	@Test
	void testListBeers() {
		webTestClient.get().uri(BeerController.BEER_PATH)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().valueEquals("Content-type", "application/json")
				.expectBody().jsonPath("$.size()").isEqualTo(3);
	}
	
    @Test
    void testGetBeerById() {
        webTestClient.get().uri(BeerController.BEER_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody(BeerDTO.class);
    }
}
