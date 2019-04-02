package com.edu.kiet.moneysavingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class newacc extends Activity {

    private EditText fnedt;
    private EditText emailedt;
    private EditText passedt;
    private EditText repassedt;
    private Button submitbtn;
    private Button cancelbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newacc);

        fnedt=(EditText)findViewById(R.id.fnedt);
        emailedt=(EditText)findViewById(R.id.emailedt);
        passedt=(EditText)findViewById(R.id.passedt);
        repassedt=(EditText)findViewById(R.id.repassedt);
        submitbtn=(Button)findViewById(R.id.submitbtn);
        cancelbtn=(Button)findViewById(R.id.cancelbtn);
    }

    public void submit(View view) {
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
