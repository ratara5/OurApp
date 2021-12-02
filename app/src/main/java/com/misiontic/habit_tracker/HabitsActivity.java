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


public class HabitsActivity extends AppCompatActivity {
    static int[] EASY = new int[]{6,5,5,4};
    static int[] MEDIO = new int[]{7,5,5,4};
    static int[] HARD = new int[]{9,5,5,4};
    private TextView textContenedorNumero;
    int pos = 0;
    private Button btnTouchAction;
    private CheckBox[] cbList;
    private int [] selected_runtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits);

        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.my_child_toolbar);
        setSupportActionBar(myChildToolbar);

        ActionBar ab = getSupportActionBar();
        encontrarComponentesPorId();

        ab.setDisplayHomeAsUpEnabled(true);

        cbList = new CheckBox[]{
                (CheckBox) findViewById(R.id.easyRuntimeCb),
                (CheckBox) findViewById(R.id.mediumRuntimeCb),
                (CheckBox) findViewById(R.id.hardRuntimeCb)
        };
        for (CheckBox cb:cbList) {
            cb.setOnCheckedChangeListener(cbListener);
        }
        Toast.makeText(getApplicationContext(), "Checkeado",Toast.LENGTH_LONG);

    }

    CompoundButton.OnCheckedChangeListener cbListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            checkEnoughAndMakeDisabled(cbList);
        }
    };

    private void checkEnoughAndMakeDisabled(CheckBox checkBoxes[]){
        int countChecked = 0;
        for (CheckBox cb:checkBoxes){
            cb.setEnabled(true);
            if (cb.isChecked()) {
                int selectedCb = cb.getId();
                String selectedName = getResources().getResourceEntryName(selectedCb);
                Log.d("selected cb", selectedName);
                callAction(selectedName);
                countChecked++;
            }
        }
        if (countChecked == 1) {
            for (CheckBox cb:checkBoxes){
                if (!cb.isChecked())cb.setEnabled(false);
            }
        }
    }

    private void callAction(String selectedCb) {
        switch(selectedCb) {
            case "easyRuntimeCb":
                selected_runtime = EASY;
                break;
            case "mediumRuntimeCb":
                selected_runtime = MEDIO;
                break;
            case "hardRuntimeCb":
                selected_runtime = HARD;
                break;
        }
        if(selected_runtime.length > 0){
            textContenedorNumero.setText(""+selected_runtime[0]);
            ejecutarRutina(selected_runtime);
            pos=0;
        }
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
