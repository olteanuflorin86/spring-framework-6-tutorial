package guru.springframework.spring6restmvc.client;

import java.util.Map; 

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import guru.springframework.spring6restmvc.model.BeerDTO;

@Service
public class BeerClientImpl implements BeerClient {
	
	public static final String BEER_PATH = "/api/v3/beer";
	public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";
	
	private final WebClient webClient;
	
	public BeerClientImpl(WebClient.Builder webClientBuilder) {
//		this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
		this.webClient = webClientBuilder.build();
	}

	@Override
	public Flux<String> listBeer() {
		return webClient.get().uri(BEER_PATH)
				.retrieve().bodyToFlux(String.class);
	}
	
    @Override
    public Flux<Map> listBeerMap() {
        return webClient.get().uri(BEER_PATH)
                .retrieve().bodyToFlux(Map.class);
    }

    @Override
    public Flux<JsonNode> listBeersJsonNode() {
        return webClient.get().uri(BEER_PATH)
                .retrieve().bodyToFlux(JsonNode.class);
    }
    
    @Override
    public Flux<BeerDTO> listBeerDtos() {
        return webClient.get().uri(BEER_PATH)
                .retrieve().bodyToFlux(BeerDTO.class);
    }
    
    @Override
    public Mono<BeerDTO> getBeerById(String id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(BEER_PATH_ID)
                        .build(id))
                .retrieve()
                .bodyToMono(BeerDTO.class);
    }
    
    @Override
    public Flux<BeerDTO> getBeerByBeerStyle(String beerStyle) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                        .path(BEER_PATH)
                        .queryParam("beerStyle", beerStyle).build())
                .retrieve()
                .bodyToFlux(BeerDTO.class);
    }
    
}
