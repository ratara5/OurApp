package com.misiontic.habit_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.misiontic.habit_tracker.db.MySQLiteHelper;
import com.misiontic.habit_tracker.listviews.HabitListViewAdapter;
import com.misiontic.habit_tracker.model.Habits;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TodayHabitsActivity extends AppCompatActivity {

    //private static ArrayList<Habits> todayHabitsList;
    private static ArrayList<String> todayHabitsList;
    private static ListView listView2;
    private MySQLiteHelper connectionBD;
    private static ArrayAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_habits);

        listView2=findViewById(R.id.listView2);
        todayHabitsList=new ArrayList<>();

        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.mh_toolbar);
        setSupportActionBar(myChildToolbar);

        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Ir a Home");
        ab.setHomeAsUpIndicator(R.drawable.ic_back_white);

        //Obtener con serializable
        /*
        todayHabitsList=(ArrayList<Habits>)getIntent().getSerializableExtra("today",0);
        */
        /* Obtener normal
        int idHabitToday= getIntent().getIntExtra("checkedHabitId",0);
        */
        /* Obtener con savedInstance
        if(savedInstanceState == null || !savedInstanceState.containsKey("key")) {

        }}  else {
        todayHabitsList = savedInstanceState.getParcelableArrayList("key");
        }
        */
        // Obtener con bd

        MySQLiteHelper connectionBD = new MySQLiteHelper(this);
        String sentence = "SELECT * FROM dates WHERE date = ?";
        String today = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String[] params = new String[]{String.valueOf(today)};
        Cursor results = connectionBD.getData(sentence, params);

        results.moveToFirst();
        do {
            int id = results.getInt(0);
            int dateId = results.getColumnIndex("date");
            String date = results.getString(dateId);
            int id_habit = results.getInt(2);


            todayHabitsList.add(String.valueOf(id)+" - "+date+" - "+String.valueOf(id_habit));

        } while (results.moveToNext());
        results.close();

        adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, todayHabitsList);
        //adapter2.notifyDataSetChanged();
        listView2.setAdapter(adapter2);


    }

}