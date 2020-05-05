package com.example.a0102;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


import static com.example.a0102.MyDialog.SIZE;
import static com.example.a0102.Settings.LANGUAGE;
import static com.example.a0102.Settings.PREFERENCES;

public class Add extends ListFragment{

    //настройки
    int size;
    SharedPreferences mSettings;

    //списки
    ArrayList<Product> products = new ArrayList<Product>();
    MyAdapter adapter;

    //новый фрагмент
    Choose_product frag2;
    FragmentTransaction fTrans;

    //чтение из файла
    int i =0;
    String [] name= new String[19];
    String  st;
    int k=0;
    int lan;
    int [] image1 ={
            R.drawable.grib,R.drawable.greens, R.drawable.cake,R.drawable.rice,
            R.drawable.noodles,R.drawable.oil,R.drawable.milk,R.drawable.seafood,R.drawable.sosiska,
            R.drawable.chicken,R.drawable.lettuce,R.drawable.nut, R.drawable.fish,
            R.drawable.cheese, R.drawable.apple,R.drawable.bread,R.drawable.pineapple,
            R.drawable.cherry,R.drawable.egg,};

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //настройки
        mSettings= getActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        if(mSettings.contains(SIZE)) {
            size = mSettings.getInt(SIZE, 0);
        }
        else{
            size=30;
        }

        if(mSettings.contains(LANGUAGE)) {
            lan = mSettings.getInt(LANGUAGE, 0);
        }
        else{
            lan=0;

        }
        if(lan==0){
            st="pp.txt";
        }
        else{
            st="ppa.txt";
        }

        //чтение из файла
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getActivity().getAssets().open(st), "UTF-8"));
            String mLine;
            while ((mLine = reader.readLine()) != null && k<=164) {
                if (k>145){
                name[i]=mLine.replace(",","");
                i+=1;
                }
                k+=1;
            }
        } catch (IOException e) {

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {

                }
            }
        }
        fillData();
        adapter = new MyAdapter(getContext(),products,size);
        setListAdapter(adapter);
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
            frag2 = new Choose_product(position+1);
            fTrans.replace(R.id.fragments, frag2);
            fTrans.commit();
    }



//методы для фрагмента
    public static Add newInstance() {
        Add fragment = new Add();
        return fragment;
    }
    public Add() {

    }
}

