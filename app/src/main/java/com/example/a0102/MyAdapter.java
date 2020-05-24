package com.example.a0102;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/*
    Адаптер для списка "категориии продутов"
*/
public class MyAdapter extends BaseAdapter {
    
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Product> objects;
    int size;

    MyAdapter(Context context, ArrayList<Product> products, int size1) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        size=size1;

    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    Product getProduct(int position) {
        return ((Product) getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_item, parent, false);
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int widht = displayMetrics.widthPixels;
        FrameLayout frameLayout = view.findViewById(R.id.imag);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(widht/3,widht/3);
        frameLayout.setLayoutParams(lp);
        Product p = getProduct(position);

        TextView r=view.findViewById(R.id.text);
        r.setText(p.name);
        r.setTextSize(TypedValue.COMPLEX_UNIT_DIP,size);
        ((ImageView) view.findViewById(R.id.image)).setImageResource(p.image);



        return view;
    }

}

