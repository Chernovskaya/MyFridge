package com.example.a0102;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import static com.example.a0102.Settings.ALLALL;
import static com.example.a0102.Settings.GOBAD;
import static com.example.a0102.Settings.SIZE;


public class SplashZastavka extends Activity {
    public static final String PREFERENCES = "mysettings";
    public static final String VISIT = "visit";
    SharedPreferences mSettings;
    SharedPreferences.Editor editor;

    private static int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        mSettings = this.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        editor = mSettings.edit();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSettings= getApplication().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
                if(mSettings.contains(VISIT)) {
                    Intent i = new Intent(SplashZastavka.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    editor.putInt(SIZE, height/60);
                    Intent i = new Intent(SplashZastavka.this, Instruction.class);
                    startActivity(i);
                    finish();
                    editor.putInt(VISIT, 1);
                    editor.putInt(ALLALL, 0);
                    editor.putInt(GOBAD, 0);
                    editor.apply();
                }




            }
        }, SPLASH_TIME_OUT);
    }
}


