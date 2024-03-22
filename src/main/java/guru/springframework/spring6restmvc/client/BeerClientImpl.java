package guru.springframework.spring6restmvc.client;

import java.util.Map; 

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;

import reactor.core.publisher.Flux;

import guru.springframework.spring6restmvc.model.BeerDTO;

@Service
public class BeerClientImpl implements BeerClient {
	
	public static final String BEER_PATH = "/api/v3/beer";
	
	private final WebClient webClient;
	
	public BeerClientImpl(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
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
    
}
