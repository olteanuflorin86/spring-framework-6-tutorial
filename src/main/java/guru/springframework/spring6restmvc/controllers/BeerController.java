package guru.springframework.spring6restmvc.controllers;

import java.util.concurrent.atomic.AtomicInteger; 

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class BeerController {

	public static final String BEER_PATH = "/api/v2/beer";
	public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";
	
	private final BeerService beerService;
	
	@GetMapping(BEER_PATH)
	Flux<BeerDTO> listBeers() {
//		return Flux.just(BeerDTO.builder().id(1).build(), 
//				BeerDTO.builder().id(2).build());
		return beerService.listBeers();
	}
	
	@GetMapping(BEER_PATH_ID)
	Mono<BeerDTO> getBeerById(@PathVariable("beerId") Integer beerId) {
		return beerService.getBeerById(beerId);
	}
	
	@PostMapping(BEER_PATH)
	ResponseEntity<Void> createNewBeer(@RequestBody BeerDTO beerDTO) {
		
		AtomicInteger atomicInteger = new AtomicInteger();
		
		beerService.saveNewBeer(beerDTO).subscribe(savedBeerDTO -> {
			atomicInteger.set(savedBeerDTO.getId());
		});
		
		return ResponseEntity.created(UriComponentsBuilder
					.fromHttpUrl("http://localhost:8080/" + BEER_PATH + "/" + atomicInteger.get())
					.build().toUri())
				.build();
	}
}
