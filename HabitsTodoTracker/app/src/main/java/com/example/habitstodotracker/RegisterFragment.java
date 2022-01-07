package com.example.habitstodotracker;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends BottomSheetDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public RegisterFragment(onClick click) {
        this.click = click;
    }

    TextInputEditText txtRegisterNama;
    TextInputEditText txtRegisterEmail;
    TextInputEditText txtRegisterPassword;
    Button btnRegister;
    TextView btnToLogin;
    private onClick click;
    private FirebaseAuth mAuth;


    public onClick getClick() {
        return click;
    }

    public void setClick(onClick click) {
        this.click = click;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        click = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof onClick){
            click = (onClick) context;
        }else{
            throw new RuntimeException(context.toString() + "harus implement OnButtonClickedListener");
        }
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtRegisterNama = (TextInputEditText) view.findViewById(R.id.txtRegisterNama);
        txtRegisterEmail = (TextInputEditText) view.findViewById(R.id.txtRegisterEmail);
        txtRegisterPassword = (TextInputEditText) view.findViewById(R.id.txtRegisterPassword);
        btnRegister = (Button) view.findViewById(R.id.btnRegister);
        btnToLogin = (TextView) view.findViewById(R.id.btnToLogin);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.Click();
            }
        });
    }
    public interface onClick{
        void Click();

    }

    public void registerUser() {
        String username = txtRegisterNama.getText().toString().trim();
        String email = txtRegisterEmail.getText().toString().trim();
        String password = txtRegisterPassword.getText().toString().trim();

        if (username.isEmpty()) {
            txtRegisterNama.setError("Name is required!");
            txtRegisterNama.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            txtRegisterEmail.setError("email is required!");
            txtRegisterEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtRegisterEmail.setError("please provide a valid email!");
            txtRegisterEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            txtRegisterPassword.setError("password is required!");
            txtRegisterPassword.requestFocus();
            return;
        }
        if (password.length() <= 6) {
            txtRegisterPassword.setError("password must be equal or greater than 6 characters!");
            txtRegisterPassword.requestFocus();
            return;
        }
        Utils.showProgress(getContext());
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    User user = new User(username, email);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "berhasil register", Toast.LENGTH_SHORT).show();

                                Utils.cancelProgress();
                                txtRegisterEmail.setText("");
                                txtRegisterNama.setText("");
                                txtRegisterPassword.setText("");
                            } else {
                                Toast.makeText(getContext(), "gagal register, coba lagi", Toast.LENGTH_SHORT).show();
                                Utils.cancelProgress();

                            }
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "gagal register", Toast.LENGTH_SHORT).show();
                    Utils.cancelProgress();

                }
            }
        });
    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bottom_sheet_register, container, false);
    }
}