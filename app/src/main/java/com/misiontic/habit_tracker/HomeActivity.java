package com.misiontic.habit_tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private TextView tvUserName;
    private Toolbar mToolbar;
    private View layoutUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final DrawerLayout drawerlayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        mToolbar = findViewById(R.id.topAppBar);

        setSupportActionBar(mToolbar);

        //toolbar.setTitle("Title"); Para navegación entre interfaces
        //mToolbar.getNavigationIcon(); Para navegación entre interfaces
        //mToolbar.setNavigationIcon(R.drawable.ic_menu); Para navegación entre interfaces


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerlayout.openDrawer(GravityCompat.START);
            }
        });


        layoutUserName = navigationView.getHeaderView(0);
        tvUserName = (TextView) layoutUserName.findViewById(R.id.userTextView);

        String email = getIntent().getStringExtra("email");
        String provider = getIntent().getStringExtra("provider");


        tvUserName.setText(email);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Intent sig = new Intent(this, NewActivity.class);
                startActivity(sig);
                return true;

            case R.id.search:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            case R.id.logout:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public void exit(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}