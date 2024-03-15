package guru.springframework.spring6restmvc.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import guru.springframework.spring6restmvc.domain.Customer;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {
	
}
