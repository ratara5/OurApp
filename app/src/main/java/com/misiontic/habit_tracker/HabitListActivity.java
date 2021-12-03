package com.misiontic.habit_tracker;

import static android.media.CamcorderProfile.get;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.gson.Gson;

//import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HabitListActivity extends AppCompatActivity implements HabitListViewAdapter.CheckBoxCheckedListener{

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

        //Traer todayHabitListErase para eliminar los habitlist que ya se habían enviado a todayHabitActivity


        //Cursor ...a... listview
        Cursor results=getHabitsBd();
        habitList = getHabitList(results);
        listView = adapterHabits(habitList);
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
        /*
        for (int i = 0; i < habitList.size(); i++) {
            if(habitList.get(i).isSelected()){
                Toast.makeText(HabitListActivity.this, "Se remueve el elemento "+habitList.get(i).getName(), Toast.LENGTH_SHORT).show();
                habitList.remove(get(i));
            }
        }
        adapter.notifyDataSetChanged();
        */
        adapter.setCheckedlistener(this);
    }

    public Cursor getHabitsBd(){
        MySQLiteHelper connectionBD = new MySQLiteHelper(this);
        String selectQuery = "SELECT * FROM habits WHERE checked=?";
        String[] params = new String[]{String.valueOf("FALSE")};
        Cursor results = connectionBD.getData(selectQuery, params);
        //Cursor results = connectionBD.getData(selectQuery, null);
        return results;
    }

    public ArrayList<Habits> getHabitList(Cursor results) {
        try {
            results.moveToFirst();
            do {
                int id = results.getInt(0);
                int nameId = results.getColumnIndex("name");
                String nameHabit = results.getString(nameId);
                String descriptionHabit = results.getString(2);
                String categoryHabit = results.getString(3);

                Habits newHabit = new Habits(nameHabit, descriptionHabit, categoryHabit);
                newHabit.setId(id);

                habitList.add(newHabit);

            } while (results.moveToNext());
        } catch (Exception e) {
            Toast.makeText(this, this.getString(R.string.failure_on_get), Toast.LENGTH_LONG).show();
        }
        return habitList;
    }

    public ListView adapterHabits(ArrayList<Habits> habitList) {
        adapter = new HabitListViewAdapter(this, habitList);
        listView.setAdapter(adapter);
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
        //se puede eliminar desde aquí?
    }


    @Override
    public void getCheckBoxCheckedListener(int position) {
        Habits checkedHabit=(Habits)listView.getItemAtPosition(position);
        //Toast.makeText(getApplicationContext(),"Has marcado "+checkedHabit.getName()+"?",Toast.LENGTH_LONG).show();
        habitList.remove(position);
        //adapter.notifyDataSetChanged();
        //Enviar normal
        int checkedHabitId=checkedHabit.getId();
        checkedHabit.setSelected(true);
        /*
        Intent intentToday=new Intent(HabitListActivity.this,TodayHabitsActivity.class);
        intentToday.putExtra("checkedHabitId",checkedHabitId);
        startActivity(intentToday);
        */
        //Enviar con serializable
        /*
        Intent intentToday=new Intent(HabitListActivity.this,TodayHabitsActivity.class);
        intentToday.putStringArrayListExtra("today", ArrayList<Habits>todayHabitList);
        */
        //Enviar con bundle
        /*
        Bundle value= new Bundle();
        value.putParcelableArrayList("temp", todayHabitList);
        */
        //Enviar con bd
        MySQLiteHelper connectionBD = new MySQLiteHelper(this);


        //String now = Calendar.getInstance().getTime().toString();

        /*
        Calendar now = Calendar.getInstance();
        String DATE_FORMAT_NOW = "yyyy-MM-dd";
        SimpleDateFormat today = new SimpleDateFormat(DATE_FORMAT_NOW);
        String.valueOf(today.format(now.getTime()));
         */

        updateHabitChecked(checkedHabit);

        String today = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        String insertQuery = "INSERT INTO dates(date, id_habit )" +
                "VALUES('" + today + "','" + checkedHabitId +"')";

        boolean suc = connectionBD.insertData(insertQuery);
        if (suc) {
            Toast.makeText(this, this.getString(R.string.success_on_save) + " en local", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, this.getString(R.string.failure_on_save) + " en local", Toast.LENGTH_LONG).show();
        }
        //adapter.clear();
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

    }

    public void updateHabitChecked(Habits checkedHabit) {
        String tabla = "habits";
        ContentValues cv = new ContentValues();
        cv.put("name", checkedHabit.getName().toString());
        cv.put("description", checkedHabit.getDescription().toString());
        cv.put("category", checkedHabit.getCategory().toString());
        cv.put("checked", "TRUE");
        String whereClause = "_id = ?";
        String[] params = new String[]{String.valueOf(checkedHabit.getId())};
        MySQLiteHelper connectionBD = new MySQLiteHelper(this);
        int rows = connectionBD.updateData(tabla, cv, whereClause, params);
        if (rows > 0) {
            Toast.makeText(this, "Chequeo de hábito actualizado", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HabitListActivity.this, TodayHabitsActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(this, "Chequeo de hábito no se pudo actualizar", Toast.LENGTH_SHORT).show();
        }
    }

}