package ro.fortech.security.service;

import ro.fortech.security.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer getCustomerById(final Integer id);

    List<Customer> getAllCustomers();

    Customer addCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    String deleteById(Integer id);
}
