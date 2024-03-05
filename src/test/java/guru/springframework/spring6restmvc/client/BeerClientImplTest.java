package guru.springframework.spring6restmvc.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BeerClientImplTest {
	
	@Autowired
	BeerClientImpl beerClient;
	
    @Test
    void listBeersNoBeerName() {
    	
    	beerClient.listBeers(null);
    }

    @Test
    void listBeers() {
    	
    	beerClient.listBeers("ALE");
    }
    
}
