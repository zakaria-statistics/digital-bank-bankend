package org.sid.ebankingbackend.mappers;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.sid.ebankingbackend.dtos.CustomerDTO;
import org.sid.ebankingbackend.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO= new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
//        customerDTO.setId(customer.getId());
//        customerDTO.setName(customerDTO.getName());
//        customerDTO.setEmail(customerDTO.getEmail());
        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer= new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }
}
