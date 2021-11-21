package com.misiontic.habit_tracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Fragment;
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

    static TextView tvUserName;
    //private Toolbar mToolbar;
    private View layoutUserName;

    //private NavController navController;
    //private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final DrawerLayout drawerlayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);

        //mToolbar = findViewById(R.id.topAppBar);
        //setSupportActionBar(mToolbar);

        //toolbar.setTitle("Title"); Para navegación entre interfaces
        //mToolbar.getNavigationIcon(); Para navegación entre interfaces
        //mToolbar.setNavigationIcon(R.drawable.ic_menu); Para navegación entre interfaces


        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                drawerlayout.openDrawer(GravityCompat.START);
            }
        });


        layoutUserName = navigationView.getHeaderView(0);
        tvUserName = (TextView) layoutUserName.findViewById(R.id.userTextView);

        String email = getIntent().getStringExtra("email");

        tvUserName.setText(email);

        //HomeFragment homeFragment=new HomeFragment();
        //FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, homeFragment);
        //fragmentTransaction.commit();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(navigationView, navController);

        //mostrar título de cada fragment en la barra
        final TextView textTitle=findViewById(R.id.textTitle);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                textTitle.setText(destination.getLabel());
            }
        });

        //pasar e-mail a ProfileFragment
        Bundle args = new Bundle();
        args.putString("emailFromActivityHome", email);
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(args);

    }



    public void exit(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}