package com.misiontic.habit_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity {

    ListView listViewData;
            ArrayAdapter<String>adapter;
            String [] arrayHabitos ={habito1, habito2, habito3, habito4, habito5, habito6,
            habito7,habito8, habito9, habito10};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listViewData = findViewById(R.id.listview_data);
        adapter = new ArrayAdapter<>( this,
                android.R.layout.simple_list_item_multiple_choice,arrayHabitos);
        listViewData.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;

        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_done){
            String itemSelected = "Selected items: \n";
            for (int i=0; i<listViewData.getCount();i++){
                if (listViewData.isItemChecked(i)){
                    itemSelected += listViewData.getItemAtPosition(i) + "\n";
                }
            }
            Toast.makeText(this,itemSelected,Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}