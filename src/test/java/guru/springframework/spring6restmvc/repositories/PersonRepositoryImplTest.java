package guru.springframework.spring6restmvc.repositories;

import org.junit.jupiter.api.Test; 

import guru.springframework.spring6restmvc.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class PersonRepositoryImplTest {

	PersonRepository personRepository = new PersonRepositoryImpl();
	
	@Test
	void testMonoByIdBlock() {
		Mono<Person> personMono = personRepository.getById(1);
		
		Person person = personMono.block();
		
		System.out.println(person.toString());
	}
	
	@Test
	void testGetByIdSubscriber() {
		Mono<Person> personMono = personRepository.getById(1);

		personMono.subscribe(person -> {
			System.out.println(person.toString());
		});
	}
	
	@Test
	void testMapOperation() {
		Mono<Person> personMono = personRepository.getById(1);
		
		personMono.map(person -> {
			return person.getFirstName();
		}).subscribe(firstName -> {
			System.out.println(firstName);
		});
	}
	
	@Test
	void testFluxBlockFirst() {
		Flux<Person> personFlux = personRepository.findAll();
		
		Person person = personFlux.blockFirst();
		
		System.out.println(person.toString());
	}
	
	@Test
	void testFluxSubscriber() {
		Flux<Person> personFlux = personRepository.findAll();
		
		personFlux.subscribe(person -> {
			System.out.println(person.toString());
		});
	}
	
	@Test
	void testFluxMap() {
		Flux<Person> personFlux = personRepository.findAll();
		
		personFlux.map(Person::getFirstName).subscribe(firstName 
				-> System.out.println(firstName));
	}
	
	@Test
	void testFluxToList() {
		Flux<Person> personFlux = personRepository.findAll();
		
		Mono<List<Person>> listMono =  personFlux.collectList();
		
		listMono.subscribe(list -> {
			list.forEach(person -> System.out.println(person.getFirstName()));
		});
	}
	
}
