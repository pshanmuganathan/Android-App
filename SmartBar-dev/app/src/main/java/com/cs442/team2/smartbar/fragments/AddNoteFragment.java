package com.cs442.team2.smartbar.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs442.team2.smartbar.R;
import com.cs442.team2.smartbar.UserEntity;
import com.cs442.team2.smartbar.WorkoutEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Abhishekgupta on 11/23/16.
 */

public class AddNoteFragment extends Fragment {


    private DatabaseReference mDatabase;
    WorkoutEntity workout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final View v = inflater.inflate(R.layout.fragment_note, container, false);


        SharedPreferences sharedPreferences = getContext().getSharedPreferences("smartbar", MODE_PRIVATE);
        String userString = sharedPreferences.getString("user", "");

        Gson gson = new Gson();
        final UserEntity user = gson.fromJson(userString, UserEntity.class);


        final EditText nodeEdtText = (EditText) v.findViewById(R.id.noteEdtText);
        Button saveNoteBtn = (Button) v.findViewById(R.id.saveBtn);
        Bundle bundle = getArguments();
        if (bundle != null) {
            workout = (WorkoutEntity) bundle.getSerializable("workout");
            nodeEdtText.setText(workout.getNotes());
        }
        saveNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                workout.setNotes(nodeEdtText.getText().toString());
                String workoutId = workout.getwId();
                mDatabase.child("users").child(user.getUsername()).child("workouts").child(workoutId).setValue(workout).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "Added Notes...", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        return v;
    }


}
