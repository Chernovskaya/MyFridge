package com.example.a0102;

public class ItemProduct {
    String name1;
    int image1;
    int days;
    int id;
    String image2;
    ItemProduct(String describe, int image, int _days,int _id,String image3) {
        name1 = describe;
        image1 = image;
        days=_days;
        id=_id;
        image2=image3;
    }
    public String getName() {
        return name1;
    }
    public String getImage2() {
        return image2;
    }
    public int getImage() {
        return image1;
    }
    public int getDays() {
        return days;
    }
    public int getId() {
        return id;
    }
}

