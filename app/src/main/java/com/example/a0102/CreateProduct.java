package com.example.a0102;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.ALARM_SERVICE;
import static android.widget.Toast.LENGTH_SHORT;

public class CreateProduct extends Fragment {
    ImageView image;
    Button button;
    EditText name;
    EditText dayy;
    TextView date;
    final int Pick=1;
    ControlSQL dbHelper;

    DateFormat dateFormat;
    String name1;
    String dateText;
    Date currentDate;
    int day1;
    View view;
    int year;
    int month;
    int day;
    GregorianCalendar gcal;
    int days;
    DatePickerDialog picker;
    Home frag2;
    FragmentTransaction fTrans;
    Calendar c1;
    TextView dayofmonth;
    int id1;
    int umnoj=1;
    Bitmap selectedImage;
    Uri uri;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        gcal = new GregorianCalendar();
        view = inflater.inflate(R.layout.create, container, false);

        dayofmonth=view.findViewById(R.id.dayofmonth1);
        dayofmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog3(dayofmonth);
            }
        });
        image=view.findViewById(R.id.image);
        button =view.findViewById(R.id.button);
        dayy=view.findViewById(R.id.day);
        date=view.findViewById(R.id.date);
        currentDate = new Date();
        dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        dateText = dateFormat.format(currentDate);
        date.setText("дата изготовления: "+dateText+" ");
        dbHelper=new ControlSQL(getContext());
        name=view.findViewById(R.id.name);
        frag2 = new Home();
        days=0;
        c1 = Calendar.getInstance();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                day = cldr.get(Calendar.DAY_OF_MONTH);
                month = cldr.get(Calendar.MONTH);
                year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                if ((gcal.get(Calendar.YEAR) < year) || (gcal.get(Calendar.MONTH) < monthOfYear && gcal.get(Calendar.YEAR) == year) || (gcal.get(Calendar.MONTH) == monthOfYear && gcal.get(Calendar.YEAR) == year && gcal.get(Calendar.DAY_OF_MONTH) <=dayOfMonth)) {
                                    Toast.makeText(getContext(), "Невозможно установить дату", Toast.LENGTH_SHORT).show();
                                } else {
                                    date.setText("дата изготовления" + dayOfMonth + "." + (monthOfYear + 1) + "." + year);

                                    c1.set(Calendar.MONTH, monthOfYear);
                                    c1.set(Calendar.DATE, dayOfMonth);
                                    c1.set(Calendar.YEAR, year);
                                    Date dateOne = c1.getTime();
                                    Calendar c2 = Calendar.getInstance();
                                    c2.set(Calendar.MONTH, gcal.get(Calendar.MONTH));
                                    c2.set(Calendar.DATE, gcal.get(Calendar.DAY_OF_MONTH) );
                                    c2.set(Calendar.YEAR, gcal.get(Calendar.YEAR));
                                    Date two= c2.getTime();
                                    days = Days.daysBetween(new DateTime(dateOne), new DateTime(two)).getDays();
                                }
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                    try {
                        if(name.getText().length() == 0|| dayy.getText().length() == 0){
                            throw new NullPointerException("Пусто");
                        }
                        if(name.getText().length()!=0|| dayy.getText().length()!=0 ) {
                            day1 = (Integer.parseInt(dayy.getText().toString())) - days;
                            if (day1<=0){
                                throw new NullPointerException("Не рекомендуем к употреблению");

                            }
                            else {
                                c1.add(Calendar.DATE, day1);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                Cursor c = db.query("mytable", null, null, null, null, null, null);

                                ContentValues cv = new ContentValues();


                                cv.put("email", day1*umnoj);
                                cv.put("name", name.getText().toString());
                                cv.put("image",uri+"");
                                cv.put("day", c1.get(Calendar.DATE)+"");
                                cv.put("month", c1.get(Calendar.MONTH)+"");
                                cv.put("year", c1.get(Calendar.YEAR)+"");
                                db.insert("mytable", null, cv);
                                if (c != null && c.moveToFirst()) {
                                    do{
                                        id1=Integer.valueOf(c.getString(c.getColumnIndexOrThrow("id")));
                                    }while (c.moveToNext());
                                }
                                dbHelper.close();

                                Intent intent = new Intent(getContext(), AlarmBroadcast.class);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        getContext(), id1, intent, 0);
                                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ (day1 * 1000), pendingIntent);

                                fTrans = getFragmentManager().beginTransaction();
                                fTrans.replace(R.id.fragments, frag2);
                                fTrans.commit();
                            }
                        }
                    }
                    catch (NullPointerException e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent photo = new Intent(Intent.ACTION_PICK);
                photo.setType("image/*");
                startActivityForResult(photo, Pick);
            }
        });

        return view;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case Pick:
                if(resultCode == RESULT_OK){
                    try {
                        uri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContext().getContentResolver().openInputStream(uri);
                        selectedImage = BitmapFactory.decodeStream(imageStream);
                        image.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }



    }









    public void Dialog3(final TextView dayofmonth1){
        final String[] array = {"дни","месяцы"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выберете")
                // добавляем переключатели
                .setSingleChoiceItems(array, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int item) {
                                Toast.makeText( getActivity(),array[item],LENGTH_SHORT).show();
                                if (item==0){
                                    umnoj=1;
                                }
                                else{
                                    umnoj=30;
                                }
                            }
                        })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (umnoj==1){
                            dayofmonth1.setText("дни");
                        }
                        else{
                            dayofmonth1.setText("месяцы");
                        }
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        umnoj=1;
                    }
                });

        builder.show();
        return;
    }

}