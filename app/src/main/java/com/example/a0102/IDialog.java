package com.example.a0102;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import static com.example.a0102.Settings.LANGUAGE;
import static com.example.a0102.Settings.PREFERENCES;

public class IDialog extends DialogFragment {
    SharedPreferences mSettings;
    TextView textView;
   FrameLayout linearLayout;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //получение настроек
        mSettings= getActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        int lan= mSettings.getInt(LANGUAGE, 0);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.informationabout, null);
        builder.setView(v);
        textView=v.findViewById(R.id.text);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        linearLayout=v.findViewById(R.id.dia);
        LinearLayout.LayoutParams lp = new  LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,2*height/3 );
        linearLayout.setLayoutParams(lp);
        textView.setTextSize(height/80);
        if(lan==0){
            textView.setText("  Основной задачей приложения «MyFridge» является отслеживание срока годности продуктов в вашем холодильнике. \n" +
                    "   В разделе «+» можно добавить предложенный товар, изменив дату производства и срок годности продукта. Если в предложенном списке не оказалось нужного товара, можно создать самому, введя необходимые данные. После истечения срока годности Вам на телефон придет уведомление.\n" +
                    "   В разделе «дом» находятся содержимое вашего виртуального холодильника. В верхней панели располагаются испорченные товары. Для того чтобы удалить продукт его следует долго удерживать. В этом разделе можно перейти к режиму просмотра списка покупок для похода в магазин.\n" +
                    "   В разделе «настройки» Вы можете выбрать предпочтения во внешнем виде приложения и посмотреть свою статистику.\n" +
                    "   Приложению необходимы разрешения:\n" +
                    "1.\tРассылка уведомлений\n" +
                    "2.\tДоступ к памяти устройства (для загрузки изображения при создании собственного продукта)\n" +
                    "   Для того, чтобы улучшить работу приложения, Вы можете написать на почту ychernovskaya@gmail.com\n" +
                    "Автор приложения: Я.Т. Черновская\n");
        }
        else{
           textView.setText("   MyFridge is the tracking of the shelf life of products in your refrigerator.\n" +
                   "    In the \"+\" section, you can add the proposed product. If the required product is not indicated in the proposed list, you can create it yourself and enter the necessary data. After the expiration date.\n" +
                   "    The home section contains the contents of your virtual refrigerator. In the upper panel there are spoiled goods. The product should be held for a long time. In this section, you can switch to the mode of viewing the list of customers for shopping.\n" +
                   "    In the \"settings\" section, you can select preferences in the external video application and see your statistics.\n" +
                   "    The application needs permissions:\n" +
                   "1. Newsletter\n" +
                   "2. Access to the device’s memory (for downloading images when creating your own product)\n" +
                   "    In order to improve the application, you can write to ychernovskaya@gmail.com\n" +
                   "Application author: Yana Chernovskaya");
        }
        return builder.create();
    }

    public IDialog( ){
    }
}
