package com.example.auto_warehouse.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
public class Supermarket {
    @Id
    private String suid;
    private String suregion;
    private String password;
    private String rid;
    private String bankCardNumber;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Supermarket(){

    }

    public Supermarket(String suid, String suregion, String rid,String bankCardNumber) {
        this.suid = suid;
        this.suregion = suregion;
        this.rid = rid;
        this.bankCardNumber = bankCardNumber;
    }

    public String getSuid() {
        return suid;
    }

    public void setSuid(String suid) {
        this.suid = suid;
    }

    public String getSuregion() {
        return suregion;
    }

    public void setSuregion(String suregion) {
        this.suregion = suregion;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setBankCardNumber(String bankCardNumber){
        this.bankCardNumber=bankCardNumber;
    }

    public String getBankCardNumber(){
        return bankCardNumber;
    }
}
