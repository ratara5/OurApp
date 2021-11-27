package com.misiontic.habit_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.misiontic.habit_tracker.R;
import com.misiontic.habit_tracker.db.MySQLiteHelper;
import com.misiontic.habit_tracker.listviews.HabitListViewAdapter;
import com.misiontic.habit_tracker.model.Habits;

import java.util.ArrayList;

public class HabitListActivity extends AppCompatActivity {

    private ArrayList<Habits> habitList;
    private static ListView listView;
    private static HabitListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_list);

        listView = findViewById(R.id.habitsList);
        habitList = new ArrayList<>();

        showHabits();
    }

    public void showHabits() {
        int id;
        String nameHabit, descriptionHabit, categoryHabit;

        MySQLiteHelper connectionBD = new MySQLiteHelper(this);
        String selectQuery = "SELECT * FROM habits";

        Cursor results = connectionBD.getData(selectQuery, null);

        try {
            results.moveToFirst();
            do {
                id = results.getInt(0);
                int nameId = results.getColumnIndex("name");
                nameHabit = results.getString(nameId);
                descriptionHabit = results.getString(2);
                categoryHabit = results.getString(3);

                Habits newHabit=new Habits(nameHabit,descriptionHabit,categoryHabit);
                newHabit.setId(id);
                //String s = id + " - " + nameHabit + " - " + descriptionHabit + " - " + categoryHabit;
                //Toast.makeText(this, s, Toast.LENGTH_LONG).show();

                //lista
                habitList.add(newHabit);
                //

            } while (results.moveToNext());
            adapter = new HabitListViewAdapter(this, habitList);
            listView.setAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(this, "@string/failure_on_get", Toast.LENGTH_LONG).show();

        } finally {
            results.close();
        }

    }
}