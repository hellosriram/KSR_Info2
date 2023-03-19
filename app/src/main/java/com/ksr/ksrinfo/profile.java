package com.ksr.ksrinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    ImageView ivImage;
    TextView tvName;
    Button btLogout;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivImage = findViewById(R.id.iv_image);
        tvName = findViewById(R.id.tv_name);
        btLogout = findViewById(R.id.bt_logout);
        DatabaseReference RootRef;

        // Initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        RootRef = FirebaseDatabase.getInstance().getReference();

        String currentUserId= firebaseUser.getUid();

        RootRef.child("Users").child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                        String uname = snapshot.child("name").getValue().toString();
                        tvName.setText(uname);
                        Toast.makeText(profile.this, uname, Toast.LENGTH_SHORT).show();

                }
                else{
                    // When firebase user is not equal to null set image on image view
                    Glide.with(profile.this).load(firebaseUser.getPhotoUrl()).into(ivImage);
                    // set name on text view
                    tvName.setText(firebaseUser.getDisplayName());
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


                // Initialize sign in client
                googleSignInClient = GoogleSignIn.getClient(profile.this, GoogleSignInOptions.DEFAULT_SIGN_IN);

                btLogout.setOnClickListener(view -> {
                    // Sign out from google
                    googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // Check condition
                            if (task.isSuccessful()) {
                                // When task is successful sign out from firebase
                                firebaseAuth.signOut();
                                // Display Toast
                                Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_SHORT).show();
                                // Finish activity
                                finish();
                            }
                        }
                    });
                });
            }
        }
