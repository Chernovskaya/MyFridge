package com.example.a0102;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;
import static com.example.a0102.Settings.LANGUAGE;
import static com.example.a0102.Settings.PREFERENCES;
import static com.example.a0102.Settings.SIZE;

public class CheckProduct extends DialogFragment {

    LinearLayout linearLayout;

    //настройки
    SharedPreferences mSettings;
    int size;
    //БД
    MYSQL dbHelper;
    View view;
    int n = 1;
    int lan;
    String status;
    TextView textView;
    SQLiteDatabase db;
    String id1;
    ListView listView;
    String name;
    CheckProductAdapter adapter;
    private ArrayList<CheckProductExample> spisok = new ArrayList<CheckProductExample>();
    AlertDialog.Builder builder;


    //методы необходимые для фрагмента

    public CheckProduct()

    {
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {



        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.checkproduct, null);

        dbHelper = new MYSQL(getContext());
        builder.setView(view);
        ImageView imageView=view.findViewById(R.id.plus);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                LayoutInflater inflater1 = getLayoutInflater();
                View view1= inflater1.inflate(R.layout.checkproductdialog,null);
                builder1.setView(view1);
                final EditText name = view1.findViewById(R.id.edittext);
                if (lan==0){
                    builder1.setTitle("Введите имя продукта");
                }
                else{
                    builder1.setTitle("Enter the title" );
                }
                builder1.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        });
                builder1.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                ContentValues cv = new ContentValues();
                                cv.put("status",0);
                                cv.put("name", name.getText().toString());
                                db.insert("mytable1", null, cv);
                                onDismiss(getDialog());
                                DialogFragment dlg1=new CheckProduct();
                                dlg1.show(getFragmentManager(), "dlg1");
                            }
                        });
                builder1.show();
            }
        });

        FrameLayout frameLayout= view.findViewById(R.id.f);
        textView = view.findViewById(R.id.text);
        dbHelper = new MYSQL(getContext());
        Display display1 = getActivity().getWindowManager().getDefaultDisplay();
        Point size1 = new Point();
        try {
            display1.getRealSize(size1);
        } catch (NoSuchMethodError err) {
            display1.getSize(size1);
        }
        int height = size1.y;
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(height/2));

        //настройка

        mSettings = getActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        if (mSettings.contains(LANGUAGE)) {
            lan = mSettings.getInt(LANGUAGE, 0);
        } else {
            lan = 0;
        }
        if (lan == 0) {
            textView.setText("Список продуктов");
        } else {
            textView.setText("Grocery list");
        }

        db = dbHelper.getWritableDatabase();
        Cursor c = db.query("mytable1",null, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                n = 0;
                name = c.getString(c.getColumnIndexOrThrow("name"));
                status = c.getString(c.getColumnIndexOrThrow("status"));
                id1 = c.getString(c.getColumnIndexOrThrow("id"));
                spisok.add(new CheckProductExample(name,status,id1));
            }
            while (c.moveToNext());
        }
        listView = view.findViewById(R.id.list);
        DialogFragment dlg1=new CheckProduct();
        adapter = new CheckProductAdapter(getContext(),spisok,size,dlg1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),"dhtrfrt",Toast.LENGTH_SHORT).show();
            }
        });
        dbHelper.close();
        frameLayout.setLayoutParams(lp);

        if (n == 1) {
            textView = new TextView(getContext());
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            if (lan == 0) {
                textView.setText("Пусто");
            } else {
                textView.setText("Empty");
            }
            textView.setGravity(Gravity.CENTER);
            frameLayout.addView(textView);
        }

       return builder.create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }


}

/*
<style name="CheckBox" parent="Theme.AppCompat.Light">
<item name="colorControlNormal">
@android:color/colorrr
</item>

<item name="colorControlActivated">
@android:color/colorrr
</item>
</style>

*/
