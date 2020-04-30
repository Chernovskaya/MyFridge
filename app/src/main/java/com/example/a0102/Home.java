package com.example.a0102;
import android.app.AlarmManager;
import android.app.AlertDialog;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;



import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static android.widget.Toast.LENGTH_SHORT;
import static androidx.core.app.NotificationCompat.PRIORITY_HIGH;

//YANA LOH
public class Home extends Fragment {

    FrameLayout layout;

    //настройки
    public static final String PREFERENCES = "mysettings";
    public static final String SIZE = "size";
    SharedPreferences mSettings;
    int size;

    //дата
    Date currentDate = new Date();
    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    String dateText = dateFormat.format(currentDate);

    //БД
    ControlSQL dbHelper;
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
            R.drawable.g144,R.drawable.g145,R.drawable.g146,
    };
    ArrayList<ItemProduct> spisok = new ArrayList<ItemProduct>();
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home, container, false);
        layout=view.findViewById(R.id.fragments);
        frameLayout=view.findViewById(R.id.fragmentsss);


        //содзание для БД
        dbHelper = new ControlSQL(getContext());



        //настройка
        mSettings= getActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        if(mSettings.contains(SIZE)) {
            size = mSettings.getInt(SIZE, 0);
        }
        else{
            size=30;
        }

        //прогрманное создание даты
        TextView main = view.findViewById(R.id.data);
        main.setTextSize(TypedValue.COMPLEX_UNIT_DIP,size);
        main.setText(dateText+" ");

        //всякие списки адпаптеры
        fillData(view);
        listView = view.findViewById(R.id.listView2);
        adapter = new HomeAdapter(getContext(), spisok,size);
        listView.setAdapter(adapter);

        //обработка нажатия на элемент списка
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               int opaa = spisok.get(position).getId();
               Dialog(opaa,view);
               return true;
            }
        });

        //проверка на пустоту
        if (spisok.isEmpty()){
            Display display =getActivity().getWindowManager().getDefaultDisplay();
            final int width = display.getWidth();
            n=1;
            textView=new TextView(getContext());
            textView.setTextSize(width/6);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            textView.setText("Пусто");
            textView.setGravity(Gravity.CENTER);
            frameLayout.addView(textView);
        }
        else{
            if (n==1) {
                frameLayout.removeView(textView);
                n=0;
            }
        }
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
                String dayc=c.getString(c.getColumnIndexOrThrow("day"));
                String monthc=c.getString(c.getColumnIndexOrThrow("month"));
                String yearc=c.getString(c.getColumnIndexOrThrow("year"));
                name = c.getString(c.getColumnIndexOrThrow("name"));
                day = Integer.valueOf(c.getString(c.getColumnIndexOrThrow("email")));
                int id=Integer.valueOf(c.getString(c.getColumnIndexOrThrow("id")));
               image = c.getString(c.getColumnIndexOrThrow("image"));

                int one_d=Integer.parseInt(dayc);
                int two_d=Integer.parseInt(cal.get(Calendar.DATE)+"");

                int one_m=Integer.parseInt(monthc);
                int two_m=Integer.parseInt(cal.get(Calendar.MONTH)+"");

                int one_y=Integer.parseInt(yearc);
                int two_y=Integer.parseInt(cal.get(Calendar.YEAR)+"");

                 if ((one_y==two_y && ( (one_m==two_m && one_d>two_d) || (one_m>two_m)))  || (one_y>two_y)    ){
                     if(image.contains("/")){
                         spisok.add(new ItemProduct(name,666, day,id,image));
                     }
                     else{
                         image1=Integer.parseInt(image);
                         spisok.add(new ItemProduct(name, arrayimage[image1], day,id,""));
                     }
                     }
                 else{
                     creating(view,image1,id,image);
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
        builder.setTitle("Удалить продукт");
        builder.setMessage("Вы действительно хотите удалить продукт?");
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
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete("mytable", "id = "+String.valueOf(opa),null);
                        dbHelper.close();

                        Intent myIntent = new Intent(getContext(),
                                AlarmBroadcast.class);

                        PendingIntent pendingIntent
                                = PendingIntent.getBroadcast(getContext(),
                                opa, myIntent, 0);

                        AlarmManager alarmManager
                                = (AlarmManager)getActivity().getSystemService(ALARM_SERVICE);

                        alarmManager.cancel(pendingIntent);


                        fillData(view3);
                        adapter.notifyDataSetChanged();
                    }
                });
        builder.show();

    }

//программное создание layout просрочки
void creating(final View view1, int day, final int id, String foruri){

    //корневая среда
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    params.width=120;
    params.height=101;

    //объект
    LinearLayout mRootFrameLayout =view1.findViewById(R.id.kuku);
    LinearLayout linearLayout = new LinearLayout(getContext());
    linearLayout.setOrientation(LinearLayout.VERTICAL);

    //image
    final ImageView pic = new ImageView(getContext());
    if(foruri.contains("/")){
        try {
            final InputStream imageStream = getContext().getContentResolver().openInputStream(Uri.parse(foruri));
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            pic.setImageBitmap(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    else{
        pic.setImageResource(arrayimage[day]);
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
}





