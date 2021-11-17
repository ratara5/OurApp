package com.misiontic.habit_tracker;

import androidx.appcompat.app.AppCompatActivity;

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
