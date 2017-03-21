package com.cs442.team2.smartbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cs442.team2.smartbar.data.DataBaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by pshanmuganathan on 11/12/2016.
 */

public class GraphActivity extends Activity {
    DataBaseHelper wDbHelper;
    List<WorkoutEntity> workoutHistoryDetails;
    private DatabaseReference mDatabase;
    UserEntity user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Bundle bundle = getIntent().getExtras();
        int month = bundle.getInt("month");
        month++;
        int day = bundle.getInt("day");
        int tday = day;
        int year = bundle.getInt("year");
        SharedPreferences sharedPreferences = this.getSharedPreferences("smartbar", MODE_PRIVATE);
        String userString = sharedPreferences.getString("user", "");

        Gson gson = new Gson();
        workoutHistoryDetails= new ArrayList<>();
        user = gson.fromJson(userString, UserEntity.class);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        loadWorkouts(user,month,day,year,userString);



/*
        wDbHelper = new DataBaseHelper(this);
        try {

            wDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }
        try {

            wDbHelper.openDataBase();

        } catch (Exception sqle) {

            //throw sqle;

        }
        while(i<3) {
            String sql = "select * from workouts where date like '%"+year+"-"+month+"-"+tday+"%' and fk_user_id="+userid;
            Cursor cursor = wDbHelper.getReadableDatabase().rawQuery(sql, null);
            while (cursor.moveToNext())

            {

                int sets = cursor.getInt(cursor.getColumnIndex("sets"));
                int reps = cursor.getInt(cursor.getColumnIndex("reps"));
                int weight = cursor.getInt(cursor.getColumnIndex("weight"));
                double temp = ((double)weight * (double)sets * (double)reps) / (double)1000;
                x[i] = temp;

            }
            tday++;
            i++;
        }
        */

    }
    private void loadWorkouts(UserEntity user,final int month,final int day,final int year,final String username) {
        final double[] x = {0,0,0,0,0};


        mDatabase.child("users").child(user.getUsername()).child("workouts").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                workoutHistoryDetails.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    WorkoutEntity workout = (WorkoutEntity) snapshot.getValue(WorkoutEntity.class);
                    workoutHistoryDetails.add(workout);

                }
                int pickedindex = 0, y = 0, flag = 0,beforindex=0,afterindex=0,m=0,checkbefore=0,checkafter=0,checkpicked=0;
                int beforeindex1 =0,afterindex1 =0,checkafter1=0,checkbefore1=0;
                String date1 = year + "-" + month + "-" + day;
                String[] splitday = date1.split("-");
                int datepicked =  Integer.parseInt(splitday[2]);
                int beforedate = datepicked -1;
                int afterdate = datepicked +1;
                int beforedate1 = datepicked -2;
                int afterdate1 = datepicked +2;


                /*
                for (WorkoutEntity i : workoutHistoryDetails) {


                    if (date1.equals(i.getDate())) {
                        j = y;
                        flag = 1;

                    }
                    y++;

                }
                */
                for (WorkoutEntity i : workoutHistoryDetails) {


                    if (date1.equals(i.getDate())) {
                        for(WorkoutEntity k : workoutHistoryDetails)
                        {
                            String ctr = k.getDate();
                            String[] ctrsplit= ctr.split("-");
                            if(Integer.parseInt(ctrsplit[2]) == beforedate)
                            {
                               beforindex=m;
                                checkbefore = 1;
                            }
                            if(Integer.parseInt(ctrsplit[2]) == afterdate)
                            {
                                afterindex=m;
                                checkafter = 1;
                            }
                            if(Integer.parseInt(ctrsplit[2]) == beforedate1)
                            {
                                beforeindex1=m;
                                checkbefore1 = 1;
                            }
                            if(Integer.parseInt(ctrsplit[2]) == afterdate1)
                            {
                                afterindex1=m;
                                checkafter1 = 1;
                            }
                           m++;
                        }
                        pickedindex = y;
                        checkpicked = 1;

                    }
                    y++;

                }
              if((checkpicked==1) && (checkbefore ==1) && (checkafter==1) && (checkbefore1==1) && (checkafter1==1))
              {

                flag=1;
              }
                else
              {
                  flag=2;
              }
                if (flag == 1)

                {
                    WorkoutEntity entity1 = (WorkoutEntity) workoutHistoryDetails.get(beforindex);
                    String sets1 = entity1.getSets();
                    String reps1 = entity1.getReps();
                    String weight1 = entity1.getBarWeight();
                    Double s1 = Double.parseDouble(sets1);
                    Double s2 = Double.parseDouble(reps1);
                    Double s3 = Double.parseDouble(weight1);
                    double temp1 = (s1 * s2 * s3) / (double) 1000;
                    x[1] = temp1;

                    WorkoutEntity entity2 = (WorkoutEntity) workoutHistoryDetails.get(pickedindex);
                    String sets2 = entity2.getSets();
                    String reps2 = entity2.getReps();
                    String weight2 = entity2.getBarWeight();
                    Double s4 = Double.parseDouble(sets2);
                    Double s5 = Double.parseDouble(reps2);
                    Double s6 = Double.parseDouble(weight2);
                    double temp2 = (s4 * s5 * s6) / (double) 1000;
                    x[2] = temp2;

                    WorkoutEntity entity3 = (WorkoutEntity) workoutHistoryDetails.get(afterindex);
                    String sets3 = entity3.getSets();
                    String reps3 = entity3.getReps();
                    String weight3 = entity3.getBarWeight();
                    Double s7 = Double.parseDouble(sets3);
                    Double s8 = Double.parseDouble(reps3);
                    Double s9 = Double.parseDouble(weight3);
                    double temp3 = (s7 * s8 * s9) / (double) 1000;
                    x[3] = temp3;

                    WorkoutEntity entity4 = (WorkoutEntity) workoutHistoryDetails.get(beforeindex1);
                    String sets4 = entity4.getSets();
                    String reps4 = entity4.getReps();
                    String weight4 = entity4.getBarWeight();
                    Double s10 = Double.parseDouble(sets4);
                    Double s11 = Double.parseDouble(reps4);
                    Double s12 = Double.parseDouble(weight4);
                    double temp4 = (s10 * s11 * s12) / (double) 1000;
                    x[0] = temp4;

                    WorkoutEntity entity5 = (WorkoutEntity) workoutHistoryDetails.get(afterindex1);
                    String sets5 = entity5.getSets();
                    String reps5 = entity5.getReps();
                    String weight5 = entity5.getBarWeight();
                    Double s13 = Double.parseDouble(sets4);
                    Double s14 = Double.parseDouble(reps4);
                    Double s15 = Double.parseDouble(weight4);
                    double temp5 = (s13 * s14 * s15) / (double) 1000;
                    x[4] = temp5;

                    date1 = Integer.toString(month) + "/" + Integer.toString(day - 1);
                    String date2 = Integer.toString(month) + "/" + Integer.toString(day);
                    String date3 = Integer.toString(month) + "/" + Integer.toString(day + 1);
                    String date4 = Integer.toString(month) + "/" + Integer.toString(day -2);
                    String date5 = Integer.toString(month) + "/" + Integer.toString(day +2);


                    GraphView graph = (GraphView) findViewById(R.id.graph);


                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                            new DataPoint(0, x[0]),
                            new DataPoint(1, x[1]),
                            new DataPoint(2, x[2]),
                            new DataPoint(3, x[3]),
                            new DataPoint(4, x[4])

                    });

                    graph.addSeries(series);
                    StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                    staticLabelsFormatter.setHorizontalLabels(new String[]{date4,date1, date2, date3,date5});


                    graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                    graph.getGridLabelRenderer().setNumHorizontalLabels(5);



                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(0);
                    graph.getViewport().setMaxY(3);
                    //series.setDrawBackground(true);
                    //series.setBackgroundColor(Color.BLUE);
                    series.setDrawDataPoints(true);
                    series.setDataPointsRadius(10);


                    graph.getGridLabelRenderer().setHumanRounding(false);
                    graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);

                } else {
                    if(flag ==2) {
                        Toast.makeText(getApplicationContext(), "No Continuity of Workout Data", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "No Workouts for the Date Chosen", Toast.LENGTH_SHORT).show();
                    }
                        Intent intent = new Intent(getApplicationContext(), PickdateActivity.class);


                    startActivity(intent);
                    finish();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("team2", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

}
