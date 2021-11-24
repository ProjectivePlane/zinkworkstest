package com.example.zinkworksatm.service;
import java.util.ArrayList;
import java.util.List;
import com.example.zinkworksatm.model.Account;
import com.example.zinkworksatm.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService{
  @Autowired AccountRepository accountRepository;

  public List<Account> getAllAccounts(){
    List<Account> result=new ArrayList<Account>();
    accountRepository.findAll().forEach(acct -> result.add(acct));
    return result;
  }

  public Account getAccountById(String id){
    return accountRepository.findById(id).get();
  }

  public void saveOrUpdate(Account acct){
    accountRepository.save(acct);
  }
}
