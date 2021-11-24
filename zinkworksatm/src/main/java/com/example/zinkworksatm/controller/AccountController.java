package com.example.zinkworksatm.controller;

import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import com.example.zinkworksatm.model.Account;
import com.example.zinkworksatm.repository.AccountRepository;
import com.example.zinkworksatm.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
class AccountController{
  private static final Logger log=LoggerFactory.getLogger(AccountController.class);
  @Autowired private final AccountService service;

  public AccountController(AccountService service){
    this.service=service;
  }

  //Idempotent operation
  @GetMapping("/account/{accountNumber}/{pin}")
  ResponseEntity<Map<String,String>> getAccount(@PathVariable String accountNumber,@PathVariable String pin){
    Map<String,String> result=new HashMap<String,String>();
    log.info("getAccount");
    Account accountResult=this.service.getAccountById(accountNumber);
    log.info("Arguments: accountNumber="+accountNumber+", pin="+pin);
    log.info("accountResult="+accountResult);
    log.info("pin = ["+pin+"], retrieved pin = ["+accountResult.getPin()+"]");
    if(pin.equals(accountResult.getPin())){
      log.info("We have match.");
      result.put("accountNumber",accountResult.getAccountNumber());
      result.put("balance",Integer.toString(accountResult.getBalance()));
      result.put("overdraft",Integer.toString(accountResult.getOverdraft()));
      result.put("total limit",Integer.toString(accountResult.getBalance()+accountResult.getOverdraft()));
      return new ResponseEntity<>(result,HttpStatus.OK);
    }else{
      log.info("Didn't match.");
      result.put("message","Invalid credentials");
      return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
    }
  }
}
