package com.misiontic.habit_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class ListActivity extends AppCompatActivity {

    EditText txtHabit1, txtHabit2;
    CheckBox cbWater, cbRead, cbWakeup, cbPush;
    CheckBox cbLanguage;
    CheckBox cbDairy;
    CheckBox cbFloss;
    Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        txtHabit1 = (EditText) findViewById(R.id.txtaddhabit1);
        txtHabit2 = (EditText) findViewById(R.id.txtaddhabit2;
        cbWater = (CheckBox) findViewById(R.id.CBhabito1Agua);
        cbRead = (CheckBox) findViewById(R.id.CBhabito2Libro);
        cbWakeup =(CheckBox) findViewById(R.id.CBhabito3despertarTemprano);
        cbPush = (CheckBox) findViewById(R.id.CBhabito4Flexiones);
        cbLanguage = (CheckBox)  findViewById(R.id.CBhabito5Idioma);
        cbDairy = (CheckBox) findViewById(R.id.CBhabito6Diario);
        cbFloss = (CheckBox) findViewById(R.id.CBhabito7);




    }


    public void HabitPreferences(View view){

        CheckBox cbWater = findViewById(R.id.CBhabito1Agua);


    }

   // extendedFab.setOnClickListener {
        // Respond to Extended FAB click
    //}
}