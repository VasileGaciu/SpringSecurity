package ro.fortech.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.fortech.security.entity.Customer;
import ro.fortech.security.service.CusotmesService;

import java.util.List;

import static ro.fortech.security.util.Constants.CUSTOMER_DELETE_FAIL;

@RestController()
@RequestMapping("/")
public class CustomersController {

    @Autowired
    private CusotmesService cusotmesService;
    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") final Integer id){
        Customer customer = cusotmesService.getCustomerById(id);
        if(customer == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(customer);
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers(){
      return cusotmesService.getAllCustomers();
    }
    @PostMapping("/add")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = cusotmesService.addCustomer(customer);
        return ResponseEntity.ok(createdCustomer);
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        if(customer == null || customer.getId() == null){
           return ResponseEntity.notFound().build();
        }
        Customer existingCustomer = cusotmesService.getCustomerById(customer.getId());
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());

        Customer createdCustomer = cusotmesService.updateCustomer(existingCustomer);

        return ResponseEntity.ok(createdCustomer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") final Integer id){
     String customerResponse = cusotmesService.deleteById(id);
     if(customerResponse == null){
         return ResponseEntity.notFound().build();
     }
        if(customerResponse.equals(CUSTOMER_DELETE_FAIL)){
            return ResponseEntity.badRequest().body(customerResponse);
        }
        return ResponseEntity.ok(customerResponse);
    }
}