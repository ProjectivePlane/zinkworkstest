package com.example.zinkworksatm.configuration;

import com.example.zinkworksatm.repository.AccountRepository;
import com.example.zinkworksatm.repository.CashRecordRepository;
import com.example.zinkworksatm.model.Account;
import com.example.zinkworksatm.model.CashRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase{
  private static final Logger log=LoggerFactory.getLogger(LoadDatabase.class);
  @Bean
  CommandLineRunner initDatabase(AccountRepository accountRepository,CashRecordRepository cashRecordRepository){
  return args -> {
    log.info("initDatabase");
//      log.info("Preloading "+accountRepository.save(new Account("123456789","1234",800,200)));
//      log.info("Preloading "+accountRepository.save(new Account("987654321","4321",1230,150)));
//      log.info("Preloading "+cashRecordRepository.save(new CashRecord(50,10)));
//      log.info("Preloading "+cashRecordRepository.save(new CashRecord(20,30)));
//      log.info("Preloading "+cashRecordRepository.save(new CashRecord(10,30)));
//      log.info("Preloading "+cashRecordRepository.save(new CashRecord(5,20)));
    };
  }
}
