package guru.springframework.spring6restmvc.client;

import static org.junit.jupiter.api.Assertions.assertNotNull; 

import org.junit.jupiter.api.Test; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;

@SpringBootTest
public class BeerClientImplTest {
	
	@Autowired
	BeerClientImpl beerClient;
	
    @Test
    void listBeersNoBeerName() {

        beerClient.listBeers(null, null, null, null, null);
    }

    @Test
    void listBeers() {

        beerClient.listBeers("ALE", null, null, null, null);
    }
    
    @Test
    void getBeerById() {

        Page<BeerDTO> beerDTOS = beerClient.listBeers();

        BeerDTO dto = beerDTOS.getContent().get(0);

        BeerDTO byId = beerClient.getBeerById(dto.getId());

        assertNotNull(byId);
    }
    
    @Test
    void testCreateBeer() {
    	
    	BeerDTO newBeerDTO = BeerDTO.builder()
    		.price(new BigDecimal("10.99"))
    		.beerName("Mango Bobs")
    		.beerStyle(BeerStyle.IPA)
    		.quantityOnHand(500)
    		.upc("123456")
    		.build();
    	
    	BeerDTO savedBeerDTO = beerClient.createBeer(newBeerDTO);
    	assertNotNull(savedBeerDTO);
    
    }
    
}
