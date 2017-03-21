package com.cs442.team2.smartbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import static com.cs442.team2.smartbar.R.layout.workout_list_txtview;

/**
 * Created by SumedhaGupta on 11/24/16.
 */

public class WorkoutListAdapter extends ArrayAdapter<WorkoutEntity> implements Filterable {

    int resource;
    public Context context;
    public ArrayList<WorkoutEntity> workoutsList;
    public ArrayList<WorkoutEntity> orig;

    public WorkoutListAdapter(Context context, int resource, ArrayList<WorkoutEntity> workoutsList) {
        super(context, resource, workoutsList);
        this.resource = resource;
        this.workoutsList = workoutsList;
        this.context = context;
    }

    public class StudentHolder {
        TextView userName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        StudentHolder holder;
        final WorkoutEntity student = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(workout_list_txtview, parent, false);
            holder = new StudentHolder();
            holder.userName = (TextView) convertView.findViewById(R.id.workoutListTextview);
            convertView.setTag(holder);
        } else {
            holder = (StudentHolder) convertView.getTag();
        }

        //final TextView itemName = (TextView) convertView.findViewById(R.id.studentListTextview);
        //itemName.setTag(position);
        holder.userName.setText(student.getExercise());
        holder.userName.setText(workoutsList.get(position).getExercise());

        return convertView;
    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<WorkoutEntity> results = new ArrayList<WorkoutEntity>();
                if (orig == null)
                    orig = workoutsList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final WorkoutEntity g : orig) {
                            if (g.getExercise().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                workoutsList = (ArrayList<WorkoutEntity>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return workoutsList.size();
    }

    @Override
    public WorkoutEntity getItem(int position) {
        return workoutsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
