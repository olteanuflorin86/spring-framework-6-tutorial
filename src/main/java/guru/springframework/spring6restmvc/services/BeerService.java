package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerDTO;
import reactor.core.publisher.Flux;

public interface BeerService {

	Flux<BeerDTO> listBeers();
	
}
