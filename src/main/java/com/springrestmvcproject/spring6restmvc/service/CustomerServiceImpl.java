package com.springrestmvcproject.spring6restmvc.service;

import com.springrestmvcproject.spring6restmvc.model.Customer;
import com.springrestmvcproject.spring6restmvc.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {


    private Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {

        this.customerMap = new HashMap<>();

        Customer customer1 = Customer.builder()
                .customerName("Ravi")
                .id(UUID.randomUUID())
                .version("1.0")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();
        Customer customer2 = Customer.builder()
                .customerName("John")
                .id(UUID.randomUUID())
                .version("1.0")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();
        Customer customer3 = Customer.builder()
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
    public List<Customer> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customerMap.get(id);
    }


    @Override
    public Customer saveNewCustomer(Customer customer) {
        Customer savedCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .customerName(customer.getCustomerName())
                .version(customer.getVersion())
                .updatedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();

        customerMap.put(savedCustomer.getId(), savedCustomer);


        return savedCustomer;
    }

    @Override
    public void updateCustomerById(UUID customerId, Customer customer) {

        Customer existingCustomer = customerMap.get(customerId);
        existingCustomer.setCustomerName(customer.getCustomerName());
        customerMap.put(existingCustomer.getId(), existingCustomer);

    }

    @Override
    public void deleteCustomerById(UUID customerId) {

        customerMap.remove(customerId);
    }

    @Override
    public void patchCustomerById(UUID customerId, Customer customer) {


        Customer existingCustomer = customerMap.get(customerId);

        if(StringUtils.hasText(customer.getCustomerName())){
            existingCustomer.setCustomerName(customer.getCustomerName());
        }

    }
}
