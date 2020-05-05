package com.example.a0102;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.ALARM_SERVICE;
import static android.widget.Toast.LENGTH_SHORT;
import static com.example.a0102.News.LANGUAGE;
import static com.example.a0102.News.PREFERENCES;

public class CreateProduct extends Fragment {
    ImageView image;
    Button button;
    EditText name;
    EditText dayy;
    TextView date;
    final int Pick=1;
    ControlSQL dbHelper;
    DateFormat dateFormat;
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
    String folderToSave;
    String name2;
    Calendar cldr;
    OutputStream fOut;
    String name3;
    SharedPreferences mSettings;
    int lan;
    String st;
    int k=0;
    int i=0;
    String [] names = new String [12];



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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
        else{
            st="ppa.txt";
        }


        BufferedReader reader = null;

        String mLine;




        reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getActivity().getAssets().open(st), "UTF-8"));

            while ((mLine = reader.readLine())!= null) {
                k=k+1;
                if (k<=194 && k>=183) {
                    names[i] = mLine.replace(",", "");
                    i += 1;
                }
            }
        }

        catch (IOException e) {

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }



















        fOut=null;
        folderToSave = getContext().getCacheDir().toString();
        gcal = new GregorianCalendar();
        view = inflater.inflate(R.layout.create, container, false);
        dayofmonth=view.findViewById(R.id.dayofmonth1);
        dayofmonth.setText(names[3]);
        dayofmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog3(dayofmonth);
            }
        });
        image=view.findViewById(R.id.image);
        button =view.findViewById(R.id.button);
        dayy=view.findViewById(R.id.day);
        dayy.setHint(names[2]);
        date=view.findViewById(R.id.date);
        currentDate = new Date();
        dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        dateText = dateFormat.format(currentDate);
        date.setText(names[1]+" "+dateText+" ");
        dbHelper=new ControlSQL(getContext());
        name=view.findViewById(R.id.name);
        name.setHint(names[0]);
        frag2 = new Home();
        days=0;
        c1 = Calendar.getInstance();
        cldr = Calendar.getInstance();
        name3="146";
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                day = cldr.get(Calendar.DAY_OF_MONTH);
                month = cldr.get(Calendar.MONTH);
                year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                if ((gcal.get(Calendar.YEAR) < year) || (gcal.get(Calendar.MONTH) < monthOfYear && gcal.get(Calendar.YEAR) == year) || (gcal.get(Calendar.MONTH) == monthOfYear && gcal.get(Calendar.YEAR) == year && gcal.get(Calendar.DAY_OF_MONTH) <dayOfMonth)) {
                                    Toast.makeText(getContext(), names[4], Toast.LENGTH_SHORT).show();
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
                            throw new NullPointerException(names[6]);
                        }
                        if(name.getText().length()!=0|| dayy.getText().length()!=0 ) {
                                try {
                                    day1 = (Integer.parseInt(dayy.getText().toString())) - days;

                                }
                                catch (NumberFormatException e){
                                    Toast.makeText(getContext(),names[7], LENGTH_SHORT).show();
                                    day1=14;
                                }

                            if (day1<=0){
                                throw new NullPointerException(names[8]);

                            }
                            else {
                                c1.add(Calendar.DATE, day1);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                Cursor c = db.query("mytable1", null, null, null, null, null, null);

                                ContentValues cv = new ContentValues();


                                cv.put("email", day1*umnoj);
                                cv.put("name", name.getText().toString());
                                cv.put("image",name3+"r");
                                cv.put("day", c1.get(Calendar.DATE)+"");
                                cv.put("month", c1.get(Calendar.MONTH)+"");
                                cv.put("year", c1.get(Calendar.YEAR)+"");
                                db.insert("mytable1", null, cv);
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
                                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ day1 * 1000*umnoj, pendingIntent);

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
                        name3=cldr.getTimeInMillis()+"";
                        uri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContext().getContentResolver().openInputStream(uri);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 4;
                        selectedImage = BitmapFactory.decodeStream(imageStream,null,options);

                        image.setImageBitmap(selectedImage);

                        saveToInternalStorage(selectedImage);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    public void Dialog3(final TextView dayofmonth1){
        final String[] array = {names[3],names[9]};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(names[10])
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
                            dayofmonth1.setText(names[3]);
                        }
                        else{
                            dayofmonth1.setText(names[9]);
                        }
                    }
                })
                .setNegativeButton(names[11], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        umnoj=1;
                    }
                });

        builder.show();
        return;
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getContext());
        File directory = cw.getDir(name3+"r", Context.MODE_PRIVATE);
        File myp=new File(directory,name3+"r");
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(myp);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}