package com.example.habitstodotracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends BottomSheetDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextInputEditText txtLoginEmail;
    private TextInputEditText txtLoginPassword;
    private Button btnLogin;
    private TextView btnToRegister;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    private RegisterFragment.onClick onClick;
    public LoginFragment() {
        // Required empty public constructor
    }
    public LoginFragment(RegisterFragment.onClick onClick) {
        // Required empty public constructor
        this.onClick = onClick;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        }
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onClick = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof RegisterFragment.onClick){
            onClick = (RegisterFragment.onClick) context;
        }else{
            throw new RuntimeException(context.toString() + "harus implement OnButtonClickedListener");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_sheet_login, container, false);

        txtLoginEmail = (TextInputEditText) view.findViewById(R.id.txtLoginEmail);
        txtLoginPassword = (TextInputEditText) view.findViewById(R.id.txtLoginPassword);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnToRegister = (TextView) view.findViewById(R.id.btnToRegister);

        btnToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.Click();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtLoginEmail.getText().toString();
                String password = txtLoginPassword.getText().toString();

                if(email.isEmpty()){
                    txtLoginEmail.setError("Name is required!");
                    txtLoginEmail.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    txtLoginPassword.setError("Name is required!");
                    txtLoginPassword.requestFocus();
                    return;
                }

                Utils.showProgress(getContext());
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Utils.cancelProgress();
                            SharedPreferences settingemail = getContext().getSharedPreferences("Login", 0);
                            settingemail.edit().putString("email", email).apply();
                            SharedPreferences settingpass = getContext().getSharedPreferences("Login", 0);
                            settingpass.edit().putString("password", password).apply();

                            Intent i = new Intent(getContext(), HomeActivity.class);
                            startActivity(i);

                        }else{
                            Utils.cancelProgress();
                            Toast.makeText(getContext(), "gagal login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}