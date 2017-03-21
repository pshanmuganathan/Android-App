package com.cs442.team2.smartbar;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.cs442.team2.smartbar.fragments.NotesFragment;

public class NotesActivity extends FragmentActivity {

    NotesFragment notesFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        notesFragment = new NotesFragment();
        fragmentTransaction.replace(R.id.fragment_conatiner, notesFragment, "notes");
        fragmentTransaction.commit();
    }
}
