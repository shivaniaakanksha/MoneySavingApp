package com.edu.kiet.moneysavingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
//import android.support.design.widget.Snackbar;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.auth.api.Auth;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Pattern;

//import static com.edu.kiet.moneysavingapp.R.id.snackbar;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN =1001 ;
    private static final String TAG ="xyz" ;


    private EditText emailip;
    private EditText passip;
    private Button loginbtn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    GoogleSignInClient mGoogleSignInClient;
    private com.google.android.gms.common.SignInButton googlebtn;
    private TextInputLayout emailsignin;
    private TextInputLayout passsignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();
        emailip=(EditText)findViewById(R.id.emailip);
        passip=(EditText)findViewById(R.id.passip);
        loginbtn=(Button)findViewById(R.id.loginbtn);

        emailsignin = findViewById(R.id.emailsignin);
        passsignin = findViewById(R.id.passsignin);

        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!=null){
                    //startActivity(new Intent(MainActivity.this,dashboard.class));
                }

            }
        };

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("554461307687-v8bggepspq1q8stvhb1oh6s1mdckjh84.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
        googlebtn= findViewById(R.id.googlebtn);
        googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                Toast.makeText(MainActivity.this,"User Signed In",Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {
                Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this,"User Signed In",Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.snackbar), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this,"login failed",Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }



    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void startSignIn(){
        String email=emailip.getText().toString();
        String password=passip.getText().toString();

        if(!validateEmail() | !validatePassword() ){
            return;
        }
            else {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        //Snackbar.make(findViewById(R.id.snackbar), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this,"SignIn failed",Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(MainActivity.this,"SignIn Successfully",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

//        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
//            //Toast.makeText(MainActivity.this,"Fields cannot be Empty",Toast.LENGTH_LONG).show();
//
//        }else{

    }

//    }


    public void newaccount(View view) {
        Intent intent = new Intent(this, newacc.class);
        startActivity(intent);
    }


    private boolean validateEmail() {
        String emailInput = emailsignin.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            emailsignin.setError("   Field can't be Empty");
            return false;

        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailsignin.setError("   Please enter a valid email address");
            return false;
        } else {
            emailsignin.setError(null);
            return true;
        }

    }

    private boolean validatePassword() {
        String passInput = passsignin.getEditText().getText().toString().trim();
        if (passInput.isEmpty()) {
            passsignin.setError("   Field can't be Empty");
            return false;

        }
        else {
            passsignin.setError(null);
            return true;
        }

    }

}
