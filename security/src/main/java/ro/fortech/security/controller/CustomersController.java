package ro.fortech.security.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.fortech.security.entity.Customer;
import ro.fortech.security.service.CustomerService;

import java.util.List;

import static ro.fortech.security.util.Constants.CUSTOMER_DELETE_FAIL;

@RestController()
@RequestMapping("/customer")
public class CustomersController {

    @Autowired
    private CustomerService customerService;
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") final Integer id){
        Customer customer = customerService.getCustomerById(id);
        if(customer == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(customer);
    }

    @GetMapping("/customers")
    @PostAuthorize("hasRole('USER')")
    //@PostFilter("filterObject.firstName == 'John'")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<Customer> getAllCustomers(){
      return customerService.getAllCustomers();
    }
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.addCustomer(customer);
        return ResponseEntity.ok(createdCustomer);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        if(customer == null || customer.getId() == null){
           return ResponseEntity.notFound().build();
        }
        Customer existingCustomer = customerService.getCustomerById(customer.getId());
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());

        Customer createdCustomer = customerService.updateCustomer(existingCustomer);

        return ResponseEntity.ok(createdCustomer);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") final Integer id){
     String customerResponse = customerService.deleteById(id);
     if(customerResponse == null){
         return ResponseEntity.notFound().build();
     }
        if(customerResponse.equals(CUSTOMER_DELETE_FAIL)){
            return ResponseEntity.badRequest().body(customerResponse);
        }
        return ResponseEntity.ok(customerResponse);
    }
}
