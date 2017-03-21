package com.cs442.team2.smartbar;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.cs442.team2.smartbar.fragments.CalendarFragment;
import com.cs442.team2.smartbar.fragments.NotesFragment;
import com.cs442.team2.smartbar.fragments.OnClickOpenModule;
import com.cs442.team2.smartbar.fragments.UserJournalFragment;

import java.util.Date;

/**
 * Created by SumedhaGupta on 10/27/16.
 */

public class UserJournalActivity extends ActionBarActivity implements OnClickOpenModule {

    UserJournalFragment userJournalFragment;
    UserEntity user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        Intent intent = getIntent();
        if (intent != null) {
            user = (UserEntity) intent.getSerializableExtra("user");

        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        userJournalFragment = new UserJournalFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        userJournalFragment.setArguments(bundle);
        userJournalFragment.setOpenModuleInterface(this);
        fragmentTransaction.replace(R.id.fragment_conatiner, userJournalFragment, "user_journal");
        fragmentTransaction.commit();
    }

    @Override
    public void callOpenModule(String module, Date date) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (module) {
            case "UserJournalActivity":
                CalendarFragment fragment = new CalendarFragment();
                fragment.setOpenModuleInterface(this);
                fragmentTransaction.replace(R.id.fragment_conatiner, fragment, "calendar");
                fragmentTransaction.addToBackStack("user_journal");
                fragmentTransaction.commit();
                break;

            case "notesfragment":
                NotesFragment notesFragment = new NotesFragment();
                fragmentTransaction.replace(R.id.fragment_conatiner, notesFragment, "notes");
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedDate", date);
                notesFragment.setArguments(bundle);
                fragmentTransaction.addToBackStack("calendar");
                fragmentTransaction.commit();
                break;
        }


    }
}




