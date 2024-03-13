package guru.springframework.spring6restmvc.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import guru.springframework.spring6restmvc.domain.Beer;

public interface BeerRepository extends ReactiveCrudRepository<Beer, Integer> {

}
