package guru.springframework.spring6restmvc.mappers;

import org.mapstruct.Mapper;

import guru.springframework.spring6restmvc.domain.Customer;
import guru.springframework.spring6restmvc.model.CustomerDTO;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);
}