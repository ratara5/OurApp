package com.misiontic.habit_tracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class HabitsActivity extends AppCompatActivity {

    private TextView textContenedorNumero;
    private Button btnTouchAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits);

        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.my_child_toolbar);
        setSupportActionBar(myChildToolbar);

        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);

        encontrarComponentesPorId();

        btnTouchAction.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int intContenedorNumero = Integer.parseInt(textContenedorNumero.getText().toString());
                Log.d("parse", "" + intContenedorNumero);
                intContenedorNumero++;

                String stringNumeroFinal = String.valueOf(intContenedorNumero);

                textContenedorNumero.setText(stringNumeroFinal);

            }
        });
    }

    private void encontrarComponentesPorId(){
         textContenedorNumero= findViewById(R.id.textCont);
        btnTouchAction = findViewById(R.id.btnAction);

    }

}
