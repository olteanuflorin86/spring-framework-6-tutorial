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
import guru.springframework.spring6restmvc.model.BeerStyle;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerClientImpl implements BeerClient {
	
	private final RestTemplateBuilder restTemplateBuilder;
	
//    private static final String BASE_URL = "http://localhost:8080";
    private static final String GET_BEER_PATH = "/api/v1/beer";
    private static final String GET_BEER_BY_ID_PATH = "/api/v1/beer/{beerId}";
    
    @Override
    public Page<BeerDTO> listBeers() {
        return this.listBeers(null, null, null, null, null);
    }

	@Override
	public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber,
            Integer pageSize) {
		
		RestTemplate restTemplate = restTemplateBuilder.build();
		
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(GET_BEER_PATH);
		
		if(beerName != null) {
			uriComponentsBuilder.queryParam("beerName", beerName);
		}
		
        if (beerStyle != null) {
            uriComponentsBuilder.queryParam("beerStyle", beerStyle);
        }

        if (showInventory != null) {
            uriComponentsBuilder.queryParam("showInventory", showInventory);
        }

        if (pageNumber != null) {
            uriComponentsBuilder.queryParam("pageNumber", pageNumber);
        }

        if (pageSize != null) {
            uriComponentsBuilder.queryParam("pageSize", pageSize);
        }
		
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
	
	@Override
	public BeerDTO getBeerById(UUID beerId) {
		RestTemplate restTemplate = restTemplateBuilder.build();
		return restTemplate.getForObject(GET_BEER_BY_ID_PATH, BeerDTO.class, beerId);
	}

	@Override
	public BeerDTO createBeer(BeerDTO newBeerDTO) {
		RestTemplate restTemplate = restTemplateBuilder.build();
		
		ResponseEntity<BeerDTO> response = restTemplate.postForEntity(GET_BEER_PATH, newBeerDTO, BeerDTO.class);
		
		return null;
	}

}
