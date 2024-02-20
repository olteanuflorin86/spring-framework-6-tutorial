package guru.springframework.spring6restmvc.client;

import org.springframework.data.domain.Page;

import guru.springframework.spring6restmvc.model.BeerDTO;

public interface BeerClient {

	Page<BeerDTO> listBeers();
	
}
