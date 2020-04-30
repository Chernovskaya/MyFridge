package com.example.a0102;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import static android.widget.Toast.LENGTH_SHORT;


public class News extends Fragment {
    public static final String PREFERENCES = "mysettings";
    public static final String SIZE = "size";


    int s = 30;
    int d;
    TextView sizetext;
    SharedPreferences mSettings;
    SharedPreferences.Editor editor;
    int size;

    public News() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mSettings = getActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        editor = mSettings.edit();
        if(mSettings.contains(SIZE)) {
            size = mSettings.getInt(SIZE, 0);
        }
        else{
            size=30;
        }
        View view = inflater.inflate(R.layout.settings, container, false);
        sizetext = view.findViewById(R.id.sizetext);
        sizetext.setTextSize(TypedValue.COMPLEX_UNIT_DIP,size);
        sizetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1();
            }
        });
        view.setBackgroundColor(Color.parseColor("#008577"));



        return view;
    }

    public static News newInstance() {
        return new News();
    }


    void dialog1() {
        final String[] size = {"15", "20", "30"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выберете размер текста")
                .setSingleChoiceItems(size, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int item) {
                               d = Integer.parseInt(size[item]);
                                Toast.makeText(getActivity(), "Размер текста " + size[item], LENGTH_SHORT).show();
                            }
                        })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        s = d;
                        editor.putInt(SIZE, s);
                        editor.apply();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.show();
    }






}