package guru.springframework.spring6restmvc.repositories;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.spring6restmvc.domain.Beer;

import guru.springframework.spring6restmvc.config.DatabaseConfig;

@DataR2dbcTest
@Import(DatabaseConfig.class)
public class BeerRepositoryTest {
	
	@Autowired
	BeerRepository beerRepository;
	
	@Test
	void saveNewBeer() {
		beerRepository.save(getTestBeer())
				.subscribe(beer -> {
					System.out.println(beer.toString());
				});
				
	}

	@Test
	void testCreateJson() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		System.out.println(objectMapper.writeValueAsString(getTestBeer()));
	}
	
	Beer getTestBeer() {
		return Beer.builder()
				.beerName("Space Dust")
				.beerStyle("IPA")
				.price(BigDecimal.TEN)
				.quantityOnHand(12)
				.upc("123213")
				.build();
	}
	
}
