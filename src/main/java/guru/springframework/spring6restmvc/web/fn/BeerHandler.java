package guru.springframework.spring6restmvc.web.fn;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BeerHandler {

	private final BeerService beerService;
	
	public Mono<ServerResponse> listBeers(ServerRequest request) {
		return ServerResponse.ok()
				.body(beerService.listBeers(), BeerDTO.class);
	}
	
}
