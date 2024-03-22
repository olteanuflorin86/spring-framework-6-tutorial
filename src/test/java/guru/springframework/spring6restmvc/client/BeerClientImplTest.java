package guru.springframework.spring6restmvc.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BeerClientImplTest {
	
	@Autowired
	BeerClient beerClient;

	@Test
	void listBeer() {
		
		beerClient.listBeer().subscribe(response -> {
			System.out.println(response);
		});
		
		try {
			Thread.sleep(1000L);
		} catch(InterruptedException e) {
			throw new RuntimeException();
		}
	}
}
