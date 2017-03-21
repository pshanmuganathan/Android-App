package com.cs442.team2.smartbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    UserEntity user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        if (intent != null) {
            user = (UserEntity) intent.getSerializableExtra("user");
        }
        final int weight = Integer.parseInt(user.getWeight());
        int height = Integer.parseInt(user.getHeight());

        double x = weight * 0.45;
        double y = height * 0.025;
        double z = y * y;
        double bmi = x / z;


        TextView tname = (TextView) findViewById(R.id.text1);
        TextView tage = (TextView) findViewById(R.id.text2);
        TextView tlocation = (TextView) findViewById(R.id.text3);
        TextView tbmi = (TextView) findViewById(R.id.text4);


        tname.setText(user.getFirstName());
        tage.setText(user.getAge());
        tlocation.setText(user.getLocation());
        tbmi.setText(String.format("%.2f", bmi));

        final Intent logoutIntent = new Intent(this, LoginActivity.class);
        final Intent journalIntent = new Intent(this, UserJournalActivity.class);
        final Intent grpahIntent = new Intent(this, PickdateActivity.class);
        final Intent friendsIntent = new Intent(this, List_Main.class);
        final Intent connectIntent = new Intent(this, ConnectActivity.class);
        Button btnlogout = (Button) findViewById(R.id.Button06);
        Button btnjournal = (Button) findViewById(R.id.journal);
        Button btngraph = (Button) findViewById(R.id.Button02);
        Button btnfriends = (Button) findViewById(R.id.Button03);
        Button btnConnect = (Button) findViewById(R.id.Button05);

        final Bundle b = new Bundle();
        b.putSerializable("user", user);


        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(logoutIntent);
            }
        });
        btnjournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                journalIntent.putExtra("user", user);
                startActivity(journalIntent);
            }
        });
        btngraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("user", user);
                grpahIntent.putExtras(b);
                startActivity(grpahIntent);
            }
        });
        btnfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(friendsIntent);
            }
        });

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(connectIntent);
            }
        });
    }

}

