package com.example.feelslikemonday;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.feelslikemonday.ui.home.HomeFragment;
import com.example.feelslikemonday.ui.login.SignupActivity;
import com.example.feelslikemonday.ui.moods.AddNewMoodActivity;
import com.example.feelslikemonday.ui.map.MapsActivity;
import com.example.feelslikemonday.ui.moods.AddNewMoodActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.feelslikemonday.ui.home.HomeFragment;
import com.example.feelslikemonday.ui.login.SignupActivity;
import com.example.feelslikemonday.ui.map.MapsActivity;
import com.example.feelslikemonday.ui.moods.AddNewMoodActivity;
import com.google.android.material.navigation.NavigationView;
/*
This class is responsible for displaying all of the signed in user's moods in sorted order
*/
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private SharedPreferences pref;
    private String myUserID;

    @Override
    public void onBackPressed() {
        //do something on back button pressed
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getApplicationContext().getSharedPreferences(SignupActivity.PREFS_NAME, 0);
        myUserID = pref.getString(SignupActivity.USERNAME_KEY,null);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView textViewUser = headerView.findViewById(R.id.LoggedInUser_txtView);
        textViewUser.setText(myUserID);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_friends, R.id.nav_followerrequest,R.id.nav_sendrequest)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                Intent add = new Intent(this, AddNewMoodActivity.class);
                this.startActivity(add);
                Intent myIntent = new Intent(this, AddNewMoodActivity.class);
                this.startActivity(myIntent);
                break;
            case R.id.action_filter:
                Bundle bundle = new Bundle();
                bundle.putBoolean("filter", true);
                HomeFragment filter = new HomeFragment();
                filter.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, filter).commit();
                break;
            case R.id.action_show_map:
                Intent maps = new Intent(this, MapsActivity.class);
                this.startActivity(maps);
                break;
            case R.id.action_logout:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
