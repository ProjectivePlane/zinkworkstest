package com.example.zinkworksatm.model;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;

public @Entity
class Account {
  private @Id String accountNumber;
  private String pin;
  private int balance;
  private int overdraft;

  public Account(){}

  public Account(String accountNumber,String pin,int balance,int overdraft){
    this.accountNumber=accountNumber;
    this.pin=pin;
    this.balance=balance;
    this.overdraft=overdraft;
  }

  public String getAccountNumber(){
    return this.accountNumber;
  }

  public String getPin(){
    return this.pin;
  }

  public int getBalance(){
    return this.balance;
  }

  public int getOverdraft(){
    return this.overdraft;
  }

  public void debit(int amount){
    if(amount<this.balance+this.overdraft){
      this.balance-=amount;
    }
  }
  
  @Override
  public boolean equals(Object o){
    if(o==null)
      return false;
    if(this==o)
      return true;
    if(!(o instanceof Account))
      return false;
    Account account=(Account)o;
    return Objects.equals(this.accountNumber,account.accountNumber) && Objects.equals(this.pin,account.pin) && this.balance==account.balance && this.overdraft==account.overdraft;
  }

  @Override
  public int hashCode(){
    return Objects.hash(this.accountNumber,this.pin,this.balance,this.overdraft);
  }

  @Override
  public String toString(){
    return "Account{accountNumber="+this.accountNumber+", pin="+this.pin+", balance="+this.balance+", overdraft="+this.overdraft+"}";
  }
}
