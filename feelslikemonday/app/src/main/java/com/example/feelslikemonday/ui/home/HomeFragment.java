package com.example.feelslikemonday.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.feelslikemonday.DAO.UserCallback;
import com.example.feelslikemonday.DAO.UserDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.MoodEvent;
import com.example.feelslikemonday.model.User;
import com.example.feelslikemonday.ui.login.SignupActivity;
import com.example.feelslikemonday.ui.moods.AddNewMoodActivity;
import com.google.firebase.firestore.Blob;

import java.util.ArrayList;
import java.util.List;

/*Responsible for listing all user's moods and related information */
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private User currentUser;
    private SwipeMenuListView SwipeMenuListView;
    private List<MoodEvent> myCurrentMoodList;
    private ArrayList<MoodEvent> myEmotionList = new ArrayList<>();
    private EmotionBookAdapter adapter;
    private SharedPreferences pref;
    private String myUserID;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) { }});
        root.setBackgroundColor(Color.GREEN);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        pref = getActivity().getApplicationContext().getSharedPreferences(SignupActivity.PREFS_NAME, 0);
        myUserID = pref.getString(SignupActivity.USERNAME_KEY,null);

        UserDAO userDAO = new UserDAO();
        userDAO.get(myUserID, new UserCallback() {
            @Override
            public void onCallback(User user) {
                // current user the is object for the login user
                currentUser = user;
                myEmotionList.clear();
                myCurrentMoodList = currentUser.getMoodHistory();
                for(int i = 0; i < myCurrentMoodList.size(); i++) {
                    myEmotionList.add(myCurrentMoodList.get(i));
                }
                SwipeMenuListView = getView().findViewById(R.id.listView);
                adapter = new EmotionBookAdapter(getContext(), R.layout.list_adapter_view, myEmotionList);
                SwipeMenuListView.setBackgroundColor(Color.YELLOW);
                SwipeMenuListView.setAdapter(adapter);

            }
        }, new VoidCallback() {
            @Override
            public void onCallback() { }
        });
    } // end of onResume

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pref = getActivity().getApplicationContext().getSharedPreferences(SignupActivity.PREFS_NAME, 0);
        myUserID = pref.getString(SignupActivity.USERNAME_KEY,null);

        UserDAO userDAO = new UserDAO();
        userDAO.get(myUserID, new UserCallback() {
            @Override
            public void onCallback(User user) { //temporarily using User.myTempUserName until login stuff is done

                currentUser = user;
                myEmotionList.clear();
                myCurrentMoodList = currentUser.getMoodHistory();
                for(int i = 0; i < myCurrentMoodList.size(); i++) {
                    myEmotionList.add(myCurrentMoodList.get(i));
                }
                SwipeMenuListView = view.findViewById(R.id.listView);
                adapter = new EmotionBookAdapter(getContext(), R.layout.list_adapter_view, myEmotionList);
                SwipeMenuListView.setAdapter(adapter);

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

                        // create "Edit" item
                        SwipeMenuItem editItem = new SwipeMenuItem(
                                getContext());
                        // set item background
                        editItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                                0xCE)));
                        // set item width
                        editItem.setWidth(170);
                        // set item title
                        editItem.setTitle("Edit");
                        // set item title fontsize
                        editItem.setTitleSize(18);
                        // set item title font color
                        editItem.setTitleColor(Color.WHITE);
                        // add to menu
                        menu.addMenuItem(editItem);

                        // delete
                        SwipeMenuItem deleteItem = new SwipeMenuItem(
                                getContext());
                        // set item background
                        deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                                0x3F, 0x25)));
                        // set item width
                        deleteItem.setWidth(170);
                        // set a icon
                        deleteItem.setIcon(R.drawable.ic_delete);
                        // add to menu
                        menu.addMenuItem(deleteItem);
                    }
                };
                SwipeMenuListView.setMenuCreator(creator);
                SwipeMenuListView.setOnMenuItemClickListener(new com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                        switch (index) {
                            case 0:
                                // view
                                viewEmotion(view, position);
                                break;
                            case 1:
                                // view
                                editEmotion(view, position);
                                break;

                            case 2:
                                // Delete
                                removeEmotion(view, position);
                                break;
                        }
                        // false : close the menu; true : not close the menu
                        return false;
                    }
                });
            }
        }, new VoidCallback() {
            @Override
            public void onCallback() { }
        });
    }

    public void removeEmotion(@NonNull View view, int index){
        List<MoodEvent> moodHistoryTempTemp = currentUser.getMoodHistory();
        SwipeMenuListView = view.findViewById(R.id.listView);
        myEmotionList.remove(index);
        moodHistoryTempTemp.remove(index);
        UserDAO userDAO = new UserDAO();
        userDAO.createOrUpdate(currentUser,new VoidCallback(){
            @Override
            public void onCallback() { }
        });

        adapter = new EmotionBookAdapter(getContext(), R.layout.list_adapter_view, myEmotionList);
        SwipeMenuListView.setAdapter(adapter);
        // Send the new UserObjecct to the DB
    }

    public void viewEmotion(@NonNull View view, int index){

        MoodEvent currentMoodEvent = myEmotionList.get(index);
        Intent intent = new Intent(getContext(), DisplayCurrentMood.class);

        //If an image is set, pass the image in the form of a byte array
        Blob imageBlob = currentMoodEvent.getImage();
        byte[] imageByteArr = null;
        if(imageBlob != null){
            imageByteArr = imageBlob.toBytes();
        }

        intent.putExtra("image", imageByteArr);
        intent.putExtra("myDate", currentMoodEvent.getDate());
        intent.putExtra("mytime", currentMoodEvent.getTime());
        intent.putExtra("emotionalState", currentMoodEvent.getEmotionalState());
        intent.putExtra("reason", currentMoodEvent.getReason());
        intent.putExtra("socialSituation", currentMoodEvent.getSocialSituation());
        intent.putExtra("moodType", currentMoodEvent.getMoodType().getEmoji());

        startActivity(intent);
    }

    public void editEmotion(@NonNull View view, int index){

        MoodEvent currentMoodEvent = myEmotionList.get(index);

        Intent intent = new Intent(getContext(), AddNewMoodActivity.class);

        int indexSocial = getCurrentSocialIndex(currentMoodEvent.getSocialSituation());
        int indexMood = getCurrentMoodIndex(currentMoodEvent.getMoodType().getName());

        //If an image is set, pass the image in the form of a byte array
        Blob imageBlob = currentMoodEvent.getImage();
        byte[] imageByteArr = null;
        if(imageBlob != null){
            imageByteArr = imageBlob.toBytes();
        }

        intent.putExtra("image", imageByteArr);
        intent.putExtra("myDate", currentMoodEvent.getDate());
        intent.putExtra("mytime", currentMoodEvent.getTime());
        intent.putExtra("emotionalState", currentMoodEvent.getEmotionalState());
        intent.putExtra("reason", currentMoodEvent.getReason());
        intent.putExtra("socialSituation", indexSocial);
        intent.putExtra("moodTypeName", indexMood);
        intent.putExtra("state", 1);
        intent.putExtra("stateIndex", index);

        startActivity(intent);
    }

    public int getCurrentSocialIndex(String social)
    {
        int returnValue = 0;
        for (int i = 0; i < MoodEvent.SOCIAL_SITUATIONS.size(); i++) {
            if (social.equals(MoodEvent.SOCIAL_SITUATIONS.get(i))) {
                returnValue = i;
            }
        }
        return returnValue;
    }

    public int getCurrentMoodIndex(String mood) {
        int returnValue = 0;
        for (int i = 0; i < MoodEvent.MOOD_TYPES.size(); i++) {
            if (mood.equals(MoodEvent.MOOD_TYPES.get(i).getName())) {
                returnValue = i;
            }
        }
        return returnValue;
    }
}
