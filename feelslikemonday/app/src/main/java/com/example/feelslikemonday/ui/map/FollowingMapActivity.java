package com.example.feelslikemonday.ui.map;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.feelslikemonday.DAO.FollowPermissionCallback;
import com.example.feelslikemonday.DAO.FollowPermissionDAO;
import com.example.feelslikemonday.DAO.FollowPermissionCallback;
import com.example.feelslikemonday.DAO.UserCallback;
import com.example.feelslikemonday.DAO.UserDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.FollowPermission;
import com.example.feelslikemonday.model.FolloweeMoodEvent;
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
                    for(i=0;i<followeeList.size()-1;i++){
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
                    markerType = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                    break;
                case "Disgust":
                    markerType = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                    break;
                case "Fear":
                    markerType = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
                    break;
                case "Happiness":
                    markerType = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA);
                    break;
                case "Sadness":
                    markerType = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
                    break;
                case "Surprise":
                    markerType = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
                    break;
            }
            String[] latLongSplit = markerLocation.split(" ");
            currentLocation = new LatLng(Double.valueOf(latLongSplit[1]), Double.valueOf(latLongSplit[0]));
            mMap.addMarker(new MarkerOptions().position(currentLocation).icon(markerType).title(followeeList.get(i)).snippet(recentMoodEvent.getMoodType().getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
    }

}