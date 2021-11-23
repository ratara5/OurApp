package com.misiontic.habit_tracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.misiontic.habit_tracker.AuthActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    EditText etEmail, etNameFirst, etNameLast, etPhone, etAddress;
    String email;

    //private FirebaseAuth mAute;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);



        etEmail= (EditText) view.findViewById(R.id.editTextEmail);
        etNameFirst= (EditText) view.findViewById(R.id.editTextNameFirst);
        etNameLast= (EditText) view.findViewById(R.id.editTextNameLast);
        etPhone= (EditText) view.findViewById(R.id.editTextPhone);
        etAddress= (EditText) view.findViewById(R.id.editTextAddress);

        etEmail.setText(email);

        return view;
    }
}