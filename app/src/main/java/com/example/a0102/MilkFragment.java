package com.example.a0102;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static android.content.Context.ALARM_SERVICE;
import static android.widget.Toast.LENGTH_SHORT;

public class MilkFragment extends Fragment implements AdapterView.OnItemClickListener {
    ArrayList<ItemProduct> productss = new ArrayList<ItemProduct>();
    ArrayList<ItemProduct> sort = new ArrayList<ItemProduct>();

    ObjectAdapter adapter;
    String selectedItem;
    String search;

    ControlSQL dbHelper;

    Date currentDate;

    DateFormat dateFormat;

   int day;
    String dateText;
   int imagee;

    int [] arrayimage;
    int [] days;
    int days1;
    String [] arrayname;

    String image;
    EditText mInputSearch;
    CreateProduct frag2;
    Home frag1;
    FragmentTransaction fTrans;
    FloatingActionButton fab;
    DatePickerDialog picker;
    int year;
    int month;
    int day1;
    GregorianCalendar gcal;
    LinearLayout view1;
    private int number;
    Calendar c1;
    //настройки
    public static final String PREFERENCES = "mysettings";
    public static final String SIZE = "size";
    int size;
    SharedPreferences mSettings;
    Date dateOne;
    Calendar c2;
    int id;
    int len1;
    int len2;
    int umnoj=1;

    public MilkFragment(int number) {
        this.number=number;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(number==1){len1=0;len2=5 ;}
        if(number==2){len1= 6;len2=12 ;}
        if(number==3){len1= 13;len2= 22;}
        if(number==4){len1=23 ;len2= 30;}
        if(number==5){len1= 31;len2= 34;}
        if(number==6){len1= 35;len2= 39;}
        if(number==7){len1=40 ;len2= 47;}
        if(number==8){len1= 48;len2= 57;}
        if(number==9){len1= 58;len2=63 ;}
        if(number==10){len1= 64;len2= 70;}
        if(number==11){len1=71 ;len2=86 ;}
        if(number==12){len1=87 ;len2= 91;}
        if(number==13){len1= 92;len2= 102;}
        if(number==14){len1= 103;len2= 108;}
        if(number==15){len1= 109;len2= 122;}
        if(number==16){len1= 123;len2=127 ;}
        if(number==17){len1=128 ;len2= 135;}
        if(number==18){len1= 136;len2= 142;}
        if(number==19){len1= 143;len2=145 ;}
            arrayname=new String[]{
                    "Лисички","Шампиньоны","Опята","Подосиновик","Рыжики","Масленок",

                    "Шпинат","Щавель","Укроп","Петрушка","Зеленый лук","Розмарин","Кинза",

                    "Пирожное","Торт","Халва","Мороженое","Мармелад","Печенье","Вафли","Пряник","Крем","Шоколад",

                    "Гречка","Кукурузная крупа","Пшеничная крупа","Перловка","Мюсли","Овсянка","Рис","Манная каша",

                    "Рисовая лапша","Яичная лапша","Пшеничная лапша","Кукурузная лапша",

                    "Маргарин","Кокосовое масло","Подсолнечное масло","Оливковое масло","Льяное масло",

                    "Йогурт","Сливочное масло","Кефир","Молоко коровье","Ряженка","Сгущённое молоко","Сметана","Творог",

                    "Креветки","Кальмары","Мидии","Устрицы","Осьминог","Рак","Краб","Лобстер","Икра","Морские водоросли",

                    "Ветчина","Колбаса вареная","Докторская колбаса","Копчёная колбаса","Молочные сосиски","Салями",

                    "Свинина","Говядина","Курицы","Индейка","Баранина","Конина","Бекон",

                    "Картофель","Редиска","Васаби","Морковь","Помидор","Капуста","Огурец","Свекла","Чеснок","Лук","Баклажан","Перец","Кабачок","Тыква","Броколли","Горох",

                    "Грецкий орех","Кешью","Миндаль","Фисташки","Фундук",

                    "Кета","Камбала","Сельдь","Скумбрия","Щука","Тунец","Треска","Форель","Минтай","Горбуша","Карп",

                    "Сыр","Сыр бри","Моцарелла","Филадельфия","Сыр с плесенью","Камамбер",

                    "Абрикос","Апельсин","Арбуз","Бананы","Гранат","Грейпфрут","Груша","Дыня","Лимон","Мандарины","Персик","Слива","Хурма","Яблоки",

                    "Хлеб","Булочка","Лаваш","Пончик","Хлебцы",

                    "Авокадо","Ананас","Дуриан","Личи","Помело","Карамбола","Манго","Личи",

                    "Вишня","Виноград","Голубика","Малина","Клубника","Ежевика","Крыжовник",

                    "Куриное яйцо","Перепелиное яйцо","Яйцо индейки"};
            arrayimage=new int[]{
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
                    R.drawable.g144,R.drawable.g145,R.drawable.g146,


            };
            days=new int[]{10,20};


        gcal = new GregorianCalendar();
        View view = inflater.inflate(R.layout.milkf, container, false);
        view.setBackgroundColor(Color.parseColor("#3f8678"));
        final ListView listView = view.findViewById(R.id.list);
       frag2 = new CreateProduct();
        frag1 = new Home();



        mSettings= getActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        if(mSettings.contains(SIZE)) {
            size = mSettings.getInt(SIZE, 0);
        }
        else{
            size=30;
        }

        fab = view.findViewById(R.id.fab);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog("Создать свой продукт","Вы действительно хотите добавить продукт вручную?");
            }
        });
        fillData();
        dbHelper=new ControlSQL(getContext());

        adapter = new ObjectAdapter(getContext(), productss,size);
        listView.setAdapter(adapter);



        mInputSearch = view.findViewById(R.id.text2);
        mInputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                productss.clear();
                sort.clear();
                search=  mInputSearch.getText().toString().toLowerCase().trim();
                if(search!="") {
                    for (int i = len1; i < len2+1; i++) {
                        if (arrayname[i].toLowerCase().contains(search)) {
                            sort.add(new ItemProduct(arrayname[i], arrayimage[i], 5,0,""));
                        }
                    }
                }
                adapter = new ObjectAdapter(getContext(),sort,size);
                listView.setAdapter(adapter);
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
        ////////////////////////////////////////////////////////////////////////
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = productss.get(position).getName();
                day=productss.get(position).getDays();
                imagee= Arrays.asList(arrayname).indexOf(selectedItem);
                Dialog2(day);

            }
        });
        return view;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void fillData() {


        for (int i = len1; i <len2+1 ; i++) {
            productss.add(new ItemProduct(arrayname[i], arrayimage[i],days[1],0,""));
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////
    public static MilkFragment newInstance() {
        MilkFragment fragment = new MilkFragment(0);
        return fragment;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void Dialog(String title, String content) {
        fab.hide();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                            fab.show();
                    }
                });
        builder.setPositiveButton("OK",

                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        fTrans = getFragmentManager().beginTransaction();
                        fTrans.replace(R.id.fragments, frag2);
                        fTrans.commit();

                    }
                });
        builder.show();
    }
    private void Dialog2(final int g1) {
        currentDate = new Date();
        dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        dateText = dateFormat.format(currentDate);
        fab.hide();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Добавить продукт");
        view1 = (LinearLayout) getLayoutInflater().inflate(R.layout.change, null);
        builder.setView(view1);
        final TextView tvTime =view1.findViewById(R.id.textdata);
        final EditText textday = view1.findViewById(R.id.textdays);
        final TextView dayormonth=view1.findViewById(R.id.dayormonth);
        dayormonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog3(dayormonth);
            }
        });
        tvTime.setText("дата изготовления: "+dateText);
        textday.setText(""+day);
        c1 = Calendar.getInstance();
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                day1 = cldr.get(Calendar.DAY_OF_MONTH);
                month = cldr.get(Calendar.MONTH);
                year = cldr.get(Calendar.YEAR);
                days1=0;

                picker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if ((gcal.get(Calendar.YEAR) < year) || (gcal.get(Calendar.MONTH) < monthOfYear && gcal.get(Calendar.YEAR) == year) || (gcal.get(Calendar.MONTH) == monthOfYear && gcal.get(Calendar.YEAR) == year && gcal.get(Calendar.DAY_OF_MONTH) <=dayOfMonth)) {
                            Toast.makeText(getContext(), "Невозможно установить дату", LENGTH_SHORT).show();
                        } else {
                                tvTime.setText("дата изготовления" + dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                                c1 = Calendar.getInstance();
                                c1.set(Calendar.MONTH, monthOfYear);
                                c1.set(Calendar.DATE, dayOfMonth);
                                c1.set(Calendar.YEAR, year);
                                dateOne = c1.getTime();
                                c2 = Calendar.getInstance();
                                c2.set(Calendar.MONTH, gcal.get(Calendar.MONTH));
                                c2.set(Calendar.DATE, gcal.get(Calendar.DAY_OF_MONTH));
                                c2.set(Calendar.YEAR, gcal.get(Calendar.YEAR));
                                Date two = c2.getTime();

                            days1 = Days.daysBetween(new DateTime(dateOne), new DateTime(two)).getDays();
                        }
                    }
                }, year, month, day1);
                picker.show();
            }
        });
        builder.setNegativeButton("Отмена",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        fab.show();
                    }
                });
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        if (textday.getText().length() == 0){
                            Toast.makeText(getContext(),"неправильный ввод", Toast.LENGTH_LONG).show();
                        }
                        else{

                            day1=g1;
                            if(!textday.getText().toString().isEmpty()) {
                                day1 = (Integer.parseInt(textday.getText().toString())) - days1;
                            }

                        if (day1<=0) {
                            Toast.makeText(getContext(), "Невозможно установить дату", LENGTH_SHORT).show();
                        } else {
                                c1.add(Calendar.DATE, day1);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                Cursor c = db.query("mytable", null, null, null, null, null, null);
                                ContentValues cv = new ContentValues();
                                cv.put("email", day1*umnoj);
                                cv.put("name", selectedItem);
                                cv.put("image", imagee);
                                cv.put("day", c1.get(Calendar.DATE)+"");
                                cv.put("month", c1.get(Calendar.MONTH)+"");
                                cv.put("year", c1.get(Calendar.YEAR)+"");
                                db.insert("mytable", null, cv);
                            if (c != null && c.moveToFirst()) {
                                do{
                                   id=Integer.valueOf(c.getString(c.getColumnIndexOrThrow("id")));
                                }while (c.moveToNext());
                            }
                                dbHelper.close();

                            Intent intent = new Intent(getContext(), AlarmBroadcast.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                    getContext(), id, intent, 0);
                            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ (day1 * 1000), pendingIntent);
                                fTrans = getFragmentManager().beginTransaction();
                                fTrans.replace(R.id.fragments, frag1);
                                fTrans.commit();
                            }
                        }
                    }
                });
        builder.show();
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