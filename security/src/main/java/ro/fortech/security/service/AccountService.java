package ro.fortech.security.service;

import ro.fortech.security.entity.Account;
import ro.fortech.security.model.AccountModel;

import javax.persistence.criteria.CriteriaBuilder;

public interface AccountService {

    AccountModel getAccount(final Integer id);

    Account createAccount(final Account account);

    Account updateAccount(final Account account);

    String deleteAccount(final Integer id);
}
