package guru.springframework.spring6restmvc.client;

import org.springframework.boot.web.client.RestTemplateBuilder;  
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerDTOPageImpl;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class BeerClientImpl implements BeerClient {
	
	private final RestTemplateBuilder restTemplateBuilder;
	
//    private static final String BASE_URL = "http://localhost:8080";
    private static final String GET_BEER_PATH = "/api/v1/beer";

	@Override
	public Page<BeerDTO> listBeers() {
		
		RestTemplate restTemplate = restTemplateBuilder.build();
		
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(GET_BEER_PATH);
		
//		ResponseEntity<String> stringResponse = restTemplate.getForEntity("http://localhost:8080/api/v1/beer", String.class);
		ResponseEntity<String> stringResponse = 
//				restTemplate.getForEntity(BASE_URL + GET_BEER_PATH, String.class);
				restTemplate.getForEntity(GET_BEER_PATH, String.class);
		
		
		ResponseEntity<Map> mapResponse =
//              restTemplate.getForEntity(BASE_URL + GET_BEER_PATH, Map.class);
				restTemplate.getForEntity(GET_BEER_PATH, Map.class);
		
		
		ResponseEntity<JsonNode> jsonResponse =
//				restTemplate.getForEntity(BASE_URL + GET_BEER_PATH, JsonNode.class);
				restTemplate.getForEntity(GET_BEER_PATH, JsonNode.class);
		
		jsonResponse.getBody().findPath("content")
				.elements().forEachRemaining(node -> {
					System.out.println(node.get("beerName").asText());
				});
		
		ResponseEntity<BeerDTOPageImpl> beerDTOResponse =
////              restTemplate.getForEntity(BASE_URL + GET_BEER_PATH , BeerDTOPageImpl.class);
//				restTemplate.getForEntity(GET_BEER_PATH , BeerDTOPageImpl.class);
				restTemplate.getForEntity(uriComponentsBuilder.toUriString() , BeerDTOPageImpl.class);
		
		
		System.out.println(beerDTOResponse.getBody());
		
//		return null;
		return beerDTOResponse.getBody();
	}

}
