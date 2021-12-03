package com.misiontic.habit_tracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.misiontic.habit_tracker.db.MySQLiteHelper;

import java.util.HashMap;
import java.util.Map;

public class NewActivity extends AppCompatActivity {

    private EditText etHabitName, etHabitDescription, etHabitCategory;

    private FirebaseFirestore dbF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        etHabitName=findViewById(R.id.editTextHabitName);
        etHabitDescription=findViewById(R.id.editTextHabitDescription);
        etHabitCategory=findViewById(R.id.editTextHabitCategory);

        dbF = FirebaseFirestore.getInstance();

        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.my_ch_toolbar);
        setSupportActionBar(myChildToolbar);

        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void saveForm(View view){
        String habitName=etHabitName.getText().toString();
        String habitDescription=etHabitDescription.getText().toString();
        String habitCategory=etHabitCategory.getText().toString();

        if(!habitName.equals("") && !habitDescription.equals("") && !habitCategory.equals("")) {
            //Guardar en local
            MySQLiteHelper connectionBD = new MySQLiteHelper(this);
            String insertQuery = "INSERT INTO habits(name, description, category)" +
                    "VALUES('" + habitName + "','" + habitDescription + "','" + habitCategory + "')";
            boolean suc = connectionBD.insertData(insertQuery);
            if (suc) {
                Toast.makeText(this, this.getString(R.string.success_on_save) + " en local", Toast.LENGTH_LONG).show();
                cleanForm();
            } else {
                Toast.makeText(this, this.getString(R.string.failure_on_save) + " en local", Toast.LENGTH_LONG).show();
            }
            Intent intent = new Intent(NewActivity.this, HabitListActivity.class);
            startActivity(intent);

            //Guardar en CloudFirestore
            //Map<String, Object> habit = new HashMap<>();
            //habit.put("description", habitDescription);
            //habit.put("category", habitCategory);
            //db.collection("habits").document(habitName).set(habit);
        }else{
            Toast.makeText(NewActivity.this,R.string.all_fields, Toast.LENGTH_LONG).show();
        }

    }

    public void cleanForm(View view){
        etHabitName.setText("");
        etHabitCategory.setText("");
        etHabitDescription.setText("");
    }

    public void cleanForm(){ //aplicar Nullable?
        etHabitName.setText("");
        etHabitCategory.setText("");
        etHabitDescription.setText("");
    }
}