package com.misiontic.habit_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        //Animations
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.move_up);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.move_down);

        TextView byTextView = findViewById(R.id.tvBy);
        TextView habitColTextView = findViewById(R.id.tvHabitCol);
        ImageView logoImageView = findViewById(R.id.ivLogo);

        byTextView.setAnimation(animation1);
        habitColTextView.setAnimation(animation1);
        logoImageView.setAnimation(animation2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LaunchActivity.this, AuthActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}