package com.example.auto_warehouse.bean;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 超市在仓库中存储的货物及总量
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ShopStorage {
    @Id
    private String sname;

    //@TableField(exist = false)
    private int storageNum;

}
