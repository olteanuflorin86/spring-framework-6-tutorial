package guru.springframework.spring6restmvc.repositories;

import org.junit.jupiter.api.Test; 

import guru.springframework.spring6restmvc.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

	@Test
	void testFluxFilterOnName() {
		personRepository.findAll()
				.filter(person -> person.getFirstName().equals("Fiona"))
				.subscribe(person -> System.out.println(person.getFirstName()));
	}
	
	@Test
	void testFluxFilterGetById() {
		Mono<Person> fionaMono = personRepository.findAll().filter(person -> person.getFirstName().equals("Fiona")).next();
	
		fionaMono.subscribe(person -> System.out.println(person.getFirstName()));
	}
	
	@Test
	void testFindPersonByIdNotFound() {
		Flux<Person> personFlux = personRepository.findAll();
		
		final Integer id = 8;
		
		Mono<Person> personMono = personFlux.filter(person -> person.getId() == id).single()
				.doOnError(throwable -> {
					System.out.println("Error occurred in flux");
                    System.out.println(throwable.toString());
				});
		
		personMono.subscribe(person -> {
			System.out.println(person.toString());
		}, throwable -> {
			System.out.println("Error occurred in the mono");
            System.out.println(throwable.toString());
		});
	}
	
	@Test
	void testGetByIdFound() {
		Mono<Person> personMono = personRepository.getById(3);
		
		assertTrue(personMono.hasElement().block());
	}
	
	void testGetByIdNotFound() {
		Mono<Person> personMono = personRepository.getById(6);
		
		assertTrue(personMono.hasElement().block());
	}
	
	@Test
	void testGetByIdFoundStepVerifier() {
		Mono<Person> personMono = personRepository.getById(3);
		
		StepVerifier.create(personMono).expectNextCount(1).verifyComplete();
		
		personMono.subscribe(person -> {
			System.out.println(person.getFirstName());
		});
	}
	
	@Test
	void testGetByIdNotFoundStepVerifier() {
		Mono<Person> personMono = personRepository.getById(6);
		
//		StepVerifier.create(personMono).expectNextCount(1).verifyComplete();
		StepVerifier.create(personMono).expectNextCount(0).verifyComplete();
		
		personMono.subscribe(person -> {
			System.out.println(person.getFirstName());
		});		
	}
	
}
