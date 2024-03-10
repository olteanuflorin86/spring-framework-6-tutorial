package guru.springframework.spring6restmvc.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestToUriTemplate;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withAccepted;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withNoContent;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withResourceNotFound;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.MockServerRestTemplateCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.spring6restmvc.config.RestTemplateBuilderConfig;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerDTOPageImpl;
import guru.springframework.spring6restmvc.model.BeerStyle;

//@RestClientTest(BeerClientImpl.class)
@RestClientTest
@Import(RestTemplateBuilderConfig.class)
public class BeerClientMockTest {
	
	static final String URL = "http://localhost:8080";
	
//	@Autowired
//	BeerClient beerClient;
	BeerClient beerClient;
	
//	@Autowired
//	MockRestServiceServer server;
	MockRestServiceServer server;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	RestTemplateBuilder restTemplateBuilderConfigured;
	
	@Mock
	RestTemplateBuilder mockRestTemplateBuilder = new RestTemplateBuilder(new MockServerRestTemplateCustomizer());
	
    BeerDTO dto;
    String dtoJson;
    
	@BeforeEach
	void setUp() throws JsonProcessingException {
		RestTemplate restTemplate = restTemplateBuilderConfigured.build();
		server = MockRestServiceServer.bindTo(restTemplate).build();
		when(mockRestTemplateBuilder.build()).thenReturn(restTemplate);
		beerClient = new BeerClientImpl(mockRestTemplateBuilder);
		dto = getBeerDTO();
        dtoJson = objectMapper.writeValueAsString(dto);
	}
	
	@Test
	void testListBeers() throws JsonProcessingException {
		String payload = objectMapper.writeValueAsString(getPage());
		
		server.expect(method(HttpMethod.GET))
				.andExpect(requestTo(URL + BeerClientImpl.GET_BEER_PATH))
				.andRespond(withSuccess(payload, MediaType.APPLICATION_JSON));
		
		Page<BeerDTO> dtos = beerClient.listBeers();
		assertThat(dtos.getContent().size()).isGreaterThan(0);
	}
	
    @Test
    void testGetById() throws JsonProcessingException {
//        BeerDTO dto = getBeerDTO();
//
//        String response = objectMapper.writeValueAsString(dto);

//        server.expect(method(HttpMethod.GET))
//                .andExpect(requestToUriTemplate(URL +
//                        BeerClientImpl.GET_BEER_BY_ID_PATH, dto.getId()))
////                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
//        		.andRespond(withSuccess(dtoJson, MediaType.APPLICATION_JSON));
    	mockGetOperation();

        BeerDTO responseDto = beerClient.getBeerById(dto.getId());
        assertThat(responseDto.getId()).isEqualTo(dto.getId());
    }
    
    @Test
    void testCreateBeer() throws JsonProcessingException {
//        BeerDTO dto = getBeerDTO();
//        String response = objectMapper.writeValueAsString(dto);
        URI uri = UriComponentsBuilder.fromPath(BeerClientImpl.GET_BEER_BY_ID_PATH)
                        .build(dto.getId());

        server.expect(method(HttpMethod.POST))
                        .andExpect(requestTo(URL +
                                BeerClientImpl.GET_BEER_PATH))
                        .andExpect(header("Authorization", "Basic dXNlcjE6cGFzc3dvcmQ="))
                                .andRespond(withAccepted().location(uri));

//        server.expect(method(HttpMethod.GET))
//                .andExpect(requestToUriTemplate(URL +
//                        BeerClientImpl.GET_BEER_BY_ID_PATH, dto.getId()))
////                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
//                .andRespond(withSuccess(dtoJson, MediaType.APPLICATION_JSON));
        mockGetOperation();

        BeerDTO responseDto = beerClient.createBeer(dto);
        assertThat(responseDto.getId()).isEqualTo(dto.getId());
    }
    
    @Test
    void testUpdateBeer() {
        server.expect(method(HttpMethod.PUT))
                .andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH,
                        dto.getId()))
                .andExpect(header("Authorization", "Basic dXNlcjE6cGFzc3dvcmQ="))
                .andRespond(withNoContent());

        mockGetOperation();

        BeerDTO responseDto = beerClient.updateBeer(dto);
        assertThat(responseDto.getId()).isEqualTo(dto.getId());
    }
    
    @Test
    void testDeleteBeer() {
        server.expect(method(HttpMethod.DELETE))
                .andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH,
                        dto.getId()))
                .andExpect(header("Authorization", "Basic dXNlcjE6cGFzc3dvcmQ="))
                .andRespond(withNoContent());

        beerClient.deleteBeer(dto.getId());

        server.verify();
    }
    
    @Test
    void testDeleteNotFound() {
        server.expect(method(HttpMethod.DELETE))
                .andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH,
                        dto.getId()))
                .andExpect(header("Authorization", "Basic dXNlcjE6cGFzc3dvcmQ="))
                .andRespond(withResourceNotFound());

        assertThrows(HttpClientErrorException.class, () -> {
            beerClient.deleteBeer(dto.getId());
        });

        server.verify();
    }
    
    @Test
    void testListBeersWithQueryParam() throws JsonProcessingException {
        String response = objectMapper.writeValueAsString(getPage());

        URI uri = UriComponentsBuilder.fromHttpUrl(URL + BeerClientImpl.GET_BEER_PATH)
                .queryParam("beerName", "ALE")
                .build().toUri();

        server.expect(method(HttpMethod.GET))
                .andExpect(requestTo(uri))
                .andExpect(header("Authorization", "Basic dXNlcjE6cGFzc3dvcmQ="))
                .andExpect(queryParam("beerName", "ALE"))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        Page<BeerDTO> responsePage = beerClient
                .listBeers("ALE", null, null, null, null);

        assertThat(responsePage.getContent().size()).isEqualTo(1);
    }

	BeerDTO getBeerDTO() {
		return BeerDTO.builder()
				.id(UUID.randomUUID())
                .price(new BigDecimal("10.99"))
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("123245")
                .build();
	}
	
	BeerDTOPageImpl getPage() {
//		return new BeerDTOPageImpl(Arrays.asList(getBeerDTO()), 1, 25, 1);
		
		List<BeerDTO> listOfBeerDTO = new ArrayList<>();
		listOfBeerDTO.add(getBeerDTO());
		return new BeerDTOPageImpl(listOfBeerDTO, 1, 25, 1);
	}
	
    private void mockGetOperation() {
        server.expect(method(HttpMethod.GET))
                .andExpect(requestToUriTemplate(URL +
                        BeerClientImpl.GET_BEER_BY_ID_PATH, dto.getId()))
                .andExpect(header("Authorization", "Basic dXNlcjE6cGFzc3dvcmQ="))
                .andRespond(withSuccess(dtoJson, MediaType.APPLICATION_JSON));
    }
}
