package guru.springframework.spring6restmvc.client;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import reactor.core.publisher.Flux;

public interface BeerClient {

	Flux<String> listBeer();
	
	Flux<Map> listBeerMap();
	
	Flux<JsonNode> listBeersJsonNode();
}
