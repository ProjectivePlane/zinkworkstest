package com.example.zinkworksatm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.zinkworksatm.model.CashRecord;

public interface CashRecordRepository extends JpaRepository<CashRecord,Integer>
{

}
