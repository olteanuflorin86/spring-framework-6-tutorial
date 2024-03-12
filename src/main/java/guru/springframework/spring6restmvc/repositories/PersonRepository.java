package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository {

	Mono<Person> getById(Integer id);
	
	Flux<Person> findAll();
}
