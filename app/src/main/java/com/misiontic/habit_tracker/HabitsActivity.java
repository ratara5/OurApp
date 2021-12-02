package com.misiontic.habit_tracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class HabitsActivity extends AppCompatActivity {
    static int[] EASY = new int[]{6,5,5,4};
    static int[] MEDIO = new int[]{7,5,5,4};
    static int[] HARD = new int[]{9,5,5,4};
    private TextView textContenedorNumero;
    int pos = 0;
    private Button btnTouchAction;
    private CheckBox cbHabit4Push, checkBox2, checkBox3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits);
        cbHabit4Push =(CheckBox) findViewById(R.id.cbHabit4Push);
        checkBox2 =(CheckBox) findViewById(R.id.checkBox2);
        checkBox3 =(CheckBox) findViewById(R.id.checkBox3);

        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.my_child_toolbar);
        setSupportActionBar(myChildToolbar);

        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);

        encontrarComponentesPorId();
        cbHabit4Push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbHabit4Push.isChecked()){
                    checkBox3.setEnabled(false);
                    checkBox2.setEnabled(false);
                    textContenedorNumero.setText(""+EASY[0]);
                    ejecutarRutina(EASY);
                    pos=0;
                }else{
                    checkBox3.setEnabled(true);
                    checkBox2.setEnabled(true);
                }

                Log.i("ESTAD0", "onClick: "+ cbHabit4Push.isChecked());
            }
        });
        cbHabit4Push.setChecked(true);
        Toast.makeText(getApplicationContext(), "Checkeado",Toast.LENGTH_LONG);

    }


    private void encontrarComponentesPorId(){
        textContenedorNumero= findViewById(R.id.textCont);
        btnTouchAction = findViewById(R.id.btnAction);

    }
    public void ejecutarRutina(int[] rutina){

        btnTouchAction.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int intContenedorNumero = Integer.parseInt(textContenedorNumero.getText().toString());
                Log.d("parse", "" + intContenedorNumero);
                if(intContenedorNumero==0){
                    if(pos<3){
                        pos++;
                        intContenedorNumero=rutina[pos];
                        String stringNumeroFinal = String.valueOf(intContenedorNumero);
                        textContenedorNumero.setText(stringNumeroFinal);
                    }else{
                        Toast.makeText(getApplicationContext(),"Rutina terminada",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    intContenedorNumero--;
                    String stringNumeroFinal = String.valueOf(intContenedorNumero);
                    textContenedorNumero.setText(stringNumeroFinal);
                }
            }
        });
    }

}
