package guru.springframework.spring6restmvc.services;

import org.springframework.stereotype.Service;

import guru.springframework.spring6restmvc.model.BeerDTO;
import reactor.core.publisher.Mono;

@Service
public class BeerServiceImpl implements BeerService {

	@Override
	public Mono<BeerDTO> saveBeer(BeerDTO beerDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<BeerDTO> getById(String beerId) {
		// TODO Auto-generated method stub
		return null;
	}

}
