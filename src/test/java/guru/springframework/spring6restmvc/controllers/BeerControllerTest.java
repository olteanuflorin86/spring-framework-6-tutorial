package guru.springframework.spring6restmvc.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepositoryTest;
import reactor.core.publisher.Mono;

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
    
    @Test
    void testCreateNewBeer() {
        webTestClient.post().uri(BeerController.BEER_PATH)
        		.body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
        		.header("Content-Type", "application/json")
        		.exchange()
        		.expectStatus().isCreated()
        		.expectHeader().location("http://localhost:8080/api/v2/beer/4");
    }
    
    @Test
    void testUpdateExistingBeer() {
        webTestClient.put()
        		.uri(BeerController.BEER_PATH_ID, 1)
        		.body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
        		.exchange()
        		.expectStatus().isNoContent();
    }

    @Test
    void testDeleteBeerById() {
        webTestClient.delete()
                .uri(BeerController.BEER_PATH_ID, 1)
                .exchange()
                .expectStatus()
                .isNoContent();
    }

}
