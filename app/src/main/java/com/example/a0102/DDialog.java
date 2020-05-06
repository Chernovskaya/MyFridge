package com.example.a0102;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import java.util.ArrayList;
import java.util.List;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import static com.example.a0102.Settings.ALLALL;
import static com.example.a0102.Settings.GOBAD;
import static com.example.a0102.Settings.PREFERENCES;


public class DDialog extends DialogFragment {
    SharedPreferences mSettings;
    TextView textView1;
    TextView textView;
    private String total;
    private String eaten;
    private String spoiled;
    private String name;
    TextView textView2;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mSettings= getActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        int gobad= mSettings.getInt(GOBAD, 0);
        int allall = mSettings.getInt(ALLALL, 0)-gobad;
        View v = inflater.inflate(R.layout.diagramma, null);
        textView1=v.findViewById(R.id.textView2);
        textView=v.findViewById(R.id.textView);
        textView1.setText(eaten+": "+allall);
        textView.setText(name);
        textView2=v.findViewById(R.id.textView1);
        textView2.setText(spoiled+": "+gobad);
        PieChartView pieChartView = v.findViewById(R.id.chart);
        List<SliceValue> pie = new ArrayList<>();

        if(gobad==0){
            pie.add(new SliceValue(100,Color.parseColor("#98e867")));
        }
        else{
            pie.add(new SliceValue(allall,Color.parseColor("#98e867")));
            pie.add(new SliceValue(gobad, Color.parseColor("#fd4100")));
        }

        PieChartData pieChartData = new PieChartData(pie);
        pieChartData.setHasCenterCircle(true).setCenterText1(total);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartView.setPieChartData(pieChartData);
        return v;
    }

    public DDialog( String name,String total,String spoiled,String eaten){
        this.name=name;
        this.total=total;
        this.spoiled=spoiled;
        this.eaten=eaten;


    }
}