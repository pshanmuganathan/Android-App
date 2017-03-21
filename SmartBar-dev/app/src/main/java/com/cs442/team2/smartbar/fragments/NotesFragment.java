package com.cs442.team2.smartbar.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import com.cs442.team2.smartbar.R;
import com.cs442.team2.smartbar.UserEntity;
import com.cs442.team2.smartbar.WorkoutEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by SumedhaGupta on 11/23/16.
 */

public class NotesFragment extends Fragment implements AdapterView.OnItemClickListener {

    List<WorkoutEntity> workoutHistoryDetails;
    private DatabaseReference mDatabase;
    private Bundle saveInstance;
    ListView workoutHistory;
    ArrayAdapter workoutListAdapter;
    String dateOfWorkout;
    private Date selectedDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.saveInstance = savedInstanceState;
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_workouts_notes, container, false);


        Bundle bundle = getArguments();
        if (bundle != null) {
            selectedDate = (Date) bundle.getSerializable("selectedDate");
        }

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("smartbar", MODE_PRIVATE);
        String userString = sharedPreferences.getString("user", "");

        Gson gson = new Gson();
        UserEntity user = gson.fromJson(userString, UserEntity.class);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        workoutHistoryDetails = new ArrayList<>();
        loadWorkouts(user);
        TextView selectedDateTxtView = (TextView) v.findViewById(R.id.selectedDate);
        selectedDateTxtView.setText("Date : " + removeTime(selectedDate));
        workoutHistory = (ListView) v.findViewById(R.id.workoutsList);
        // Creating the list adapter and populating the list
        workoutListAdapter = new ArrayAdapter(v.getContext(), android.R.layout.simple_list_item_2, workoutHistoryDetails) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TwoLineListItem row;
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    row = (TwoLineListItem) inflater.inflate(android.R.layout.simple_list_item_2, null);
                } else {
                    row = (TwoLineListItem) convertView;
                }
                WorkoutEntity data = workoutHistoryDetails.get(position);
                row.getText1().setText(data.getExercise());
                row.getText2().setText(data.getDate());
                dateOfWorkout = data.getDate();

                return row;
            }
        };
        workoutHistory.setOnItemClickListener(this);
        workoutHistory.setAdapter(workoutListAdapter);
        workoutListAdapter.notifyDataSetChanged();


        return v;
    }

    private void loadWorkouts(UserEntity user) {
        mDatabase.child("users").child(user.getUsername()).child("workouts").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                workoutHistoryDetails.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    WorkoutEntity workout = (WorkoutEntity) snapshot.getValue(WorkoutEntity.class);
                    if (workout != null) {
                        Date setDate = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            setDate = dateFormat.parse(workout.getDate());
                            if (removeTime(setDate).compareTo(removeTime(selectedDate)) == 0) {
                                workoutHistoryDetails.add(workout);
                            }
                        } catch (Exception e) {
                            Log.d("asda", "error");
                        }
                    }


                }
                if (workoutHistoryDetails.size() == 0) {
                    WorkoutEntity w = new WorkoutEntity();
                    w.setExercise("No Workouts!!!");
                    w.setDate("");
                    workoutHistoryDetails.add(w);
                }

                workoutListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("team2", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    public Date removeTime(Date date) {
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date);
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        return cal2.getTime();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        WorkoutEntity workout = (WorkoutEntity) workoutHistory.getItemAtPosition(position);


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        AddNoteFragment addNoteFragment = new AddNoteFragment();
        fragmentTransaction.replace(R.id.noteFragment, addNoteFragment, "addviewnote");
        Bundle bundle = new Bundle();
        bundle.putSerializable("workout", workout);
        addNoteFragment.setArguments(bundle);
        //fragmentTransaction.addToBackStack("notes");
        fragmentTransaction.commit();


    }
}
