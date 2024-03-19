package guru.springframework.spring6restmvc.services;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;
	
	@Override
	public Flux<CustomerDTO> listCustomers() {
        return customerRepository.findAll()
                .map(customerMapper::customerToCustomerDto);
	}
	@Override
	public Mono<CustomerDTO> getCustomerById(String customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::customerToCustomerDto);
	}
	@Override
	public Mono<CustomerDTO> saveNewCustomer(CustomerDTO customerDTO) {
        return customerRepository.save(customerMapper.customerDtoToCustomer(customerDTO))
                .map(customerMapper::customerToCustomerDto);
	}
	@Override
	public Mono<CustomerDTO> saveNewCustomer(Mono<CustomerDTO> customerDTO) {
        return customerDTO.map(customerMapper::customerDtoToCustomer)
                .flatMap(customerRepository::save)
                .map(customerMapper::customerToCustomerDto);
	}
	@Override
	public Mono<CustomerDTO> updateCustomer(String customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId)
                .map(customer -> {
                    customer.setCustomerName(customerDTO.getCustomerName());
                    return customer;
                }).flatMap(customerRepository::save)
                .map(customerMapper::customerToCustomerDto);
	}
	@Override
	public Mono<CustomerDTO> patchCustomer(String customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId)
                .map(customer -> {
                    if (StringUtils.hasText(customerDTO.getCustomerName())){
                        customer.setCustomerName(customerDTO.getCustomerName());
                    }
                    return customer;
                }).flatMap(customerRepository::save)
                .map(customerMapper::customerToCustomerDto);
	}
	@Override
	public Mono<Void> deleteCustomerById(String customerId) {
		return customerRepository.deleteById(customerId);
	}
}
