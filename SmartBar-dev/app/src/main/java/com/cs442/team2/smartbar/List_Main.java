package com.cs442.team2.smartbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.List;


public class List_Main extends ActionBarActivity
{

    List<String> friends;
    ListView list;
    String[] text;
    Integer[] imageId ;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listmain);

        SharedPreferences sharedPreferences = this.getSharedPreferences("smartbar", MODE_PRIVATE);
        String userString = sharedPreferences.getString("user", "");
        Gson gson = new Gson();
        UserEntity user = gson.fromJson(userString, UserEntity.class);


        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        list=(ListView)findViewById(R.id.list);



        mDatabase.child("users").child(user.getUsername()).child("friends").addListenerForSingleValueEvent(new ValueEventListener()
        {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
                friends = dataSnapshot.getValue(t);
                imageId = new Integer[friends.size()];
                text= new String[friends.size()];
                for(int i=0; i<friends.size(); i++)
                {
                    imageId[i] = R.drawable.bar;
                }
                for(String s: friends)
                {
                    text[count++] = s;
                }

                CustomList adapter = new CustomList(List_Main.this, text, imageId);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        Log.w("team2", "loadPost:onCancelled", databaseError.toException());
    }

    });




    list.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Toast.makeText(List_Main.this, "You Clicked at " +text[+ position], Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(List_Main.this,FriendAddWorkout.class);
            intent.putExtra("friendName", text[position]);
            startActivity(intent);
        }
    });
}
}