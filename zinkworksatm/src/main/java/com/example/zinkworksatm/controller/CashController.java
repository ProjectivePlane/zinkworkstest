package com.example.zinkworksatm.controller;

import java.util.Optional;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.example.zinkworksatm.model.Account;
import com.example.zinkworksatm.model.CashRecord;
import com.example.zinkworksatm.repository.AccountRepository;
import com.example.zinkworksatm.repository.CashRecordRepository;
import com.example.zinkworksatm.service.AccountService;
import com.example.zinkworksatm.service.CashRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
class CashController{
  private static final Logger log=LoggerFactory.getLogger(CashController.class);
  @Autowired private final AccountService accountService;
  @Autowired private final CashRecordService cashRecordService;

  CashController(AccountService accountService,CashRecordService cashRecordService){
    this.accountService=accountService;
    this.cashRecordService=cashRecordService;
  }

  @PostMapping("/cash/{accountNumber}/{pin}/{amount}")
  ResponseEntity<Map<String,String>> getCash(@PathVariable String accountNumber,@PathVariable String pin,@PathVariable String amount){
  Map<String,String> result=new HashMap<String,String>();
  Account accountResult=accountService.getAccountById(accountNumber);
  int amountInt;
  List<CashRecord> cashRecordList=this.cashRecordService.getAll();
  List<CashRecord> descendingCashRecordList=cashRecordList.stream().sorted((o1,o2)->(o2.getDenomination()-o1.getDenomination())).collect(Collectors.toList());
  List<Integer> notesPaidOutList=new LinkedList<Integer>();
  //Total amount of cash in machine.
  int totalCashAvailable=cashRecordList.stream()
                                  .map(x -> x.getDenomination()*x.getCount())
                                  .reduce(0,Integer::sum);
  int remainingAmount;
  log.info("Have account.");
  if(pin.equals(accountResult.getPin())){
    log.info("Can access account.");
    try{
      amountInt=Integer.parseInt(amount);
    }catch(NumberFormatException ex){
      //amount does not represent an integeter
      result.put("message","Invalid amount specified");
      return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
    }
    log.info("amountInt = "+amountInt+", balance="+accountResult.getBalance()+", overdraft="+accountResult.getOverdraft());

    if(amountInt<accountResult.getBalance()){
      log.info("Do not need overdraft, amountInt="+amountInt+", balance="+accountResult.getBalance()+", totalCashAvailable="+totalCashAvailable);
      remainingAmount=amountInt;
      for(CashRecord r: descendingCashRecordList){
        while(remainingAmount>=r.getDenomination() && r.hasNote()){
          notesPaidOutList.add(r.getDenomination());
          remainingAmount-=r.getDenomination();
          r.decrementCount();
        }
      }
      log.info("remainingAmount="+remainingAmount+",notesPaidOutList="+notesPaidOutList);
      result.put("balance",String.valueOf(accountResult.getBalance()-amountInt));
      result.put("notes dispensed",notesPaidOutList.toString());
      this.cashRecordService.saveOrUpdate(descendingCashRecordList);
      accountResult.debit(amountInt);
      accountService.saveOrUpdate(accountResult);
      return new ResponseEntity<>(result,HttpStatus.OK);
    }else if(amountInt<accountResult.getBalance()+accountResult.getOverdraft()){
      log.info("Need overdraft, amountInt="+amountInt+", balance="+accountResult.getBalance()+", totalCashAvailable="+totalCashAvailable);
      remainingAmount=amountInt;
      for(CashRecord r: descendingCashRecordList){
        while(remainingAmount>=r.getDenomination() && r.hasNote()){
          notesPaidOutList.add(r.getDenomination());
          remainingAmount-=r.getDenomination();
          r.decrementCount();
        }
      }
      //log.info("accountRepository="+accountRepository);
      log.info("remainingAmount="+remainingAmount+",notesPaidOutList="+notesPaidOutList);
      result.put("balance",String.valueOf(accountResult.getBalance()-amountInt));
      result.put("notes dispensed",notesPaidOutList.toString());
      this.cashRecordService.saveOrUpdate(descendingCashRecordList);
      accountResult.debit(amountInt);
      accountService.saveOrUpdate(accountResult);
      return new ResponseEntity<>(result,HttpStatus.OK);
    }else{
      log.info("Insufficient funds, amountInt="+amountInt+"balance+overdraft="+accountResult.getBalance()+accountResult.getOverdraft()+", totalCashAvailable="+totalCashAvailable);
      result.put("message","Insufficient funds");
      return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
    }
  }
  result.put("message","Invalid credentials");
  return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
}
}
