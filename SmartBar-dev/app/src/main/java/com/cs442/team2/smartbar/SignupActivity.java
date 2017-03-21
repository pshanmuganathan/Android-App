package com.cs442.team2.smartbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    String firstname = "";
    String lastname= "";
    String email = "";
    String password = "";
    String cpass = "";
    String age = "";
    String height = "";
    String weight = "";
    UserEntity user;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button btnSignUp = (Button) findViewById(R.id.btnSignUp);
        final TextView txtfirstname = (TextView) findViewById(R.id.txtfname);
        final TextView txtlastname = (TextView) findViewById(R.id.txtlname);
        final TextView txtemail = (TextView) findViewById(R.id.txtEmail);
        final TextView txtpassword = (TextView) findViewById(R.id.txtPass);
        final TextView txtcpass = (TextView) findViewById(R.id.txtConfirmPass);
        final TextView txtage = (TextView) findViewById(R.id.txtDOB);
        final TextView txtheight = (TextView) findViewById(R.id.txtHeight);
        final TextView txtweight = (TextView) findViewById(R.id.txtWeight);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstname = txtfirstname.getText().toString();
                lastname = txtlastname.getText().toString();
                email = txtemail.getText().toString();
                password = txtpassword.getText().toString();
                cpass = txtcpass.getText().toString();
                age = txtage.getText().toString();
                height = txtheight.getText().toString();
                weight = txtweight.getText().toString();

                if (firstname.equals("") || lastname.equals("") || email.equals("")|| password.equals("")
                        || cpass.equals("") || age.equals("") || height.equals("") || weight.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                }else if (!password.equals(cpass)){
                    Toast.makeText(getApplicationContext(), "Password do not match.", Toast.LENGTH_SHORT).show();
                }

                else{
                    user = new UserEntity(firstname,lastname,email, password,age,height,weight);
                    user.setUsername(email.split("@")[0]);
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("users").child(user.getUsername()).setValue(user);
                    final Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    loginIntent.putExtra("username", user.getUsername());
                    startActivity(loginIntent);}
            }
        });
    }
}

