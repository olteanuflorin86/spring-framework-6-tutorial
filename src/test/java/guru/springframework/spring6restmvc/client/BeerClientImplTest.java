package guru.springframework.spring6restmvc.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;

@SpringBootTest
class BeerClientImplTest {
	
	@Autowired
	BeerClient beerClient;

	@Test
	void listBeer() {
		
		AtomicBoolean atomicBoolean = new AtomicBoolean(false);
		
		beerClient.listBeer().subscribe(response -> {
			System.out.println(response);
			
			atomicBoolean.set(true);
		});
		
//		try {
//			Thread.sleep(1000L);
//		} catch(InterruptedException e) {
//			throw new RuntimeException();
//		}
		
		await().untilTrue(atomicBoolean);
	}
	
    @Test
    void testGetMap() {
    	
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeerMap().subscribe(response -> {
            System.out.println(response);
            
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }
    
    @Test
    void testGetBeerJson() {

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeersJsonNode().subscribe(jsonNode -> {

            System.out.println(jsonNode.toPrettyString());
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }
    
    @Test
    void testGetBeerDto() {

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeerDtos().subscribe(dto -> {
            System.out.println(dto.getBeerName());
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }
    
}
