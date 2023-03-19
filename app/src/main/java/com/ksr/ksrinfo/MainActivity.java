package com.ksr.ksrinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private VideoView videoView;
    private Button continuebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        // continuebtn = findViewById(R.id.continueBtn);
        videoView = findViewById(R.id.videoView);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.introvideo);
        videoView.setVideoURI(uri);

        // Remove the video controls (i.e. play/pause/rewind/etc.)
        videoView.setMediaController(null);

        // Start playing the video
        videoView.start();

        // When the video finishes playing, start a new activity
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    Intent i = new Intent(MainActivity.this, home.class);
                    startActivity(i);
                    finish();
                }

                else {
                    Intent intent = new Intent(MainActivity.this, login.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


//        continuebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent
//                        = new Intent(MainActivity.this, login.class);
//                startActivity(intent);
//            }
//        });

    }
}