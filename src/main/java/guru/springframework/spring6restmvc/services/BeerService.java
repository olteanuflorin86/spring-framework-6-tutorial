package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {
	
	Flux<BeerDTO> listBeers();

	Mono<BeerDTO> getById(String beerId);

    Mono<BeerDTO> saveBeer(Mono<BeerDTO> beerDto);
    
    Mono<BeerDTO> saveBeer(BeerDTO beerDTO);
    
    Mono<BeerDTO> updateBeer(String beerId, BeerDTO beerDTO);
    
    Mono<BeerDTO> patchBeer(String beerId, BeerDTO beerDTO);
    
    Mono<Void> deleteBeerById(String beerId);

}
