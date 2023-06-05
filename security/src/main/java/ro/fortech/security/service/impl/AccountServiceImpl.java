package ro.fortech.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.fortech.security.entity.Account;
import ro.fortech.security.entity.Customer;
import ro.fortech.security.model.AccountModel;
import ro.fortech.security.repository.AccountRepository;
import ro.fortech.security.repository.CustomerRepository;
import ro.fortech.security.service.AccountService;

import javax.transaction.Transactional;
import java.util.Optional;

import static ro.fortech.security.util.Constants.ACCOUNT_DELETED;
import static ro.fortech.security.util.Constants.ACCOUNT_DELETE_FAIL;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public AccountModel getAccount(final Integer id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if(!optionalAccount.isPresent()){
            return null;
        }
        Account account = optionalAccount.get();
        Customer customer = account.getCustomer();
        return new AccountModel(account.getId(), customer.getFirstName(),
                customer.getLastName(), account.getBalance());
    }

    @Override
    public Account createAccount(Account account) {
        Optional<Customer> customerOptional = customerRepository.findById(account.getCustomer().getId());
        Optional<Account> accountOptional = accountRepository.findById(account.getId());
        if(!customerOptional.isPresent() || accountOptional.isPresent()){
            return null;
        }
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Account account) {
        Optional<Account> optionalAccount = accountRepository.findById(account.getId());
        if(!optionalAccount.isPresent()) {
            return null;
        }
        Account savedAccount = optionalAccount.get();
        savedAccount.setBalance(account.getBalance());
        return accountRepository.save(savedAccount);
    }

    @Override
    public String deleteAccount(final Integer id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if(!optionalAccount.isPresent()) {
            return null;
        }
        Account account = optionalAccount.get();
        if(account.getBalance() > 0){
          return ACCOUNT_DELETE_FAIL;
        }
        accountRepository.delete(account);
        return ACCOUNT_DELETED;
    }


}
