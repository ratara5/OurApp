package com.misiontic.habit_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.misiontic.habit_tracker.db.MySQLiteHelper;
import com.misiontic.habit_tracker.listviews.HabitListViewAdapter;
import com.misiontic.habit_tracker.model.DatePickerFragment;
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

    //Calendar
    private EditText etDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_habits);

        listView2 = findViewById(R.id.listView2);
        todayHabitsList = new ArrayList<>();

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
            String time = results.getString(2);
            String name_habit = results.getString(3);

            todayHabitsList.add(String.valueOf(id) + " - " + date + " - "+ time + " - " + String.valueOf(name_habit));

        } while (results.moveToNext());
        results.close();

        adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, todayHabitsList);
        //adapter2.notifyDataSetChanged();
        listView2.setAdapter(adapter2);

        //Calendar
        etDate = findViewById(R.id.etDate);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
                //break;
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                String dy;
                if(day<10){
                     dy="0"+String.valueOf(day);
                }else{
                     dy=String.valueOf(day);
                }
                String selectedDate = dy + "-" + (month+1) + "-" + year;
                etDate.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}