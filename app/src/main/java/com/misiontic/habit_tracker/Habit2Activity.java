package com.misiontic.habit_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.misiontic.habit_tracker.db.MySQLiteHelper;

public class Habit2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit2);
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
                Toast.makeText(this, id+" - "+nameHabit+" - "+descriptionHabit+" - "+categoryHabit, Toast.LENGTH_LONG).show();
            }while(results.moveToNext());
        }catch(Exception e){
            Toast.makeText(this, "@string/failure_on_get",Toast.LENGTH_LONG).show();
        }finally {
            results.close();
        }






    }
}