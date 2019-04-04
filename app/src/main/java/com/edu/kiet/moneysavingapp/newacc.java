package com.edu.kiet.moneysavingapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

import java.util.regex.Pattern;

public class newacc extends Activity {

    private static final Pattern PASSWORD_PATTERN= Pattern.compile("^" +
            //"(?=.*[0-9])" +         //at least 1 digit
            //"(?=.*[a-z])" +         //at least 1 lower case letter
            //"(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            "(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{4,}" +               //at least 4 characters
            "$");
    private EditText UserName;
    private EditText emailedt;
    private EditText passedt;
    //private EditText repassedt;
    FirebaseAuth fireBaseAuth;
    private TextInputLayout textLayoutUserName;
    private TextInputLayout textLayoutEmail;
    private TextInputLayout textLayoutPass;
   // private TextInputLayout textLayoutRePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newacc);

        UserName = findViewById(R.id.UserNameedt);
        emailedt = findViewById(R.id.emailedt);
        passedt = findViewById(R.id.passedt);
        //repassedt = findViewById(R.id.repassedt);
        Button submitbtn = findViewById(R.id.submitbtn);

        fireBaseAuth = FirebaseAuth.getInstance();

        textLayoutUserName = findViewById(R.id.textLayoutUserName);
        textLayoutEmail = findViewById(R.id.textLayoutEmail);
        textLayoutPass = findViewById(R.id.textLayoutPass);
        //textLayoutRePass = findViewById(R.id.textLayoutRePass);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registeruser();
            }
        });
    }


    private boolean validateEmail() {
        String emailInput = textLayoutEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            textLayoutEmail.setError("Field can't be Empty");
            return false;

        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textLayoutEmail.setError("Please enter a valid email address");
            return false;
        } else {
            textLayoutEmail.setError(null);
            return true;
        }

    }

    private boolean validatePassword() {
        String passInput = textLayoutPass.getEditText().getText().toString().trim();
        if (passInput.isEmpty()) {
            textLayoutPass.setError("Field can't be Empty");
            return false;
        } else {
            textLayoutPass.setError(null);
            return true;
        }

    }

    private boolean validateUsername() {
        String UsernameInput = textLayoutUserName.getEditText().getText().toString().trim();
        if (UsernameInput.isEmpty()) {
            textLayoutUserName.setError("Field can't be Empty");
            return false;

        } else if (UsernameInput.length()>15) {
            textLayoutUserName.setError("Username too long");
            return false;
        } else {
            textLayoutUserName.setError(null);
            return true;
        }

    }


    private void registeruser() {
        String email = emailedt.getText().toString().trim();
        String password = passedt.getText().toString().trim();
        String username = UserName.getText().toString().trim();
        //String repassword = repassedt.getText().toString().trim();

        if(!validateUsername() | !validateEmail() | !validatePassword()  );

//        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(fullname) || TextUtils.isEmpty(repassword)) {
//            Toast.makeText(newacc.this, "Fields cannot be Empty", Toast.LENGTH_LONG).show();
//            return;
//        }


            fireBaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(newacc.this, "Registered successfully ", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(newacc.this, "Can't be Registered", Toast.LENGTH_LONG).show();
                            }
                        }
                    });


    }


}
