package com.example.a0102;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import static com.example.a0102.Settings.LANGUAGE;
import static com.example.a0102.Settings.PREFERENCES;


public class CheckProduct extends Fragment{



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
    public ArrayList<CheckProductExample> spisok = new ArrayList<CheckProductExample>();

    public CheckProduct()

    {
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        view = inflater.inflate(R.layout.checkproduct, container, false);
        view.setBackgroundColor(Color.parseColor("#1e1e1e"));
        dbHelper = new MYSQL(getContext());
        ImageView imageView=view.findViewById(R.id.plus);
     imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                LayoutInflater inflater1 = getLayoutInflater();
                View view1= inflater1.inflate(R.layout.checkproductdialog,null);
                builder1.setView(view1);
                final EditText name = view1.findViewById(R.id.edittext);
                final TextView text = view1.findViewById(R.id.textView7);
                if (lan==0){
                    text.setText("Введите имя продукта");
                }
                else{
                    text.setText("Enter the title" );
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
                                Fragment frg = new CheckProduct();
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.fragments, frg);
                                ft.commit();
                            }
                        });
                AlertDialog dialog = builder1.create();
                dialog.show();
                dialog.setCancelable(false);
                Button b = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                b.setTextColor(getResources().getColor(R.color.bad));
                Button b1 = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                b1.setTextColor(getResources().getColor(R.color.bad));

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
        lp.setMargins(0,height/5,0,0);

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

        adapter = new CheckProductAdapter(getContext(),spisok,size);
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
            textView.setTextColor(Color.parseColor("#ffffff"));
            textView.setGravity(Gravity.CENTER);
            frameLayout.addView(textView);
        }

       return view;
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
