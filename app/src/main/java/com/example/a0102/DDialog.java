package com.example.a0102;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.util.ArrayList;
import java.util.List;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import static com.example.a0102.Settings.ALLALL;
import static com.example.a0102.Settings.GOBAD;
import static com.example.a0102.Settings.PREFERENCES;

/*
    Диалоговое окно для отображения статистики(общее количество изменяется при добавлении продукта в свой "холодильник",
    количество испорченных - во фрагменте Home при заполнение списка)
*/

public class DDialog extends DialogFragment {
    SharedPreferences mSettings;
    TextView textView1;
    TextView textView;
    private String total;
    private String eaten;
    private String spoiled;
    private String name;
    TextView textView2;
    LinearLayout linearLayout;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //получение настроек
            mSettings= getActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
            int gobad= mSettings.getInt(GOBAD, 0);
            int allall = mSettings.getInt(ALLALL, 0)-gobad;
        
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View v = inflater.inflate(R.layout.diagramma, null);
            builder.setView(v);
            textView1=v.findViewById(R.id.textView2);
            textView=v.findViewById(R.id.textView);
            textView1.setText(eaten+": "+allall);
            textView.setText(name);
            textView2=v.findViewById(R.id.textView1);
            textView2.setText(spoiled+": "+gobad);
            PieChartView pieChartView = v.findViewById(R.id.chart);
            List<SliceValue> pie = new ArrayList<>();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        linearLayout=v.findViewById(R.id.dia);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        params.height = 2*height/3;
        linearLayout.setLayoutParams(params);

            if(gobad==0){
                pie.add(new SliceValue(100,Color.parseColor("#98e867")));
            }
            else{
                pie.add(new SliceValue(allall,Color.parseColor("#98e867")));
                pie.add(new SliceValue(gobad, Color.parseColor("#f0ad2a")));
            }
            //создание диаграммы
            PieChartData pieChartData = new PieChartData(pie);
            pieChartData.setHasCenterCircle(true).setCenterText1(total).setCenterText1Color(Color.parseColor("#ffffff"));
            pieChartData.setHasLabels(true).setValueLabelTextSize(14);
            pieChartView.setPieChartData(pieChartData);
        return builder.create();
    }



    public DDialog( String name,String total,String spoiled,String eaten){
        this.name=name;
        this.total=total;
        this.spoiled=spoiled;
        this.eaten=eaten;


    }
}
