package com.example.devas.app_10;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.ButtonClickInterface, NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
   // public Object showFragmentWithTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_main_Activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Main Page");

        navigationView=(NavigationView)findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.navdrawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_closed)
        {
            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        MainActivityFragment fragment = MainActivityFragment.newInstance(1);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout,fragment)
                .commit();
    }

    @Override
    public void onButtonClickListener(View v) {

        CoverpageFragment coverpageFragment = CoverpageFragment.newInstance(R.id.coverpage);
        coverpageFragment.setEnterTransition(new Slide(Gravity.LEFT));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout,coverpageFragment).addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menufile, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        Intent intent;
        switch (id)
        {
            case R.id.task1 :
                intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                return  true;

            case R.id.task2 :
                intent = new Intent(this,RecyclerViewClass.class);
                startActivity(intent);
                return  true;

            case R.id.task3 :
                intent = new Intent(this,SecondActivity.class);
                startActivity(intent);
                return  true;

        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id)
        {
            case R.id.aboutme_drawer:
                CoverpageFragment coverpageFragment = CoverpageFragment.newInstance(R.id.coverpage);
                coverpageFragment.setEnterTransition(new Slide(Gravity.LEFT));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout,coverpageFragment).addToBackStack(null).commit();
                break;
            case R.id.task2_drawer:
                intent = new Intent(this,RecyclerViewClass.class);
                startActivity(intent);
                break;
            case R.id.task3_drawer:
                intent = new Intent(this,SecondActivity.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
