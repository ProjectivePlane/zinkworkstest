package com.example.zinkworksatm.model;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;

public @Entity
class CashRecord{
  private @Id Integer denomination;
  private int count;

  public CashRecord(){}

  public CashRecord(int denomination,int count){
    this.denomination=denomination;
    this.count=count;
  }

  public int getDenomination(){
    return this.denomination;
  }

  public int getCount(){
    return this.count;
  }

  public void decrementCount(){
    if(this.count>0)
      this.count--;
  }

  public boolean hasNote(){
    return this.count>0;
  }

  @Override
  public boolean equals(Object o){
    if(o==null)
      return false;
    if(this==o)
      return true;
    if(!(o instanceof CashRecord))
      return false;
    CashRecord cr=(CashRecord)o;
    return this.denomination==cr.denomination && this.count==cr.count;
  }

  @Override
  public int hashCode(){
    return Objects.hash(this.denomination,this.count);
  }

  @Override
  public String toString(){
    return "CashRecord{denomination="+this.denomination+", count="+this.count+"}";
  }
}
