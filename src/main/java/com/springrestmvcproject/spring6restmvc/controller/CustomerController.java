package com.springrestmvcproject.spring6restmvc.controller;



import com.springrestmvcproject.spring6restmvc.model.Customer;
import com.springrestmvcproject.spring6restmvc.model.Customer;
import com.springrestmvcproject.spring6restmvc.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
public class CustomerController {


    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_LEADING_SLASH = "/api/v1/customer/";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH+"/{customerId}";


    private final CustomerService customerService;

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateCustomerPatchById(@PathVariable("customerId") UUID customerId,
                                                  @RequestBody Customer customer) {

        customerService.patchCustomerById(customerId, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);


    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("customerId") UUID customerId){

        customerService.deleteCustomerById(customerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("customerId")UUID customerId, @RequestBody Customer Customer){
        customerService.updateCustomerById(customerId, Customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity handlePost(@RequestBody Customer customer){
        Customer savedCustomer = customerService.saveNewCustomer(customer);

        HttpHeaders headers =new HttpHeaders();
        headers.add("Location",
                CUSTOMER_PATH_LEADING_SLASH+savedCustomer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);

    }


    @GetMapping(value = CUSTOMER_PATH)
    public List<Customer> listCustomers() {

        return customerService.listCustomers();

    }

    @GetMapping(value = CUSTOMER_PATH_ID)
    public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {

        log.debug("Get Customer By Id : Inside Controller");
        return customerService.getCustomerById(customerId);


    }


}
