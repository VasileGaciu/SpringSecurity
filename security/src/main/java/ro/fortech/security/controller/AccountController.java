package ro.fortech.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.fortech.security.entity.Account;
import ro.fortech.security.model.AccountModel;
import ro.fortech.security.service.AccountService;

import static ro.fortech.security.util.Constants.ACCOUNT_DELETE_FAIL;

@RestController()
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountModel> getCustomerAccount(@PathVariable("accountId") final Integer accountId){
        AccountModel account = accountService.getAccount(accountId);
        if(account == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }

    @PostMapping("/add")
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
        Account savedAccount = accountService.createAccount(account);
        if(savedAccount == null){
           return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(savedAccount);
    }

    @PutMapping("/update")
    public ResponseEntity<Account> updateAccount(@RequestBody final Account account){
        Account savedAccount = accountService.updateAccount(account);
        if(savedAccount == null){
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(savedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") final Integer id){
       String response = accountService.deleteAccount(id);
       if(response == null){
         return ResponseEntity.notFound().build();
       }
       if(ACCOUNT_DELETE_FAIL.equals(response)){
          return ResponseEntity.badRequest().body(response);
       }
       return ResponseEntity.ok().body(response);
    }
}
