package com.example.a0102;


import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import static com.example.a0102.Settings.PREFERENCES;
import static com.example.a0102.Settings.SIZE;

public class CheckProductAdapter extends BaseAdapter implements DialogInterface.OnDismissListener{

    //настройки
    int size;
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<CheckProductExample> objects;
    SQLiteDatabase db;
    SharedPreferences mSettings;

    MYSQL dbHelper;
    DialogInterface dlg;

    @Override
    public void onDismiss(DialogInterface dialogInterface) {

    }


    CheckProductAdapter(Context context, ArrayList<CheckProductExample> products, int size1) {

        ctx = context;
        objects = products;

        size = size1;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    CheckProductExample getProduct(int position) {
        return ((CheckProductExample) getItem(position));
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
            view = lInflater.inflate(R.layout.checkboxadapter, parent, false);
        }

        mSettings = ctx.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        if (mSettings.contains(SIZE)) {
            size = mSettings.getInt(SIZE, 0);
        } else {
            size = 30;
        }
        dbHelper = new MYSQL(ctx);
        final CheckProductExample p = getProduct(position);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        checkBox.setText(p.name2);
        checkBox.setTextSize(TypedValue.COMPLEX_UNIT_DIP,size);
        checkBox.setTag(position);
        if(p.status.contains("1")){
            checkBox.setChecked(true);
        }
        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 db = dbHelper.getWritableDatabase();
                if (((CheckBox) v).isChecked()) {
                    ContentValues values = new ContentValues();
                    values.put("status", "1");
                    if(p.status.contains("0")){
                        db.update("mytable1",values, "status" + "= ? "+"AND "+"id "+ "= ? ", new String[]{"0",p.getId2()});
                        dbHelper.close();
                    }
                }
                else{
                    db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("status", "0");
                    if(p.status.contains("1")){
                        db.update("mytable1",values, "status" + "= ? "+"AND "+"id "+ "= ? ", new String[]{"1",p.getId2()});
                        dbHelper.close();
                    }
                }
            }
        });
    ImageView imageView = view.findViewById(R.id.rub);
    imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            db.delete("mytable1", "id = "+String.valueOf(p.id2),null);
            Fragment frg = new CheckProduct();
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragments, frg);
            ft.commit();
        }
    });
        return view;
    }



}




