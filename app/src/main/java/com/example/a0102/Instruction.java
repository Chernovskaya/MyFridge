package com.example.a0102;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.example.a0102.Settings.LANGUAGE;
import static com.example.a0102.Settings.PREFERENCES;

public class Instruction extends AppCompatActivity {
    int [] image;
    int i=0;
    Button btn1,btn2,btn3;
    SharedPreferences mSettings;
    SharedPreferences.Editor editor;

    LinearLayout layout1,first,buttons;
    int lan;
    String st;
    int k;
    int i1=0;
    String [] names=new String[3];
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mSettings = getApplication().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        editor = mSettings.edit();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how);

        btn1=findViewById(R.id.button3);
        if(mSettings.contains(LANGUAGE)) {
            lan = mSettings.getInt(LANGUAGE, 0);
        }
        else{
            lan=0;
        }
        if(lan==0){
            st="pp.txt";
            image= new int[]{R.drawable.r1,R.drawable.r2,R.drawable.r3,R.drawable.r4,R.drawable.r5};
        }
        else{
            st="ppa.txt";
            image= new int[]{R.drawable.e1,R.drawable.e2,R.drawable.e3,R.drawable.e4,R.drawable.e5};
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getApplicationContext().getAssets().open(st), "UTF-8"));
            String mLine;
            while ((mLine = reader.readLine())!= null) {
                k=k+1;
                if (k<=211 && k>=209) {
                    names[i1] = mLine.replace(",", "");
                    i1 += 1;
                }
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
        btn2=findViewById(R.id.button4);
        btn2.setText(names[1]);
        btn3=findViewById(R.id.button2);
        layout1=findViewById(R.id.fon);
        first=findViewById(R.id.first);
        buttons=findViewById(R.id.fon);
        btn1.setText(names[2]);
        layout1.setBackgroundResource(image[i]);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i>0){
                    layout1.setBackgroundResource(image[i-1]);
                    i-=1;
                    btn2.setText(names[1]);
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i+=1;
                if(i<image.length){
                    layout1.setBackgroundResource(image[i]);
                    btn2.setText(names[1]);

                    if (i+1==image.length){
                        btn2.setText("Ок");
                    }
                }
                if(i==image.length){
                    Intent i = new Intent(Instruction.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
        btn3.setText(names[0]);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Instruction.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }


    }



