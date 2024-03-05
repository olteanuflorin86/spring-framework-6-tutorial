package guru.springframework.spring6restmvc.client;

import org.springframework.data.domain.Page;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;

public interface BeerClient {

	Page<BeerDTO> listBeers();
	
    Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber,
            Integer pageSize);
    
}
