package com.example.a0102;


import android.content.Context;
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

public class ObjectAdapter extends BaseAdapter {

    //настройки
    int size;
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<ItemProduct> objects;

    ObjectAdapter(Context context, ArrayList<ItemProduct> products, int size1) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        size = size1;
    }

    ItemProduct getProduct(int position) {
        return ((ItemProduct) getItem(position));
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


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;


        if (convertView == null) {
            convertView = lInflater.inflate(R.layout.list_item1, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        AppCompatActivity activity = (AppCompatActivity)ctx;
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int widht = displayMetrics.widthPixels;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(widht/3,widht/3);

        final ItemProduct p = getProduct(position);

        viewHolder.imageView.setImageResource(p.image1);
        viewHolder.imageView.setLayoutParams(lp);
        viewHolder.nameView.setText(p.name1);
        viewHolder.nameView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);


        return convertView;
    }

    private class ViewHolder {
        final ImageView imageView;
        final TextView nameView;

        ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.image);
            nameView = (TextView) view.findViewById(R.id.text);

        }

    }
}
