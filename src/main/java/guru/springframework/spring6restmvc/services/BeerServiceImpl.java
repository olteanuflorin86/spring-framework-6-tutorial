package guru.springframework.spring6restmvc.services;

import org.springframework.stereotype.Service;

import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {
	
	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;

	@Override
	public Flux<BeerDTO> listBeers() {
		return beerRepository.findAll()
				.map(beerMapper::beerToBeerDto);
	}

	@Override
	public Mono<BeerDTO> getBeerById(Integer beerId) {
		return beerRepository.findById(beerId)
				.map(beerMapper::beerToBeerDto);
	}

}
