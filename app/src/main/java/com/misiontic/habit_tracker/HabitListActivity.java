package com.misiontic.habit_tracker;

import static android.media.CamcorderProfile.get;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.misiontic.habit_tracker.db.MySQLiteHelper;
import com.misiontic.habit_tracker.listviews.HabitListViewAdapter;
import com.misiontic.habit_tracker.model.Habits;

import java.util.ArrayList;
import java.util.List;

//import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HabitListActivity extends AppCompatActivity {

    private static ArrayList<Habits> habitList;
    private static ListView listView;
    private static HabitListViewAdapter adapter;
    //private FloatingActionButton fabCreate;
    private FloatingActionButton fabCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_list);

        listView = findViewById(R.id.habitsList);
        habitList = new ArrayList<>();

        fabCreate=findViewById(R.id.fabCreate);
        fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HabitListActivity.this, NewActivity.class);
                startActivity(intent);
            }
        });

        //Cursor ...a... listview
        Cursor results=getHabitsBd();
        habitList=getHabitList(results);
        listView=adapterHabits(habitList);
        results.close();

        //Al tocar
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Habits selectedHabit=(Habits)listView.getItemAtPosition(position);
                //Mostrar en un DialogFragment
                dialog(selectedHabit);
            }
        });

        //Al chequear
        final ArrayList<Habits> todayHabits = new ArrayList<Habits>();
        for (int i = 0; i < habitList.size(); i++) {
            if(habitList.get(i).isSelected()){
                habitList.remove(get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }

    public Cursor getHabitsBd(){
        MySQLiteHelper connectionBD = new MySQLiteHelper(this);
        String selectQuery = "SELECT * FROM habits";
        Cursor results = connectionBD.getData(selectQuery, null);
        return results;
    }

    public ArrayList<Habits> getHabitList(Cursor results) {
        results.moveToFirst();
        do {
            int id = results.getInt(0);
            int nameId = results.getColumnIndex("name");
            String nameHabit = results.getString(nameId);
            String descriptionHabit = results.getString(2);
            String categoryHabit = results.getString(3);

            Habits newHabit=new Habits(nameHabit,descriptionHabit,categoryHabit);
            newHabit.setId(id);

            habitList.add(newHabit);

        } while (results.moveToNext());
        return habitList;
    }

    public ListView adapterHabits(ArrayList habitList) {

        try {
            adapter = new HabitListViewAdapter(this, habitList);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(this, R.string.failure_on_get, Toast.LENGTH_LONG).show();
        }
        return listView;

    }

    public void dialog(Habits selectedHabit) {
        AlertDialog.Builder dialog1 = new AlertDialog.Builder(HabitListActivity.this);
        dialog1.setTitle("Descripción del hábito " + selectedHabit.getName() + ": ");
        dialog1.setMessage(selectedHabit.getDescription());
        dialog1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog1.create();
        dialog1.show();
    }


}