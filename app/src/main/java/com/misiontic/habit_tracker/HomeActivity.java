package com.misiontic.habit_tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private TextView tvEmail,tvProveedor;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar=findViewById(R.id.topAppBar);
        setSupportActionBar(mToolbar);

        tvEmail=(TextView)findViewById(R.id.emailTextView);
        tvProveedor=(TextView)findViewById(R.id.providerTextView);

        String llave=getIntent().getStringExtra("llave");
        tvEmail.setText(llave); //user[0] o user.email?
        tvProveedor.setText(llave); //user[1] o user.provider?

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }




    public void exit(View view) { //No existe esta referencia en OnClic
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}