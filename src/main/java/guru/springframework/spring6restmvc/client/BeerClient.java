package guru.springframework.spring6restmvc.client;

import reactor.core.publisher.Flux;

public interface BeerClient {

	Flux<String> listBeer();
}
