package com.misiontic.habit_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TodayHabitsActivity extends AppCompatActivity {

    private static ArrayList<Habits> todayHabitsList;
    private static ListView listView2;
    private MySQLiteHelper connectionBD;
    private static ArrayAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_habits);

        listView2=findViewById(R.id.listView2);

        //Obtener con serializable
        /*
        todayHabitsList=(ArrayList<Habits>)getIntent().getSerializableExtra("today",0);
        */
        int idHabitToday= getIntent().getIntExtra("checkedHabitId",0);

        if(savedInstanceState == null || !savedInstanceState.containsKey("key")) {
            // Obtener con bd

            connectionBD = new MySQLiteHelper(this);
            String sentence = "SELECT * FROM habits WHERE _id = ?";
            String[] params = new String[]{String.valueOf(idHabitToday)};
            Cursor results = connectionBD.getData(sentence, params);

            results.moveToFirst();
            int id = results.getInt(0);
            int nameId = results.getColumnIndex("name");
            String nameHabit = results.getString(nameId);
            String descriptionHabit = results.getString(2);
            String categoryHabit = results.getString(3);

            Habits newHabit = new Habits(nameHabit, descriptionHabit, categoryHabit);

            todayHabitsList=new ArrayList<>();
            //newHabit.setId(id);
            todayHabitsList.add(newHabit);
        }  else {
            todayHabitsList = savedInstanceState.getParcelableArrayList("key");
        }
        adapter2 = new ArrayAdapter<Habits>(this, android.R.layout.simple_list_item_1, todayHabitsList);
        //adapter2.notifyDataSetChanged();
        listView2.setAdapter(adapter2);


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("key",todayHabitsList);
        super.onSaveInstanceState(outState);
    }
}