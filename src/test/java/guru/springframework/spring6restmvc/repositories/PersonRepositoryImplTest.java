package guru.springframework.spring6restmvc.repositories;

import org.junit.jupiter.api.Test;

import guru.springframework.spring6restmvc.domain.Person;
import reactor.core.publisher.Mono;

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
	
}
