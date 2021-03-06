package com.example.a0102;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
//Адаптер для списка продуктов в "холодильнике"
public class HomeAdapter extends BaseAdapter {

    //настройки
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
        DisplayMetrics displayMetrics = new DisplayMetrics();
        AppCompatActivity activity = (AppCompatActivity)ctx;
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int widht = displayMetrics.widthPixels;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(widht/3,widht/3);
        final ItemProduct p = getProduct(position);

        TextView r=view.findViewById(R.id.text);
        r.setText(p.name1);
        r.setTextSize(TypedValue.COMPLEX_UNIT_DIP,size);
        PieChartView pieChartView = view.findViewById(R.id.chart);
        List<SliceValue> pie = new ArrayList<>();

        //создание мини диаграммы для визуального отображения просроченных продуктов
            pie.add(new SliceValue(p.days-(p.days-p.days1),Color.parseColor("#ed6b02")));
            pie.add(new SliceValue(p.days-p.days1, Color.parseColor("#A39B9B")));

        PieChartData pieChartData = new PieChartData(pie);
        pieChartData.setHasCenterCircle(true).setCenterText1("");
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartView.setPieChartData(pieChartData);
        ImageView imageView=view.findViewById(R.id.image);
        imageView.setLayoutParams(lp);
        //если картинка берется из папки
       if(p.image1==666){
           try {
               ContextWrapper cw = new ContextWrapper(ctx);
               File directory = cw.getDir(p.image2, Context.MODE_PRIVATE);
               File mypath=new File(directory,p.image2);

               File f=new File(mypath+"");
               Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
               imageView.setImageBitmap(b);
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           }
       }
       
       else{
           imageView.setImageResource(p.image1);
       }

        return view;
    }

}
