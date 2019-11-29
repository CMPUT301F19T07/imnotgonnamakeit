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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.feelslikemonday.ui.login.LoginMainActivity;
import com.example.feelslikemonday.ui.login.SignupActivity;
import com.example.feelslikemonday.ui.map.FollowingMapActivity;
import com.example.feelslikemonday.ui.map.MapsActivity;
import com.example.feelslikemonday.ui.moods.AddNewMoodActivity;
import com.google.android.material.navigation.NavigationView;

import static com.example.feelslikemonday.ui.login.LoginMainActivity.USERNAME_KEY;

/**
 * This class is the 'shell' of sorts, where all the fragments are displayed within. Includes the toolbar and hamburger menu, which
 * are always called from here, regardless of which activity fragment is currently open. This activity will never actually open by itself
 * (supposedly) and will always be seen in conjunction with one of the activity fragments. Opens initially when the user successfully logs in.
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private SharedPreferences pref;
    private String myUserID;

    @Override
    public void onBackPressed() {
        //do nothing on back button pressed
    }

    /**
     * This sets up MainActivity, populating the menus and toolbar.
     * Sets up the username, titles ,profile pictures, and anything else that
     * is available in all activity fragments.
     *
     * @param savedInstanceState This is a previous saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getApplicationContext().getSharedPreferences(SignupActivity.PREFS_NAME, 0);
        myUserID = pref.getString(SignupActivity.USERNAME_KEY, null);
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
                R.id.nav_home, R.id.nav_myfriend, R.id.nav_gallery, R.id.nav_followerrequest, R.id.nav_sendrequest)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    /**
     * This inflates the menus
     *
     * @param menu Menu to be inflated
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * This navigates up within the application's activity hierarchy from the action bar.
     *
     * @return return true if Up navigation completed successfully and this Activity was finished, false otherwise.
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * This returns false to have the normal processing happen. Conducts the logic for the
     * action settings bar on the right side. Takes you to the add new mood, map, following map activities, as well as logout option.
     *
     * @param item This is the menu item that is selected.
     * @return return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent add = new Intent(this, AddNewMoodActivity.class);
                this.startActivity(add);
                break;
            case R.id.action_show_map:
                Intent maps = new Intent(this, MapsActivity.class);
                this.startActivity(maps);
                break;
            case R.id.action_show_following_map:
                Intent following_maps = new Intent(this, FollowingMapActivity.class);
                this.startActivity(following_maps);
                break;
            case R.id.action_logout:
                SharedPreferences.Editor editor = pref.edit();
                editor.remove(USERNAME_KEY);
                editor.apply();
                Intent exit = new Intent(this, LoginMainActivity.class);
                exit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

                this.startActivity(exit);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
