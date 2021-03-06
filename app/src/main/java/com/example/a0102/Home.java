package com.example.a0102;

import android.app.AlarmManager;
import android.app.AlertDialog;
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
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.ColorRes;
import androidx.annotation.RequiresApi;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import org.joda.time.DateTime;
import org.joda.time.Days;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import static android.content.Context.ALARM_SERVICE;
import static com.example.a0102.Settings.ALLALL;
import static com.example.a0102.Settings.GOBAD;
import static com.example.a0102.Settings.LANGUAGE;
import static com.example.a0102.Settings.PREFERENCES;
import static com.example.a0102.Settings.SIZE;

/* 
    Основной фрагмент, который предназначен для отображения выбранных продуктов, 
    переход во фрагмент для создания списка покупок.
*/
public class Home extends Fragment{

    FrameLayout layout;

    //настройки
    SharedPreferences mSettings;
    int size;

    //дата
    Date currentDate = new Date();
    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    String dateText = dateFormat.format(currentDate);

    //БД
    MYSQL dbHelper;
    Calendar cal;

    //порверка пустоты списка
    int n=0;
    TextView textView;


    int day;
    String name;
    FrameLayout frameLayout;

    //список
    HomeAdapter adapter;
    ListView listView;


    String image;
    int image1;
    SharedPreferences.Editor editor;
    String bool;
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
            R.drawable.g144,R.drawable.g145,R.drawable.g146,R.drawable.question_115172};
    private ArrayList<ItemProduct> spisok = new ArrayList<ItemProduct>();
    private ArrayList<ItemProduct> sort = new ArrayList<ItemProduct>();

    View view;

    int index;
    int id3;

    String mLine;
    String dayc;
    String monthc;
    String yearc;

    GregorianCalendar gcal;

    int k=0;
    int i =0;
    String names[]=new String[147];
    String names1[]=new String[147];
    int days;
    int lan;

    DialogFragment dlg;
    BufferedReader reader;
    Boolean is=true;
    LinearLayout mRootFrameLayout;
    EditText search;

     int height;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home, container, false);
        //переход в режим просмотра списка покупок
        ImageView imageView=view.findViewById(R.id.note);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        int widht= displayMetrics.widthPixels;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frg = new CheckProduct();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragments, frg);
                ft.commit();
            }
        });
        //программное создание разметки
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int)(height/12));
        layout=view.findViewById(R.id.nos);
        layout.setLayoutParams(lp1);

        LinearLayout frameLayoutpanel=view.findViewById(R.id.panel);
        FrameLayout.LayoutParams lp2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        FrameLayout.LayoutParams lp4 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,height/15);
        lp4.setMargins(widht/30,0,widht/30,0);
        frameLayoutpanel.setLayoutParams(lp4);
        lp2.setMargins(widht/30,height/20,widht/30,height/40);
        frameLayout=view.findViewById(R.id.fragmentsss);
        frameLayout.setLayoutParams(lp2);
        mRootFrameLayout =view.findViewById(R.id.kuku);


        //содзание для БД
        dbHelper = new MYSQL(getContext());


        //получение настроек
        mSettings= getActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        size = mSettings.getInt(SIZE, 0);
        mSettings= getActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        if(mSettings.contains(LANGUAGE)) {
            lan = mSettings.getInt(LANGUAGE, 0);
        }
        else{
            lan=0;
        }
         reader = null;
        // чтение русского языка из файла
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getActivity().getAssets().open("pp.txt"), "UTF-8"));

            while ((mLine = reader.readLine())!= null) {
                k=k+1;
                if (k<=146 || k==226) {
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

        i=0;
        k=0;


        //чтение английского языка из файла
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getActivity().getAssets().open("ppa.txt"), "UTF-8"));

            while ((mLine = reader.readLine())!= null) {
                k=k+1;
                if (k<=146 || k==226) {
                    names1[i] = mLine.replace(",", "");
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


        //прогрманное создание даты
        TextView main = view.findViewById(R.id.data);
        main.setTextSize(height/50);
        main.setText(dateText+" ");

        search=view.findViewById(R.id.search);

        //списки,адпаптеры
        fillData(view);
        Collections.sort(spisok, ItemProduct.COMPARE);


        //проверка на наличие испорченных продуктов
        if (is){
            TextView textView=new TextView(getContext());
            textView.setBackgroundResource(R.drawable.shape2);

            if(lan==0){
                textView.setText("Нет испорченных продуктов");
            }
            else{
                textView.setText("No spoiled foods");
            }
            textView.setPadding(10,5,10,5);
           FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER_VERTICAL;
            textView.setLayoutParams(lp);
            int width = displayMetrics.widthPixels/40;
            textView.setTextSize(width);
            layout.addView(textView);
        }
        //создание основного списка,адаптера
        listView = view.findViewById(R.id.listView2);
        adapter = new HomeAdapter(getContext(),spisok,size);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int opaa = spisok.get(i).getId();
                Info(opaa);//метод для открытия диалогового окна - информация о продукте
            }
        });


        //обработка нажатия на элемент списка
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               int opaa = spisok.get(position).getId();
               Dialog(opaa,view);//удаление из бд
               return true;
            }
        });


        //проверка на пустоту
        if (spisok.isEmpty()){
            n=1;
            textView=new TextView(getContext());
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            if(lan==0) { textView.setText("Пусто"); }
            else{textView.setText("Empty");}
                textView.setTextColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);
                frameLayout.addView(textView);
        }
        else{
            if (n==1) {
                frameLayout.removeView(textView);
                n=0;
            }
        }
        if (lan == 0) {search.setHint(names[146]);}else{search.setHint(names1[146]);}
        
        //поиск продукта в списке
        search.setTextSize(height/50);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                sort.clear();
                String search_= search.getText().toString().toLowerCase().trim();
                if(search_!="") {
                    for (int i = 0; i < spisok.size(); i++) {
                        if (spisok.get(i).getName().toLowerCase().contains(search_)) {
                            sort.add(new ItemProduct(spisok.get(i).getName(),spisok.get(i).getImage(),spisok.get(i).getDays(),spisok.get(i).getId(),spisok.get(i).getImage1(),spisok.get(i).getDays1()));
                        }
                    }
                }
                adapter = new HomeAdapter(getContext(),sort,size);
                listView.setAdapter(adapter);
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
        return view;
    }

    //заполнение списка из БД
    private void fillData(View view) {
        spisok.clear();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.query("mytable", null, null, null, null, null, null);

        if (c != null && c.moveToFirst()) {
            do {
                cal=Calendar.getInstance();
                dayc=c.getString(c.getColumnIndexOrThrow("day"));
                monthc=c.getString(c.getColumnIndexOrThrow("month"));
                yearc=c.getString(c.getColumnIndexOrThrow("year"));
                name = c.getString(c.getColumnIndexOrThrow("name"));
                day = Integer.valueOf(c.getString(c.getColumnIndexOrThrow("email")));
                int id=Integer.valueOf(c.getString(c.getColumnIndexOrThrow("id")));
                image = c.getString(c.getColumnIndexOrThrow("image"));
                bool=c.getString(c.getColumnIndexOrThrow("gobad"));
                for (int i = 0; i <names.length ; i++) {
                    if(name.contains(names[i])){
                        if (lan==1){
                            name=names1[i];
                            break;
                        }
                    }
                }
                //перевод слов, существующих в файле
                for (int i = 0; i <names1.length ; i++) {
                    if(name.contains(names1[i])){
                        if (lan==0){
                            name=names[i];
                        }
                        break;
                    }
                }
                int one_d=Integer.parseInt(dayc);
                int two_d=Integer.parseInt(cal.get(Calendar.DATE)+"");
                int one_m=Integer.parseInt(monthc);
                int two_m=Integer.parseInt(cal.get(Calendar.MONTH)+"");
                int one_y=Integer.parseInt(yearc);
                int two_y=Integer.parseInt(cal.get(Calendar.YEAR)+"");
                //если не истек срок годности продукта
                 if ((one_y==two_y && ( (one_m==two_m && one_d>two_d) || (one_m>two_m)))  || (one_y>two_y)    ) {
                     Calendar c3 = Calendar.getInstance();
                     c3.set(Calendar.MONTH, Integer.parseInt(monthc));
                     c3.set(Calendar.DATE, Integer.parseInt(dayc));
                     c3.set(Calendar.YEAR, Integer.parseInt(yearc));
                     Date three = c3.getTime();
                     gcal = new GregorianCalendar();
                     Calendar c4 = Calendar.getInstance();
                     c4.set(Calendar.MONTH, gcal.get(Calendar.MONTH));
                     c4.set(Calendar.DATE, gcal.get(Calendar.DAY_OF_MONTH));
                     c4.set(Calendar.YEAR, gcal.get(Calendar.YEAR));
                     Date four= c4.getTime();
                     int n = Days.daysBetween(new DateTime(four), new DateTime(three)).getDays();
                     if (image.contains("146r")) {
                         spisok.add(new ItemProduct(name, arrayimage[146], day, id, "",n));
                     } else {
                         if (image.contains("r")) {
                             spisok.add(new ItemProduct(name, 666, day, id, image,n));
                         } else {
                             image1 = Integer.parseInt(image);
                             spisok.add(new ItemProduct(name, arrayimage[image1], day, id, "",n));
                         }
                     }
                 }
                //если истек срок годности продукта
                 else{
                     is=false;
                     ContentValues values = new ContentValues();
                     values.put("gobad", "1");
                     if(bool.contains("0")){
                     db.update("mytable",values, "gobad" + "= ?", new String[]{"0"});
                     int update=mSettings.getInt(GOBAD,0)+1;
                         editor = mSettings.edit();
                         editor.putInt(GOBAD,update);
                         editor.apply();
                     }
                     creating(view,id,image);
                 }
            } while (c.moveToNext());
        }
        dbHelper.close();

    }

    //методы необходимые для фрагмента
    public Home() {
    }
    public static Home newInstance() {
        return new Home();
    }


//диалоговое окно для удаление из БД
    private void Dialog(final int opa, final View view3) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (lan==0){
            builder.setTitle("Удалить продукт");
            builder.setMessage("Вы действительно хотите удалить продукт?");
        }
        else{
            builder.setTitle("Delete product" );
            builder.setMessage(" Are you sure you want to delete the product?");
        }
        builder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    }
                });
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Fragment frg = new Home();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragments, frg);
                        ft.commit();
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete("mytable", "id = "+String.valueOf(opa),null);
                        dbHelper.close();

                        //создание будильника для оповещения
                        Intent myIntent = new Intent(getContext(),AlarmBroadcast.class);
                        PendingIntent pendingIntent=PendingIntent.getBroadcast(getContext(),opa, myIntent, 0);
                        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(ALARM_SERVICE);
                        alarmManager.cancel(pendingIntent);

                        //обновление списка
                        fillData(view3);
                        adapter.notifyDataSetChanged();
                    }
                });
        //использование AlertDialog для изменения дизайна кнопки
        AlertDialog dialog = builder.create();
        dialog.show();
        Button b = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        b.setTextColor(getResources().getColor(R.color.bad));
        Button b1 = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        b1.setTextColor(getResources().getColor(R.color.bad));

    }

//программное создание layout просроченных продуктов
void creating(final View view1,final int id, String foruri){
    //корневая среда
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    params.width=height/12;
    params.height=height/12;

    //объект
    LinearLayout linearLayout = new LinearLayout(getContext());
    linearLayout.setOrientation(LinearLayout.VERTICAL);

    //image
    final ImageView pic = new ImageView(getContext());
    if(foruri.contains("146r")) {
        pic.setImageResource(arrayimage[146]);
    }
        else {
        if (foruri.contains("r")) {
            try {
                ContextWrapper cw = new ContextWrapper(getContext());
                File directory = cw.getDir(foruri, Context.MODE_PRIVATE);
                File mypath = new File(directory, foruri);
                File f = new File(mypath + "");
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                pic.setImageBitmap(b);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            index=Integer.valueOf(foruri);
            pic.setImageResource(arrayimage[index]);
        }
    }
    pic.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    pic.setTag(id);
    pic.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int p= (int) pic.getTag();
            Dialog(p,view1);
        }
    });


    //создание вью элементов
    linearLayout.setBackgroundResource(R.drawable.fon1);
    linearLayout.addView(pic);
    mRootFrameLayout.addView(linearLayout,params);

    }

    //нажатие на элемента списка для получения информации о продукте
    private void Info (final int opa) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("mytable", null, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                cal=Calendar.getInstance();
                dayc=c.getString(c.getColumnIndexOrThrow("day"));
                monthc=c.getString(c.getColumnIndexOrThrow("month"));
                yearc=c.getString(c.getColumnIndexOrThrow("year"));
                name = c.getString(c.getColumnIndexOrThrow("name"));
                day = Integer.valueOf(c.getString(c.getColumnIndexOrThrow("email")));
                id3=Integer.valueOf(c.getString(c.getColumnIndexOrThrow("id")));
                image = c.getString(c.getColumnIndexOrThrow("image"));

            } while (c.moveToNext()&& opa!=id3);
        }
        for (int i = 0; i <names.length ; i++) {
            if(name.contains(names[i])){
                if (lan==1){
                    name=names1[i];
                    break;
                }
            }
        }
        for (int i = 0; i <names1.length ; i++) {
            if(name.contains(names1[i])){
                if (lan==0){
                    name=names[i];
                }
                break;
            }
        }
        dbHelper.close();
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.MONTH, Integer.parseInt(monthc));
        c1.set(Calendar.DATE, Integer.parseInt(dayc));
        c1.set(Calendar.YEAR, Integer.parseInt(yearc));
        Date dateOne = c1.getTime();
        gcal = new GregorianCalendar();
        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.MONTH, gcal.get(Calendar.MONTH));
        c2.set(Calendar.DATE, gcal.get(Calendar.DAY_OF_MONTH));
        c2.set(Calendar.YEAR, gcal.get(Calendar.YEAR));
        Date two= c2.getTime();
        days = Days.daysBetween(new DateTime(two), new DateTime(dateOne)).getDays();//рассчет оставшихся дней

        dlg = new MyDialog(name,day,dayc,monthc,yearc,image,days);//диалоговое окно с информацией о продукте
        dlg.show(getFragmentManager(), "dlg");

    }

}





