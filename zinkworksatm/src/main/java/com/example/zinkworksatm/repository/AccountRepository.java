package com.example.zinkworksatm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.zinkworksatm.model.Account;

public interface AccountRepository extends JpaRepository<Account,String>
{

}
