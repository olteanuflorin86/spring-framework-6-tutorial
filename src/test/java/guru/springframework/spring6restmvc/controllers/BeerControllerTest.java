package guru.springframework.spring6restmvc.controllers;

import org.junit.jupiter.api.MethodOrderer; 
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepositoryTest;
import reactor.core.publisher.Mono;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class BeerControllerTest {

	@Autowired
	WebTestClient webTestClient;
	
	@Test
	@Order(1)
	void testListBeers() {
		webTestClient.get().uri(BeerController.BEER_PATH)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().valueEquals("Content-type", "application/json")
				.expectBody().jsonPath("$.size()").isEqualTo(3);
	}
	
    @Test
    @Order(2)
    void testGetBeerById() {
        webTestClient.get().uri(BeerController.BEER_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody(BeerDTO.class);
    }
    
    @Test
    @Order(3)
    void testCreateNewBeer() {
        webTestClient.post().uri(BeerController.BEER_PATH)
        		.body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
        		.header("Content-Type", "application/json")
        		.exchange()
        		.expectStatus().isCreated()
        		.expectHeader().location("http://localhost:8080/api/v2/beer/4");
    }
    
    @Test
    @Order(4)
    void testUpdateExistingBeer() {
        webTestClient.put()
        		.uri(BeerController.BEER_PATH_ID, 1)
        		.body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
        		.exchange()
        		.expectStatus().isNoContent();
    }

    @Test
    @Order(999)
    void testDeleteBeerById() {
        webTestClient.delete()
                .uri(BeerController.BEER_PATH_ID, 1)
                .exchange()
                .expectStatus()
                .isNoContent();
    }
    

}
