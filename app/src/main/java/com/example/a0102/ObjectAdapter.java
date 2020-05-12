package com.example.a0102;


import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
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

        final ItemProduct p = getProduct(position);

        viewHolder.imageView.setImageResource(p.image1);
        viewHolder.nameView.setText(p.name1);
        viewHolder.nameView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
        viewHolder.dayView.setText(p.days + " ");

        viewHolder.dayView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);

        return convertView;
    }

    private class ViewHolder {
        final ImageView imageView;
        final TextView nameView, dayView;

        ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.image);
            nameView = (TextView) view.findViewById(R.id.text);
            dayView = (TextView) view.findViewById(R.id.textday);
        }

    }
}
