package com.springrestmvcproject.spring6restmvc.service;

import com.springrestmvcproject.spring6restmvc.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {


    private Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {

        this.customerMap = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder()
                .customerName("Ravi")
                .id(UUID.randomUUID())
                .version("1.0")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();
        CustomerDTO customer2 = CustomerDTO.builder()
                .customerName("John")
                .id(UUID.randomUUID())
                .version("1.0")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();
        CustomerDTO customer3 = CustomerDTO.builder()
                .customerName("Chad")
                .id(UUID.randomUUID())
                .version("1.0")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();


        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);

    }


    @Override
    public List<CustomerDTO> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public CustomerDTO getCustomerById(UUID id) {
        return customerMap.get(id);
    }


    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName(customer.getCustomerName())
                .updatedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();

        customerMap.put(savedCustomer.getId(), savedCustomer);


        return savedCustomer;
    }

    @Override
    public void updateCustomerById(UUID customerId, CustomerDTO customer) {

        CustomerDTO existingCustomer = customerMap.get(customerId);
        existingCustomer.setCustomerName(customer.getCustomerName());
        customerMap.put(existingCustomer.getId(), existingCustomer);

    }

    @Override
    public void deleteCustomerById(UUID customerId) {

        customerMap.remove(customerId);
    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {


        CustomerDTO existingCustomer = customerMap.get(customerId);

        if(StringUtils.hasText(customer.getCustomerName())){
            existingCustomer.setCustomerName(customer.getCustomerName());
        }

    }
}
