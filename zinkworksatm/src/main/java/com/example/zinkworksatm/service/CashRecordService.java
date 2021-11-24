package com.example.zinkworksatm.service;
import java.util.ArrayList;
import java.util.List;
import com.example.zinkworksatm.model.CashRecord;
import com.example.zinkworksatm.repository.CashRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CashRecordService{
  @Autowired CashRecordRepository CashRecordRepository;

  public List<CashRecord> getAll(){
    List<CashRecord> result=new ArrayList<CashRecord>();
    CashRecordRepository.findAll().forEach(acct -> result.add(acct));
    return result;
  }

  public CashRecord getCashRecordById(Integer id){
    return CashRecordRepository.findById(id).get();
  }

  public void saveOrUpdate(Iterable<CashRecord> cr){
    for(CashRecord r:cr){
      CashRecordRepository.save(r);
    }
  }
}
