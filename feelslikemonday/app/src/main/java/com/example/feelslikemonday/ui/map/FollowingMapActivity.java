package com.example.feelslikemonday.ui.map;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;
import com.example.feelslikemonday.DAO.FollowPermissionCallback;
import com.example.feelslikemonday.DAO.FollowPermissionDAO;
import com.example.feelslikemonday.DAO.UserCallback;
import com.example.feelslikemonday.DAO.UserDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.FollowPermission;
import com.example.feelslikemonday.model.MoodEvent;
import com.example.feelslikemonday.model.User;
import com.example.feelslikemonday.ui.login.SignupActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.List;

import io.opencensus.resource.Resource;

/*
 *This class is responsible to show the user's followees' recent mood map
 */
public class FollowingMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SharedPreferences pref;
    private String myUserID;
    private User currentUser;
    private List<MoodEvent> FolloweeCurrentMoodList = new ArrayList<>() ;
    private LatLng currentLocation;
    private String markerLocation;
    private BitmapDescriptor markerType;
    private FollowPermission currentFolloPermission;
    private List<String> followeeList = new ArrayList<>();
    private int i;
    private String followeeName;
    private MoodEvent recentMoodEvent;
    private String moodIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        pref = getApplicationContext().getSharedPreferences(SignupActivity.PREFS_NAME, 0);
        myUserID = pref.getString(SignupActivity.USERNAME_KEY, null);
        FollowPermissionDAO followPermissionDAO = new FollowPermissionDAO();
        followPermissionDAO.get(myUserID,new FollowPermissionCallback() {
            @Override
            public void onCallback(FollowPermission followPermission) {
                currentFolloPermission = followPermission;
                followeeList = currentFolloPermission.getFolloweeUsernames();
                if(followeeList.size()>0){
                    UserDAO userDAO = new UserDAO();
                    for(i=0;i<followeeList.size();i++){
                        userDAO.get(followeeList.get(i), new UserCallback() {
                            @Override
                            public void onCallback(User user) {
                                currentUser = user;
                                FolloweeCurrentMoodList =currentUser.getMoodHistory();
                                followeeName = currentUser.getUsername();
                                if(FolloweeCurrentMoodList.size()>0){
                                    recentMoodEvent= FolloweeCurrentMoodList.get(0);
                                    markerLocation = recentMoodEvent.getLocation();
                                    if(markerLocation !=null){
                                        placeMarkers();
                                    }
                                }
                            }
                        }, new VoidCallback() {
                            @Override
                            public void onCallback() {
                                Log.v("succc", "succ");
                            }
                        });
                    } }
            }},new VoidCallback() {
            @Override
            public void onCallback(){
                Log.v("succc", "succ");
            }
        });

    }


    private void placeMarkers() {
        markerLocation = recentMoodEvent.getLocation();
        switch (recentMoodEvent.getMoodType().getName()) {
            case "Anger":
                moodIcon = "anger.bmp";
                break;
            case "Disgust":
                moodIcon = "disgust.bmp";
                break;
            case "Fear":
                moodIcon = "fear.bmp";
                break;
            case "Happiness":
                moodIcon = "happiness.bmp";
                break;
            case "Sadness":
                moodIcon = "sadness.bmp";
                break;
            case "Surprise":
                moodIcon = "surprise.bmp";
                break;
        }
        String[] latLongSplit = markerLocation.split(" ");
        currentLocation = new LatLng(Double.valueOf(latLongSplit[1]), Double.valueOf(latLongSplit[0]));
        mMap.addMarker(new MarkerOptions().position(currentLocation).icon(BitmapDescriptorFactory.fromAsset(moodIcon)).title(followeeName).snippet(recentMoodEvent.getMoodType().getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));

    }

}