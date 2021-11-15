package com.misiontic.habit_tracker;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private ImageButton google;

    private static final String TAG = "EmailPassword";
    private static final String G_TAG = "Google";
    int RC_SIGN_IN=1;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        google = findViewById(R.id.googleImageButton);
        etEmail = (EditText) findViewById(R.id.emailEditText);
        etPassword = (EditText) findViewById(R.id.passwordEditText);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginGoogle(v);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //if(currentUser != null){
        //reload();
        //}

    }

    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
            actualizarUI(currentUser);
        }
        else {

        }
    }*/

    public void registrar(View view) {
        String correo = etEmail.getText().toString().trim();
        String contrasena = etPassword.getText().toString().trim();
        signup(correo, contrasena);
    }

    public void ingresar(View view) {
        String correo = etEmail.getText().toString().trim();
        String contrasena = etPassword.getText().toString().trim();
        login(correo, contrasena);
    }

    public void signup(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            actualizarUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(AuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            actualizarUI(null);
                        }

                        // ...
                    }
                });
    }

    public void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    actualizarUI(user);
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(AuthActivity.this, "Ingreso falló.", Toast.LENGTH_SHORT).show();
                    actualizarUI(null);
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately

                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    public void loginGoogle(View view){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(G_TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            actualizarUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(G_TAG, "signInWithCredential:failure", task.getException());
                            actualizarUI(null);
                        }
                    }
                });
    }

    public void reload() {

    }

    public void actualizarUI(FirebaseUser user) {
        if(user!=null){
            Intent sig = new Intent(this, HomeActivity.class);
            sig.putExtra("email", user.getEmail().toString());
            startActivity(sig);
        }
        Toast.makeText(this,"Error intente nuevamente", Toast.LENGTH_SHORT);
        Log.d("actualizarUI", "User null");
    }
}