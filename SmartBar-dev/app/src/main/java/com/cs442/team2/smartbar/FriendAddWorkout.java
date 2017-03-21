package com.cs442.team2.smartbar;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;


public class FriendAddWorkout extends AppCompatActivity
{
    UserEntity friend;
    private DatabaseReference mDatabase;
    UserEntity user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendworkoutpage);
        String friendName = getIntent().getStringExtra("friendName");

        SharedPreferences sharedPreferences =this.getSharedPreferences("smartbar", MODE_PRIVATE);
        String userString = sharedPreferences.getString("user", "");

        Gson gson = new Gson();
        user = gson.fromJson(userString, UserEntity.class);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final TextView tname = (TextView)findViewById(R.id.text1);
        final TextView tage = (TextView)findViewById(R.id.text2);
        final TextView tlocation = (TextView)findViewById(R.id.text3);
        final TextView tbmi = (TextView)findViewById(R.id.text4);


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                friend = (UserEntity) dataSnapshot.getValue(UserEntity.class);

                final int weight = Integer.parseInt(friend.getWeight());
                Double height = Double.parseDouble(friend.getHeight());


                double x = weight * 0.35;
                double y = height * 0.045;
                double z = y * y;
                double bmi = x / z;


                tname.setText(friend.getFirstName() + friend.getLastName());
                tage.setText(friend.getAge());
                tlocation.setText(friend.getLocation());
                tbmi.setText(String.format("%.2f",bmi));



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("team2", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase.child("users").child(friendName).addListenerForSingleValueEvent(postListener);







        Button wrkbutton = (Button)findViewById(R.id.WorkButton);

        wrkbutton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.bar)
                        .setContentTitle("Friend Workout")
                        .setContentText("Friend has requested Workout");

                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
                mNotificationManager.notify(1, mBuilder.build());


                WorkoutEntity w= new WorkoutEntity();
                w.setwId("workout3");
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("users").child(friend.getUsername()).child("workouts").setValue(w);
                mDatabase.child("users").child(user.getUsername()).child("workouts").setValue(w);

                // Creates an explicit intent for an Activity in your app
               /* //  Intent resultIntent = new Intent(this, CalendarActivity.class);

                // The stack builder object will contain an artificial back stack for the started Activity.
                // This ensures that navigating backward from the Activity leads out of your application to the Home screen.

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                // Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(CalendarActivity.class);

                // Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);

                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                // mId allows you to update the notification later on.
                //mNotificationManager.notify(mId, mBuilder.build();*/
            }
        });

    }

}
