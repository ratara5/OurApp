package com.misiontic.habit_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private TextView tvEmail,tvProveedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvEmail=(TextView)findViewById(R.id.emailTextView);
        tvProveedor=(TextView)findViewById(R.id.providerTextView);

        String llave=getIntent().getStringExtra("llave");
        tvEmail.setText(llave); //user[0] o user.email?
        tvProveedor.setText(llave); //user[1] o user.provider?

    }

    public void exit(View view) { //No existe esta referencia en OnClic
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}