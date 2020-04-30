package com.example.a0102;

import android.app.Activity;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class HomeAdapter extends BaseAdapter {
    //настройки
    public static final String PREFERENCES = "mysettings";
    public static final String SIZE = "size";
    SharedPreferences.Editor editor;
    SharedPreferences mSettings;
    int size;


Context ctx;
    LayoutInflater lInflater;
    ArrayList<ItemProduct> objects;
    RecyclerView.ViewHolder viewHolder;
    HomeAdapter(Context context, ArrayList<ItemProduct> products,int size1) {
        ctx = context;
        objects = products;
        size=size1;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_item2, parent, false);

        }
        final ItemProduct p = getProduct(position);

        TextView r=view.findViewById(R.id.text);
        r.setText(p.name1);
        r.setTextSize(TypedValue.COMPLEX_UNIT_DIP,size);

        TextView r1=view.findViewById(R.id.textView2);
        r1.setText(p.days+"");
        r1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,size);



        ImageView imageView=view.findViewById(R.id.image);

       if(p.image1==666){
           try {
               final InputStream imageStream = ctx.getContentResolver().openInputStream(Uri.parse(p.image2));
               Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
               imageView.setImageBitmap(selectedImage);
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           }
       }
       else{
           imageView.setImageResource(p.image1);
       }

        return view;
    }
    //////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////

}