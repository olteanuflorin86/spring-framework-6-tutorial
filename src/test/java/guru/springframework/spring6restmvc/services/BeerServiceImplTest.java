package guru.springframework.spring6restmvc.services;

import java.math.BigDecimal; 

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.spring6restmvc.domain.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import reactor.core.publisher.Mono;

@SpringBootTest
public class BeerServiceImplTest {
	
	@Autowired
	BeerService beerService;
	
	@Autowired
	BeerMapper beerMapper;
	
	BeerDTO beerDTO;
	
	@BeforeEach
	void setUp() {
		beerDTO = beerMapper.beerToBeerDto(getTestBeer());
	}
	
	@Test
	void saveBeer() throws InterruptedException {
		
		Mono<BeerDTO> savedMono = beerService.saveBeer(Mono.just(beerDTO));
		
		savedMono.subscribe(savedDto -> {
			System.out.println(savedDto.getId());
		});
		
		Thread.sleep(1000L);
	}
	
	public static Beer getTestBeer() {
		return Beer.builder()
				.beerName("Space Dust")
				.beerStyle("IPA")
				.price(BigDecimal.TEN)
				.quantityOnHand(12)
				.upc("123213")
				.build();
	}

}
