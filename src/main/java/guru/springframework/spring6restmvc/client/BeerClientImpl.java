package guru.springframework.spring6restmvc.client;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import guru.springframework.spring6restmvc.model.BeerDTO;

@Service
public class BeerClientImpl implements BeerClient {

	@Override
	public Page<BeerDTO> listBeers() {
		return null;
	}

}
