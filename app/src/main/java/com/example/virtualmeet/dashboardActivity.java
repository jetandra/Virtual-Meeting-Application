package com.example.virtualmeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class dashboardActivity extends AppCompatActivity {
    EditText secretCodeBox;
    Button joinBtn, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        secretCodeBox = findViewById(R.id.codeBox);
        joinBtn = findViewById(R.id.joinBtn);
        logout = findViewById(R.id.loginBtn);


        URL serverURL;

        try {
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOption = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverURL)
                    .setWelcomePageEnabled(false).build();
            JitsiMeet.setDefaultConferenceOptions(defaultOption);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }




        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (secretCodeBox.getText().toString().isEmpty()){
                    secretCodeBox.setError("Please enter your Code");
                    return;
                }

                  JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                          .setRoom(secretCodeBox.getText().toString())
                          .setWelcomePageEnabled(false)
                          .build();

                JitsiMeetActivity.launch(dashboardActivity.this,options);
            }
        });

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(dashboardActivity.this,LoginActivity.class));
//                finish();
//            }
//        });
    }
}