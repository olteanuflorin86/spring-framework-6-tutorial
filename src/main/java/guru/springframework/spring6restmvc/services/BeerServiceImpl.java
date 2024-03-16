package guru.springframework.spring6restmvc.services;

import org.springframework.stereotype.Service;

import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {
	
	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;

	@Override
	public Mono<BeerDTO> saveBeer(Mono<BeerDTO> beerDto) {
		return beerDto.map(beerMapper::beerDtoToBeer)
				.flatMap(beerRepository::save)
				.map(beerMapper::beerToBeerDto);
	}

	@Override
	public Mono<BeerDTO> getById(String beerId) {
		// TODO Auto-generated method stub
		return null;
	}

}
