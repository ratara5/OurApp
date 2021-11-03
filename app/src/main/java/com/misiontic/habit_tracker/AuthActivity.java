package com.misiontic.habit_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnRegistrar,btnIngresar;

    private static final String TAG="EmailPassword";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        etEmail=(EditText)findViewById(R.id.emailEditText);
        etPassword=(EditText)findViewById(R.id.passwordEditText);

        btnRegistrar=(Button)findViewById(R.id.signupButton);
        btnIngresar=(Button)findViewById(R.id.loginButton);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            recargar();
        }

        String correo=etEmail.getText().toString();
        String contrasena=etPassword.getText().toString();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar(correo,contrasena);
            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresar(correo,contrasena);
            }
        });
    }

    public void registrar(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "createdUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    actualizarUI(user);
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(AuthActivity.this, "Autenticación falló.", Toast.LENGTH_SHORT).show();
                    actualizarUI(null);
                }
            }
        });
    }


    public void ingresar(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    actualizarUI(user);
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(AuthActivity.this, "Ingreso falló.",Toast.LENGTH_SHORT).show();
                    actualizarUI(null);
                }
            }
        });
    }

    public void recargar(){

    }

    public void actualizarUI(FirebaseUser user){
        Intent sig=new Intent(this,HomeActivity.class);
        sig.putExtra("llave",user);
        startActivity(sig);
    }
}