package com.example.feelslikemonday.ui.following;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.feelslikemonday.DAO.FollowPermissionCallback;
import com.example.feelslikemonday.DAO.FollowPermissionDAO;
import com.example.feelslikemonday.DAO.UserCallback;
import com.example.feelslikemonday.DAO.UserDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.FollowPermission;
import com.example.feelslikemonday.model.MoodEvent;
import com.example.feelslikemonday.model.User;
import com.example.feelslikemonday.model.followeeMoodEvent;
import com.example.feelslikemonday.ui.home.DisplayCurrentMood;
import com.example.feelslikemonday.ui.login.SignupActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

/*Responsible for listing all followers' moods and related information */
public class FollowingFragment extends Fragment {

    private FollowingViewModel followingViewModel;
    private SwipeMenuListView userList;
    private FolloweeBookAdapter adapter;
    private List<String> followeeUsernames;
    private static FollowPermissionDAO DAO;
    private SharedPreferences pref;
    private String myUserID;
    private List<MoodEvent> foloweeUserMoodList;
    private ArrayList<followeeMoodEvent> myfolloweeList = new ArrayList<>();
    private int xIteration;
    private int userVisited = 0;
    private String  userCurrent;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        followingViewModel = ViewModelProviders.of(this).get(FollowingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_following, container, false);
        followingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //do stuff here
                pref = getActivity().getApplicationContext().getSharedPreferences(SignupActivity.PREFS_NAME, 0);
                myUserID = pref.getString(SignupActivity.USERNAME_KEY,null);
                userList = getView().findViewById(R.id.followee_list);


                DAO = FollowPermissionDAO.getInstance();
                DAO.get(myUserID, new FollowPermissionCallback(){
                    @Override
                    public void onCallback(FollowPermission followPermission) {
                        if(followPermission.getFollowerUsername().equals(myUserID)){

                            followeeUsernames = followPermission.getFolloweeUsernames();
                            // From here read the information for each foloowee
                            UserDAO userDAO = new UserDAO();

                            for (xIteration=0; xIteration<followeeUsernames.size(); xIteration++)
                             {
                                 userCurrent = followeeUsernames.get(xIteration);
                                 userDAO.get(userCurrent, new UserCallback() {
                                    @Override
                                    public void onCallback(User user) {
                                        // current user the is object for the login user
                                        foloweeUserMoodList = user.getMoodHistory();
                                        //foloweeUserMoodList.get(0).
                                        if (foloweeUserMoodList.size() > 0) {
                                            MoodEvent recentMood = user.getMoodHistory().get(0);
                                            //followeeMoodEvent userlatestMood = new followeeMoodEvent(user.getUsername(), foloweeUserMoodList.get(0).getDate(), foloweeUserMoodList.get(0).getTime(), foloweeUserMoodList.get(0).getMoodType());
                                            followeeMoodEvent userlatestMood = new followeeMoodEvent(user.getUsername(), recentMood);
                                            myfolloweeList.add(userlatestMood);
                                        }
                                        userVisited = userVisited + 1;
                                        if (userVisited == followeeUsernames.size())
                                        {
                                            // done reading  all set the adapter

                                       //     Collections.sort(myfolloweeList,followeeMoodEvent);
// https://www.thejavaprogrammer.com/sort-arraylist-objects-java/

                                            adapter = new FolloweeBookAdapter(getContext(), R.layout.followee_adapter_view, myfolloweeList);
                                            userList.setAdapter(adapter);

                                            SwipeMenuCreator creator = new SwipeMenuCreator() {

                                                @Override
                                                public void create(SwipeMenu menu) {
                                                    // create "open" item
                                                    SwipeMenuItem openItem = new SwipeMenuItem(
                                                            getContext());
                                                    // set item background
                                                    openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                                                            0xCE)));
                                                    // set item width
                                                    openItem.setWidth(170);
                                                    // set item title
                                                    openItem.setTitle("View");  //IMPORTANT: this is not the final draft of how the view will look, I will make it look nicer
                                                    // set item title fontsize
                                                    openItem.setTitleSize(18);
                                                    // set item title font color
                                                    openItem.setTitleColor(Color.WHITE);
                                                    // add to menu
                                                    menu.addMenuItem(openItem);


                                                                                              }
                                            }; // end of creator

                                            userList.setMenuCreator(creator);
                                            userList.setOnMenuItemClickListener(new com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener() {
                                                @Override
                                                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                                                    switch (index) {
                                                        case 0:
                                                            // view
                                                            viewEmotion(position);
                                                            break;

                                                    }
                                                    // false : close the menu; true : not close the menu
                                                    return false;
                                                }
                                            });


                                        }
                                    }
                                }, new VoidCallback() {
                                    @Override
                                    public void onCallback() {
                                    }
                                });
                            }

                            // until here each users
                        }

                    }
                }, new VoidCallback() {
                    @Override
                    public void onCallback() {
                        Toast toast = Toast.makeText(getActivity(), "User Not Exist in database(CHECK Login Activity)", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }
                });

            }
        });
        return root;
    }
    public void viewEmotion(int position) {
        MoodEvent CurrentMoodEvent = myfolloweeList.get(position).getRecentMood();
        Intent intent = new Intent(getContext(), DisplayCurrentMood.class);

        intent.putExtra("followeeUsername", myfolloweeList.get(position).getUsername());
        //intent.putExtra("myDate", CurrentMoodEvent.getDate());
        intent.putExtra("myDate", CurrentMoodEvent.getDate());
        intent.putExtra("mytime", CurrentMoodEvent.getTime());
        intent.putExtra("emotionalState", CurrentMoodEvent.getEmotionalState());
        intent.putExtra("reason", CurrentMoodEvent.getReason());
        intent.putExtra("socialSituation", CurrentMoodEvent.getSocialSituation());
        intent.putExtra("moodType", CurrentMoodEvent.getMoodType().getEmoji());
        startActivity(intent);

    }
    }
