package com.example.a0102;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.a0102.News.PREFERENCES;

public class Add extends ListFragment{
    //настройки
    public static final String PREFERENCES = "mysettings";
    public static final String SIZE = "size";
    int size;
    SharedPreferences mSettings;
    ArrayList<Product> products = new ArrayList<Product>();
    MyAdapter adapter;
    MilkFragment frag2;
    FragmentTransaction fTrans;
    String [] name={
            "Грибы","Зелень","Кондитерские изделия","Крупы",
            "Лапша","Масла","Молочная продукция","Морепродукты", "Мясные продукты",
            "Мясо","Овощи","Орехи","Рыба","Сыры","Фрукты","Хлебобулочные изделия","Экзотические Фрукты",
            "Ягоды","Яичные продукты"};
    int [] image1 ={
            R.drawable.grib,R.drawable.greens, R.drawable.cake,R.drawable.rice,
            R.drawable.noodles,R.drawable.oil,R.drawable.milk,R.drawable.seafood,R.drawable.sosiska,
            R.drawable.chicken,R.drawable.lettuce,R.drawable.nut, R.drawable.fish,
            R.drawable.cheese, R.drawable.apple,R.drawable.bread,R.drawable.pineapple,
            R.drawable.cherry,R.drawable.egg,};

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fillData();
        mSettings= getActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        if(mSettings.contains(SIZE)) {
            size = mSettings.getInt(SIZE, 0);
        }
        else{
            size=30;
        }
        adapter = new MyAdapter(getContext(),products,size);
        setListAdapter(adapter);
        //настройка

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(Color.parseColor("#3f8678"));
        return view;
    }


 //заполнение списка
    private void fillData() {
        for (int i = 0; i <name.length ; i++) {
            products.add(new Product(name[i],image1[i]));
        }
    }


 //обработка нажатия на элемент списка
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);


            fTrans = getFragmentManager().beginTransaction();
            frag2 = new MilkFragment(position+1);
            fTrans.replace(R.id.fragments, frag2);
            fTrans.commit();

    }



//методы для фрагмента
    public static Add newInstance() {
        Add fragment = new Add();
        return fragment;
    }
    public Add() {}
}

