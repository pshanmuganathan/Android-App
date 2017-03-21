package com.cs442.team2.smartbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    TextView un, pw, signup;
    private DatabaseReference mDatabase;
    ArrayList<UserEntity> userList;
    private UserEntity user;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userList = new ArrayList<>();
        context = this;


        mDatabase = FirebaseDatabase.getInstance().getReference();
        UserEntity user = new UserEntity();



///* ADD FOR UPDATING FRIENDS LIST
////    final ArrayList<FrontDetails> resultse = new ArrayList<FrontDetails>();
////    FrontListBaseAdapter asdf = new FrontListBaseAdapter(context, resultse);
////    lv1.setAdapter(new FrontListBaseAdapter(Front.this, resultse));
////    lv1.setOnItemClickListener(new OnItemClickListener() {
////        @Override
////        public void onItemClick(AdapterView<?> arg0, View arg1,
////        int position, long arg3) {
////            Object o = lv1.getItemAtPosition(position);
////            FrontDetails obj_itemDetails = (FrontDetails)o;
////            Toast.makeText(context, "You have chosen " + ' ' + obj_itemDetails.getMsgType(), Toast.LENGTH_LONG).show();
////        }
////    });
////    */

        Button btnLogIn = (Button) findViewById(R.id.btn_login);
        un = (TextView) findViewById(R.id.input_email);
        pw = (TextView) findViewById(R.id.input_password);
        signup = (TextView) findViewById(R.id.link_signup);
        try{un.setText(getIntent().getExtras().get("username").toString());}catch(Exception e){}


        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = un.getText().toString().trim();
                String p = pw.getText().toString().trim();
                Log.d("UN: ", u);
                Log.d("pw: ", p);
                user_exists(u,p);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent signupIntent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(signupIntent);
            }
        });

    }
    private boolean userExists = false;
    private void user_exists(final String u, final String p) {

        if (u.equals("") || p.equals(""))
            Toast.makeText(getApplicationContext(), "Invalid username or password.", Toast.LENGTH_SHORT).show();

        if(u.equals(null) || p.equals(null))
            Toast.makeText(getApplicationContext(), "Invalid username or password.", Toast.LENGTH_SHORT).show();

        if(u.contains(".") || u.contains("#") || u.contains("$") || u.contains("[") || u.contains("]") ||
                p.contains(".") || p.contains("#") || p.contains("$") || p.contains("[") || p.contains("]"))
            Toast.makeText(getApplicationContext(), "Invalid username or password.", Toast.LENGTH_SHORT).show();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                user = (UserEntity) dataSnapshot.getValue(UserEntity.class);
                final Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);

                if(user == null) {
                    Toast.makeText(getApplicationContext(), "Invalid username or password.", Toast.LENGTH_SHORT).show();
                }
                else if (p.equals(user.getPassword())) {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("smartbar", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    Gson gson = new Gson();
                    editor.putString("user", gson.toJson(user));
                    editor.commit();
                    profileIntent.putExtra("user", user);
                    startActivity(profileIntent);
                    Toast.makeText(getApplicationContext(), "Welcome!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("team2", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase.child("users").child(u).addListenerForSingleValueEvent(postListener);
    }


}