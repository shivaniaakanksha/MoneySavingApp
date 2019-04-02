package com.edu.kiet.moneysavingapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class newacc extends Activity {

    private EditText fnedt;
    private EditText emailedt;
    private EditText passedt;
    private EditText repassedt;
    private ProgressDialog progressDialog;
    FirebaseAuth fireBaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newacc);

        fnedt=findViewById(R.id.fnedt);
        emailedt=(EditText)findViewById(R.id.emailedt);
        passedt=(EditText)findViewById(R.id.passedt);
        repassedt=(EditText)findViewById(R.id.repassedt);
        progressDialog=new ProgressDialog(this);
    }

    private void registeruser() {
        String email = emailedt.getText().toString().trim();
        String password = passedt.getText().toString().trim();
        String fullname = fnedt.getText().toString().trim();
        String repassword = repassedt.getText().toString().trim();


        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(fullname) || TextUtils.isEmpty(repassword)) {
            Toast.makeText(newacc.this, "Fields cannot be Empty", Toast.LENGTH_LONG).show();
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

            fireBaseAuth.createUserWithEmailAndPassword(email, repassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(newacc.this, "Registered successfully ", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(newacc.this, "Can't be Registered", Toast.LENGTH_LONG).show();
                }
                }
                });
            }


    public void submit (View view){
            registeruser();
        }

}
