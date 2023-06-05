package ro.fortech.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.fortech.security.entity.Account;
import ro.fortech.security.entity.Customer;
import ro.fortech.security.repository.CustomerRepository;
import ro.fortech.security.service.CusotmesService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static ro.fortech.security.util.Constants.CUSTOMER_DELETED;
import static ro.fortech.security.util.Constants.CUSTOMER_DELETE_FAIL;

@Service
@Transactional
public class CustomerServiceImpl implements CusotmesService {
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public Customer getCustomerById(final Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElse(null);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public String deleteById(Integer id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(!customerOptional.isPresent()) {
            return null;
        }
        Customer customer = customerOptional.get();
        List<Account> accounts = customer.getAccounts();
        for(Account account : accounts){
           if(account.getBalance() > 0){
              return CUSTOMER_DELETE_FAIL;
           }
        }
        customerRepository.deleteById(id);
        return CUSTOMER_DELETED;
    }
}
