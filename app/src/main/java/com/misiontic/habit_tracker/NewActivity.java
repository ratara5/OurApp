package com.misiontic.habit_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.misiontic.habit_tracker.db.MySQLiteHelper;

public class NewActivity extends AppCompatActivity {

    private EditText etHabitName, etHabitDescription, etHabitCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        etHabitName=findViewById(R.id.editTextHabitName);
        etHabitDescription=findViewById(R.id.editTextHabitDescription);
        etHabitCategory=findViewById(R.id.editTextHabitCategory);



    }

    public void saveForm(View view){
        String habitName=etHabitName.getText().toString();
        String habitDescription=etHabitDescription.getText().toString();
        String habitCategory=etHabitCategory.getText().toString();

        MySQLiteHelper connectionBD=new MySQLiteHelper(this);
        String insertQuery="INSERT INTO habits(name, description, category)" +
                "VALUES('"+habitName+"','"+habitDescription+"','"+habitCategory+"')";

        connectionBD.insertData(insertQuery);
    }
}