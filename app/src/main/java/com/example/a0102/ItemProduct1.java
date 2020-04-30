package com.example.a0102;

public class ItemProduct1 {
    String name1;
    int image1;
    int id1;
    ItemProduct1(String describe, int image,int _id1) {
        name1 = describe;
        image1 = image;
        id1=_id1;
    }
    public String getName() {
        return name1;
    }
    public int getImage() {
        return image1;
    }
    public int getId() {
        return id1;
    }

}
