package guru.springframework.spring6restmvc.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.spring6restmvc.model.BeerDTO;

import java.math.BigDecimal;
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
    
    @Test
    void testGetBeerById() {

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        
        beerClient.listBeerDtos()
                .flatMap(dto -> beerClient.getBeerById(dto.getId()))
                .subscribe(byIdDto -> {
                    System.out.println(byIdDto.getBeerName());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }
    
    @Test
    void testGetBeerByBeerStyle() {

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.getBeerByBeerStyle("Pale Ale")
                .subscribe(dto -> {
                    System.out.println(dto.toString());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }
    
    @Test
    void testCreateBeer() {

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        BeerDTO newDto = BeerDTO.builder()
                .price(new BigDecimal("10.99"))
                .beerName("Mango Bobs")
                .beerStyle("IPA")
                .quantityOnHand(500)
                .upc("123245")
                .build();

        beerClient.createBeer(newDto)
                .subscribe(dto -> {
                    System.out.println(dto.toString());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }
    
    @Test
    void testUpdate() {

        final String NAME = "New Name";

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeerDtos()
                .next()
                .doOnNext(beerDTO -> beerDTO.setBeerName(NAME))
                .flatMap(dto -> beerClient.updateBeer(dto))
                .subscribe(byIdDto -> {
                    System.out.println(byIdDto.toString());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }
    
}
