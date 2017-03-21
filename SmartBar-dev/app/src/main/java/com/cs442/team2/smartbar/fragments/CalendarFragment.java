package com.cs442.team2.smartbar.fragments;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.cs442.team2.smartbar.R;
import com.cs442.team2.smartbar.UserEntity;
import com.cs442.team2.smartbar.WorkoutEntity;
import com.cs442.team2.smartbar.data.DataBaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by SumedhaGupta on 11/6/16.
 */

public class CalendarFragment extends Fragment {


    private int yr, mon, dy;

    DataBaseHelper wDbHelper;
    List<WorkoutEntity> workoutHistoryDetails;
    private CaldroidFragment caldroidFragment;
    TextView date;
    private Bundle saveInstance;
    OnClickOpenModule onClickOpenModule;
    private DatabaseReference mDatabase;

    public void setOpenModuleInterface(OnClickOpenModule onClickOpenModule) {
        this.onClickOpenModule = onClickOpenModule;
    }


    private DatePickerDialog.OnDateSetListener dateListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int
                        monthOfYear, int dayOfMonth) {
                    yr = year;
                    mon = monthOfYear;
                    dy = dayOfMonth;
                    updateDisplay();
                    Toast.makeText(getContext(), "Date choosen is " + date.getText(), Toast.LENGTH_SHORT).show();
                    String selectedDate = new String();
                    selectedDate = yr + "-" + (mon + 1) + "-" + dy;
                    Date setDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        setDate = dateFormat.parse(selectedDate);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Log.d("sumedha", "" + setDate);
                    caldroidFragment.setCalendarDate(setDate);
                }

                /** Updates the date in the TextView */
                private void updateDisplay() {
                    StringBuilder a = new StringBuilder().append(mon + 1).append("/")
                            .append(dy).append("/")
                            .append(yr).append(" ");

                    date.setText(a);
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.saveInstance = savedInstanceState;
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);


        SharedPreferences sharedPreferences = getContext().getSharedPreferences("smartbar", MODE_PRIVATE);
        String userString = sharedPreferences.getString("user", "");

        Gson gson = new Gson();
        UserEntity user = gson.fromJson(userString, UserEntity.class);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        workoutHistoryDetails = new ArrayList<>();
        loadWorkouts(user);


        date = (TextView) v.findViewById(R.id.dateTxtView);


        Button datePickerButton = (Button) v.findViewById(R.id.date_picker_button);

        /** This integer uniquely defines the dialog to be used for displaying date picker.*/

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mon = caldroidFragment.getMonth();
                yr = caldroidFragment.getYear();
                //dy=caldroidFragment.get;
                new DatePickerDialog(getContext(), dateListener, yr, mon, dy).show();

            }
        });


        return v;
    }

    private void loadCaldroid() {
        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        // Setup caldroid fragment
        // **** If you want normal CaldroidFragment, use below line ****
        caldroidFragment = new CaldroidFragment();
        caldroidFragment.setStyle(CaldroidFragment.STYLE_NORMAL,
                android.R.style.Theme_Black);

        ////////////////////////////////////////////////////////////////////////
        // **** This is to show customized fragment. If you want customized
        // version, uncomment below line ****
        // caldroidFragment = new CaldroidSampleCustomFragment();

        // Setup arguments

        // If Activity is created after rotation
        if (this.saveInstance != null) {
            caldroidFragment.restoreStatesFromKey(this.saveInstance,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            // Uncomment this to customize startDayOfWeek
            // args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
            // CaldroidFragment.TUESDAY); // Tuesday

            // Uncomment this line to use Caldroid in compact mode
            // args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);

            // to use dark theme
            args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);

            caldroidFragment.setArguments(args);
        }

        setCustomResourceForDates();

        // Attach to the activity
        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
               /* Toast.makeText(getContext(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), NotesActivity.class);
                startActivity(intent);*/

                onClickOpenModule.callOpenModule("notesfragment", date);


            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getContext(), text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getContext(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                   /* Toast.makeText(getContext(),
                            "Caldroid view is created", Toast.LENGTH_SHORT)
                            .show();*/
                }
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);

        //final TextView textView = (TextView) v.findViewById(R.id.textview);
    }

    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();
        //highlighting the dates
        for (WorkoutEntity w : workoutHistoryDetails) {
            String dateString = w.getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(dateString);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Date today = cal.getTime();
            //blue color for past workouts
            if (removeTime(convertedDate).compareTo(removeTime(today)) < 0) {

                Log.d("sumedha", "hi");
                ColorDrawable g = new ColorDrawable(Color.BLUE);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(convertedDate);
                caldroidFragment.setBackgroundDrawableForDate(g, calendar.getTime());
            }
            //green color for future workouts
            else {
                ColorDrawable g = new ColorDrawable(Color.GREEN);

                cal.setTime(convertedDate);
                cal.add(Calendar.DATE, 0);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(convertedDate);
                /*calendar.set(calendar.YEAR, calendar.MONTH, calendar.DATE, 8,0,0);
                setAlarm(calendar);*/
                caldroidFragment.setBackgroundDrawableForDate(g, calendar.getTime());

            }
        }

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

    private void loadWorkouts(UserEntity user) {
        mDatabase.child("users").child(user.getUsername()).child("workouts").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                workoutHistoryDetails.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    WorkoutEntity workout = (WorkoutEntity) snapshot.getValue(WorkoutEntity.class);
                    workoutHistoryDetails.add(workout);

                }
                loadCaldroid();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("team2", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

}
