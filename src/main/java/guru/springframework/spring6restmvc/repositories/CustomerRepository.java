package guru.springframework.spring6restmvc.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import guru.springframework.spring6restmvc.domain.Customer;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {

}
