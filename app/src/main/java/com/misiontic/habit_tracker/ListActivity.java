package com.misiontic.habit_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;


public class ListActivity extends AppCompatActivity {

    EditText txtHabit1, txtHabit2;
    CheckBox cbWater, cbRead, cbWakeup, cbPush;
    CheckBox cbLanguage;
    CheckBox cbDairy;
    CheckBox cbFloss;
    Button btnSave;

    FirebaseFirestore HabitsDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        HabitsDb = FirebaseFirestore.getInstance();

        txtHabit1 = (EditText) findViewById(R.id.txtaddhabit1);
        txtHabit2 = (EditText) findViewById(R.id.txtaddhabit2;
        cbWater = (CheckBox) findViewById(R.id.CBhabito1Agua);
        cbRead = (CheckBox) findViewById(R.id.CBhabito2Libro);
        cbWakeup =(CheckBox) findViewById(R.id.CBhabito3despertarTemprano);
        cbPush = (CheckBox) findViewById(R.id.CBhabito4Flexiones);
        cbLanguage = (CheckBox)  findViewById(R.id.CBhabito5Idioma);
        cbDairy = (CheckBox) findViewById(R.id.CBhabito6Diario);
        cbFloss = (CheckBox) findViewById(R.id.CBhabito7);

        btnSave.setOnClickListener(new View.OnClickListener() {






        }








    }



   // extendedFab.setOnClickListener {
        // Respond to Extended FAB click
    //}
}