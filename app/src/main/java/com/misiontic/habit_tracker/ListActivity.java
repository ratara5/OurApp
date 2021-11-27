package com.misiontic.habit_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class ListActivity extends AppCompatActivity {

    EditText txtHabit8, txtHabit9;
    CheckBox cbWater, cbRead, cbWakeup, cbPush;
    CheckBox cbLanguage;
    CheckBox cbDairy;
    CheckBox cbFloss;
    Button btn1Save;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);



        txtHabit8 = (EditText) findViewById(R.id.txtAddHabit8);
        txtHabit9 = (EditText) findViewById(R.id.txtAddHabit9);
        cbWater = (CheckBox) findViewById(R.id.cbHabit1Water);
        cbRead = (CheckBox) findViewById(R.id.cbHabit2Read);
        cbWakeup =(CheckBox) findViewById(R.id.cbHabit3Wake);
        cbPush = (CheckBox) findViewById(R.id.cbHabit4Push);
        cbLanguage = (CheckBox)  findViewById(R.id.cbHabit5Language);
        cbDairy = (CheckBox) findViewById(R.id.cbHabit6Dairy);
        cbFloss = (CheckBox) findViewById(R.id.cbHabit7Floss);
        btn1Save = (Button) findViewById(R.id.btnSave);


               btn1Save.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                   }
               });



        }

        public void SaveDate(View view){

        }

    }






   // extendedFab.setOnClickListener {
        // Respond to Extended FAB click
    // }
