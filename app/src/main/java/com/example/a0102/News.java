package com.example.a0102;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.widget.Toast.LENGTH_SHORT;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class News extends Fragment {
    public static final String PREFERENCES = "mysettings";
    public static final String SIZE = "size";
    public static final String LANGUAGE = "language";



    int s = 30;
    int d;
    TextView sizetext;
    TextView language;
    TextView how;
    SharedPreferences mSettings;
    SharedPreferences.Editor editor;
    int size;
    String st;
    int lan;
    String [] names=new String[12];
    int k=0;
    int i=0;
    public News() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final String[] sizes = {"15", "20", "30"};

        View view = inflater.inflate(R.layout.settings, container, false);
        mSettings = getActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        editor = mSettings.edit();

        if(mSettings.contains(SIZE)) {
            size = mSettings.getInt(SIZE, 0);
        }
        else{
            size=30;
        }
        if(mSettings.contains(LANGUAGE)) {
            lan = mSettings.getInt(LANGUAGE, 0);
        }
        else{
            lan=0;
        }
        if(lan==0){
            st="pp.txt";
        }
        else{
            st="ppa.txt";
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getActivity().getAssets().open(st), "UTF-8"));
            String mLine;
            while ((mLine = reader.readLine())!= null) {
                k=k+1;
                if (k<=207 && k>=196) {
                    names[i] = mLine.replace(",", "");
                    i += 1;
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
        TextView textView=view.findViewById(R.id.set);
        textView.setText(names[0]);
        final String[] languages = {names[3],names[4]};
        sizetext = view.findViewById(R.id.sizetext);
        how=view.findViewById(R.id.how);
        how.setText(names[10]);
        how.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2();
            }
        });
        how.setTextSize(TypedValue.COMPLEX_UNIT_DIP,size);
        sizetext.setText(names[1]);
        language=view.findViewById(R.id.language);
        language.setText(names[2]);
        sizetext.setTextSize(TypedValue.COMPLEX_UNIT_DIP,size);
        language.setTextSize(TypedValue.COMPLEX_UNIT_DIP,size);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1(languages,names[5],names[6],1);
            }
        });
        sizetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1(sizes,names[7],names[8],0);
            }
        });
        view.setBackgroundColor(Color.parseColor("#008577"));
        return view;
    }

    public static News newInstance() {
        return new News();
    }

    void dialog1(final String opa [], String title, final String mess, final int id1) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setSingleChoiceItems(opa, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int item) {
                                if(id1==0){ d = Integer.parseInt(opa[item]);}
                                if(id1==1){st=opa[item];}
                                //Toast.makeText(getActivity(), mess + opa[item], LENGTH_SHORT).show();
                                }
                        })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(id1==0) {
                            s = d;
                            editor.putInt(SIZE, s);
                            Fragment frg = new News();
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.fragments, frg);
                            ft.commit();
                            editor.apply();
                        }
                        if (id1==1){

                            if(st.contains("Рус")||st.contains("Rus"))
                            {
                                editor.putInt(LANGUAGE, 0);
                                editor.apply();
                            }
                            else{
                                editor.putInt(LANGUAGE, 1);
                                editor.apply();
                            }
                            int r  = mSettings.getInt(LANGUAGE, 0);
                            Log.i(TAG, ""+r);
                            Fragment frg = new News();
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.fragments, frg);
                            ft.commit();

                        }
                        }
                })
                .setNegativeButton(names[9], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.show();
    }
    void dialog2() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(names[10])
                .setMessage(names[11])

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getContext(), How.class);
                        startActivity(i);


                    }
                })
                .setNegativeButton(names[9], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.show();
    }






}