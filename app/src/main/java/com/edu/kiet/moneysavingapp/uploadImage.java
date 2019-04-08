package com.edu.kiet.moneysavingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//import java.text.BreakIterator;
//
//import static java.security.AccessController.getContext;

public class uploadImage extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseAuth mAuth;
    private Button choose_image_btn;
    //private Button upload_image_btn;
    private ImageView image_view;
    //private ProgressBar progressbaruploadimage;
    private TextView usernmae;
    private Uri imageUri;

    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        mAuth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_upload_image);
        choose_image_btn = findViewById(R.id.choose_image_btn);
        //upload_image_btn=findViewById(R.id.upload_image_btn);
        image_view = findViewById(R.id.image_view);
        //progressbaruploadimage=findViewById(R.id.progressbaruploadimage);
        usernmae = findViewById(R.id.username);
        databaseReference = FirebaseDatabase.getInstance().getReference("profilepicupload").child(firebaseUser.getUid());
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                usernmae.setText(user.getUsername());
                if (user.getImageURL().equals("default")) {
                    image_view.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(getBaseContext()).load(user.getImageURL()).into(image_view);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        choose_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });


    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            image_view.setImageURI(imageUri);

        }
    }

}
