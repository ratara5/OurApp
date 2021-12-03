package com.misiontic.habit_tracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.misiontic.habit_tracker.AuthActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    TextView tvEmail;
    EditText etNameFirst, etNameLast, etPhone, etAddress;
    String email;
    Button btnGet, btnSave, btnDelete;

    //private FirebaseAuth mAute;

    private FirebaseFirestore db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            //String email = getArguments().getString("emailFromActivityHome"); Trayendo argumentos no funcionó
        }

        email = HomeActivity.tvUserName.getText().toString();

        //También se pueden usar las siguientes 3 líneas para llenar campo e-mail
        //mAute = FirebaseAuth.getInstance();
        //FirebaseUser currenUser = mAute.getCurrentUser();
        //email=currenUser.getEmail().toString();
        //Pero, habría acceso a todos los datos también en este fragment (?)

        db = FirebaseFirestore.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);



        tvEmail= (TextView) view.findViewById(R.id.textViewEmail);

        etNameFirst= (EditText) view.findViewById(R.id.editTextNameFirst);
        etNameLast= (EditText) view.findViewById(R.id.editTextNameLast);
        etPhone= (EditText) view.findViewById(R.id.editTextPhone);
        etAddress= (EditText) view.findViewById(R.id.editTextAddress);

        btnGet=(Button)view.findViewById(R.id.getButton);
        btnSave=(Button)view.findViewById(R.id.saveButton);
        btnDelete=(Button)view.findViewById(R.id.deleteButton);

        tvEmail.setText(email);

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getContext();
                Map<String, Object> user = new HashMap<>();
                user.put("first", etNameFirst.getText().toString());
                user.put("last", etNameLast.getText().toString());
                user.put("phone", etPhone.getText().toString());
                user.put("address", etAddress.getText().toString());
                db.collection("users").document(email).set(user);
            }
        });

        btnGet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getContext();
                DocumentReference user = db.collection("users").document(email);
                user.get().addOnCompleteListener(new OnCompleteListener< DocumentSnapshot >() {
                    @Override
                    public void onComplete(@NonNull Task< DocumentSnapshot > task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            etNameFirst.setText(doc.get("first").toString());
                            etNameLast.setText(doc.get("last").toString());
                            etPhone.setText(doc.get("phone").toString());
                            etAddress.setText(doc.get("address").toString());
                        }
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getContext();
                db.collection("users").document(email).delete();
            }
        });

        return view;
    }
}