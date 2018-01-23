package com.dn_evtukhova.mainjournal1;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.dn_evtukhova.mainjournal1.fragments.FragmentAboutApp;
import com.dn_evtukhova.mainjournal1.fragments.FragmentBuget;
import com.dn_evtukhova.mainjournal1.fragments.FragmentCategory;
import com.dn_evtukhova.mainjournal1.fragments.FragmentHistory;
import com.dn_evtukhova.mainjournal1.fragments.FragmentJournalExpediture;
import com.dn_evtukhova.mainjournal1.fragments.FragmentTapeExpediture;

public class MainJournal1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

FragmentAboutApp fAboutApp;
FragmentBuget fBuget;
FragmentHistory fHistory;
FragmentJournalExpediture fJournalExpediture;
FragmentTapeExpediture fTapeExpediture;
FragmentCategory fCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_journal1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fAboutApp = new FragmentAboutApp();
        fBuget = new FragmentBuget();
        fHistory = new FragmentHistory();
        fJournalExpediture = new FragmentJournalExpediture();
        fTapeExpediture = new FragmentTapeExpediture();
        fCategory = new FragmentCategory();

        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, fJournalExpediture);
        ft.commit();


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_journal1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        android.support.v4.app.FragmentTransaction fTr = getSupportFragmentManager().beginTransaction();
        int id = item.getItemId();

        if (id == R.id.tape_expenditure) {
            // Handle the camera action
            fTr.replace(R.id.container, fTapeExpediture);
        } else if (id == R.id.buget ){
            fTr.replace(R.id.container, fBuget);
        } else if (id == R.id.journal_expenditure) {
            fTr.replace(R.id.container, fJournalExpediture);
        } else if (id == R.id.category) {
            fTr.replace(R.id.container, fCategory);
        } else if (id == R.id.history) {
            fTr.replace(R.id.container, fHistory);

        } else if (id == R.id.about_app) {
            fTr.replace(R.id.container, fAboutApp);

        } fTr.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
