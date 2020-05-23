package com.example.a0102;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import static com.example.a0102.Settings.LANGUAGE;
import static com.example.a0102.Settings.PREFERENCES;
/* 
    Диалоговое окно для просмотра информации о продукте
*/
public class MyDialog extends DialogFragment  {
    private String name;
    private String dayc;
    private String monthc;
    private String yearc;
    private String image;
    private int days;
    private int day;
    int lan;
    String st;
    SharedPreferences mSettings;
    String [] names=new String[5];
    int k1=0;
    int i=0;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        int [] arrayimage={
                R.drawable.g1,R.drawable.g2,R.drawable.g3,R.drawable.g4,R.drawable.g5,R.drawable.g6,
                R.drawable.g7,R.drawable.g8,R.drawable.g9,R.drawable.g10,R.drawable.g11,R.drawable.g12,R.drawable.g13, R.drawable.g14,
                R.drawable.cake,R.drawable.g15,R.drawable.g16,R.drawable.g17,R.drawable.g18,R.drawable.g19,R.drawable.g20,R.drawable.g21,R.drawable.g22,
                R.drawable.g24,R.drawable.g25,R.drawable.g26,R.drawable.g27,R.drawable.g28,R.drawable.g29,R.drawable.g30,R.drawable.g31,
                R.drawable.g32,R.drawable.g33,R.drawable.g34,R.drawable.g35,
                R.drawable.g36,R.drawable.g37,R.drawable.g38,R.drawable.g39,R.drawable.g40,
                R.drawable.g41,R.drawable.g42,R.drawable.g43,R.drawable.g44,R.drawable.g45,R.drawable.g46,R.drawable.g47,R.drawable.g48,
                R.drawable.g49,R.drawable.g50,R.drawable.g51,R.drawable.g52,R.drawable.g53,R.drawable.g54,R.drawable.g55,R.drawable.g56,R.drawable.g57,R.drawable.g58,
                R.drawable.g59,R.drawable.g60,R.drawable.g61,R.drawable.g62,R.drawable.g63,R.drawable.g64,
                R.drawable.g65,R.drawable.g66,R.drawable.chicken,R.drawable.g67,R.drawable.g68,R.drawable.g69,R.drawable.g70,
                R.drawable.g72,R.drawable.g73,R.drawable.g74,R.drawable.g75,R.drawable.g76,R.drawable.g77,R.drawable.g78,R.drawable.g79,R.drawable.g80,R.drawable.g81,R.drawable.g82,R.drawable.g83,R.drawable.g84,R.drawable.g85,R.drawable.g86,R.drawable.g87,
                R.drawable.g88,R.drawable.g89,R.drawable.g90,R.drawable.g91,R.drawable.g92,
                R.drawable.g93,R.drawable.g94,R.drawable.g95,R.drawable.g96,R.drawable.g97,R.drawable.g98,R.drawable.g99,R.drawable.g100,R.drawable.g101,R.drawable.g102,R.drawable.g103,
                R.drawable.g104,R.drawable.g105,R.drawable.g106,R.drawable.g107,R.drawable.g108,R.drawable.g109,
                R.drawable.g110,R.drawable.g111,R.drawable.g112,R.drawable.g113,R.drawable.g114,R.drawable.g115,R.drawable.g116,R.drawable.g117,R.drawable.g118,R.drawable.g119,R.drawable.g120,R.drawable.g121,R.drawable.g122,R.drawable.apple,
                R.drawable.g124,R.drawable.g125,R.drawable.g126,R.drawable.g127,R.drawable.g128,
                R.drawable.g129,R.drawable.g130,R.drawable.g131,R.drawable.g132,R.drawable.g133,R.drawable.g134,R.drawable.g135,R.drawable.g136,
                R.drawable.g137,R.drawable.g138,R.drawable.g139,R.drawable.g140,R.drawable.g141,R.drawable.g142,R.drawable.g143,
                R.drawable.g144,R.drawable.g145,R.drawable.g146,R.drawable.question_115172
        };

        mSettings= getActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        if(mSettings.contains(LANGUAGE)) {
            lan = mSettings.getInt(LANGUAGE, 0);
        }
        else{
            lan=0;
        }
        if(lan==0){
            st="pp.txt";

        }
        else {
            st = "ppa.txt";
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getActivity().getAssets().open(st), "UTF-8"));
            String mLine;
            while ((mLine = reader.readLine())!= null) {
                k1=k1+1;
                if (k1<=217 && k1>=213) {
                    names[i]=mLine;
                    i+=1;
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

        View v = inflater.inflate(R.layout.info, null);

        TextView textView=v.findViewById(R.id.textView3);
        TextView textView0=v.findViewById(R.id.textView);
        TextView textView2=v.findViewById(R.id.textView4);
        TextView textView3=v.findViewById(R.id.textView5);
        TextView textView4=v.findViewById(R.id.textView6);
        ImageView imageView=v.findViewById(R.id.imageView);
        textView.setText(names[0]+" "+name);
        int k=Integer.parseInt(monthc)+1;
        textView2.setText(names[1]+" "+dayc+"."+k+"."+yearc);
        textView3.setText(names[2]+" "+day);
        textView4.setText(names[3]+" "+days);
        if(image.contains("146r")) {
            imageView.setImageResource(arrayimage[146]);
        }
        else {
            if (image.contains("r")) {
                try {
                    ContextWrapper cw = new ContextWrapper(getContext());
                    File directory = cw.getDir(image, Context.MODE_PRIVATE);
                    File mypath = new File(directory, image);
                    File f = new File(mypath + "");
                    Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                    imageView.setImageBitmap(b);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                int  index=Integer.valueOf(image);
                imageView.setImageResource(arrayimage[index]);
            }
        }
        textView0.setText(names[4]);
        builder.setView(v);
        return builder.create();
    }
//получение данных из класса
    public MyDialog(String name,int day,String dayc,String monthc,String yearc, String image,int days){
        this.name=name;
        this.dayc=dayc;
        this.monthc=monthc;
        this.yearc=yearc;this.image=image;
        this.days=days;
        this.day=day;
    }
}
