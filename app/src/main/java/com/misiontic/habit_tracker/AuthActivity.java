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
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class AuthActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;

    private static final String TAG = "EmailPassword";
    private static final String G_TAG = "Google";

    private FirebaseAuth mAuth;

    private int GOOGLE_SIGN_IN=100;
    int RC_SIGN_IN=1;


    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mCallbackManager; //FB

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

                    //FB


                    if (result.getResultCode() == RC_SIGN_IN) {
                        Intent data = result.getData();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        try {
                            // Google Sign In was successful, authenticate with Firebase
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            Log.d(G_TAG, "firebaseAuthWithGoogle:" + account.getId());
                            //cambio
                            //firebaseAuthWithGoogle(account.getIdToken())
                            //fin cambio
                        } catch (ApiException e) {
                            // Google Sign In failed, update UI appropriately
                            Log.w(G_TAG, "Google sign in failed", e);
                        }
                    }
                }
            }
    );






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        etEmail = (EditText) findViewById(R.id.emailEditText);
        etPassword = (EditText) findViewById(R.id.passwordEditText);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.def_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mCallbackManager=CallbackManager.Factory.create(); //FB

        //if(currentUser != null){
        //reload();
        //}

    }

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

    public void ingresarGoogle(View view) {
        loginGoogle();
    }

    public void ingresarFacebook(View view) {
        loginFacebook();
    }

    public void signup(String email, String password) {
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

    public void loginGoogle(){
        mGoogleSignInClient.signOut();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        someActivityResultLauncher.launch(signInIntent);
    }

    // FB:1
    public void loginFacebook(){
            LoginManager.getInstance().logInWithReadPermissions(AuthActivity.this, Arrays.asList("email"));
            LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });

    }

    // FB:0
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //FB:2
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            actualizarUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(AuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            actualizarUI(null);
                        }
                    }
                });
    }

    public void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
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
        Intent sig = new Intent(this, HomeActivity.class);
        sig.putExtra("email", user.getEmail().toString());
        sig.putExtra("provider", user.getProviderData().toString());
        startActivity(sig);
    }
}