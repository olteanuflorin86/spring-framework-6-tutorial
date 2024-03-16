package guru.springframework.spring6restmvc.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import guru.springframework.spring6restmvc.domain.Beer;

public interface BeerRepository extends ReactiveMongoRepository<Beer, String> {

}
