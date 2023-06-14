package ro.fortech.security.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
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
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<AccountModel> getCustomerAccount(@PathVariable("accountId") final Integer accountId){
        AccountModel account = accountService.getAccount(accountId);
        if(account == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
        Account savedAccount = accountService.createAccount(account);
        if(savedAccount == null){
           return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(savedAccount);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Account> updateAccount(@RequestBody final Account account){
        Account savedAccount = accountService.updateAccount(account);
        if(savedAccount == null){
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(savedAccount);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
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
