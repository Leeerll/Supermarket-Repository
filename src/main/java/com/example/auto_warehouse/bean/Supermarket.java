package com.example.auto_warehouse.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Supermarket {
    @Id
    private String suid;
    private String suregion;
    private String rid;
    public Supermarket(){

    }

    public Supermarket(String suid, String suregion, String rid) {
        this.suid = suid;
        this.suregion = suregion;
        this.rid = rid;
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


}
