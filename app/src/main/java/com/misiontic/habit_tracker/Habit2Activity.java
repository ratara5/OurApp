package com.misiontic.habit_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.misiontic.habit_tracker.db.MySQLiteHelper;

import java.util.ArrayList;

public class Habit2Activity extends AppCompatActivity {

    private ArrayList<String> list_habits;
    private ListView lvHabits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit2);

        list_habits=new ArrayList<>();
        lvHabits=findViewById(R.id.lvHabits);

        showHabits();
    }

    public void showHabits(){
        int id;
        String nameHabit, descriptionHabit, categoryHabit;

        MySQLiteHelper connectionBD=new MySQLiteHelper(this);
        String selectQuery="SELECT * FROM habits";

        Cursor results=connectionBD.getData(selectQuery, null);

        try {
            results.moveToFirst();
            do{
                id=results.getInt(0);
                int nameId=results.getColumnIndex("name");
                nameHabit=results.getString(nameId);
                descriptionHabit=results.getString(2);
                categoryHabit=results.getString(3);
                String s=id+" - "+nameHabit+" - "+descriptionHabit+" - "+categoryHabit;
                //Toast.makeText(this, s, Toast.LENGTH_LONG).show();

                //lista
                list_habits.add(s);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list_habits);
                lvHabits.setAdapter(adapter);
                //

            }while(results.moveToNext());
        }catch(Exception e){
            Toast.makeText(this, "@string/failure_on_get",Toast.LENGTH_LONG).show();
        }finally {
            results.close();
        }






    }
}