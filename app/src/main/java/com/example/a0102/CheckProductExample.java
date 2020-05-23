package com.example.a0102;
/*
    Элемент списка в списке продуктов
*/
public class CheckProductExample {
    String name2;
    String status;
    String id2;

    CheckProductExample (String _name, String _status,String _id2) {
    name2=_name;
    status=_status;
    id2=_id2;
    }
    public String getName2() {
        return name2;
    }
    public String getStatus() {
        return status;
    }

    public String getId2() {
        return id2;
    }
}
