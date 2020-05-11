package com.example.a0102;

import java.util.Comparator;

public class ItemProduct {
    String name1;
    int image1;
    int days;
    int days1;
    int id;
    String image2;
    ItemProduct(String describe, int image, int _days,int _id,String image3,int _days1) {
        name1 = describe;
        image1 = image;
        days=_days;
        id=_id;
        image2=image3;
        days1=_days1;
    }
    public String getName() {
        return name1;
    }
    public int getImage() {
        return image1;
    }
    public String getImage1() {
        return image2;
    }
    public int getDays() {
        return days;
    }
    public int getDays1() {
        return days1;
    }
    public int getId() {
        return id;
    }
    public static final Comparator<ItemProduct> COMPARE = new Comparator<ItemProduct>() {
        @Override
        public int compare(ItemProduct first, ItemProduct second) {
            return first.getDays1() - second.getDays1();
        }
    };
}

