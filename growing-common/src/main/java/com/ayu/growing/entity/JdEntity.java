package com.ayu.growing.entity;

import lombok.Data;

@Data
public class JdEntity {

    public JdEntity(){}

    public JdEntity(String bookID,String bookName,String bookPrice){
        this.bookID=bookID;
        this.bookName=bookName;
        this.bookPrice=bookPrice;

    }

    private String bookID;
    private String bookName;
    private String bookPrice;
}
