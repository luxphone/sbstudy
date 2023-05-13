package com.example.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "t_booktype")
public class Booktype implements Serializable {

    
    @Id(keyType = KeyType.Auto)
    private Integer id;

    
    private String name;

    
    private Timestamp createtime;

    
    private Timestamp updatetime;

}
