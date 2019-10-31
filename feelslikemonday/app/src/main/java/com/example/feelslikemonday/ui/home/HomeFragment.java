package com.example.feelslikemonday.ui.home;

import android.content.Intent;
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
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.EmotionBookAdapter;
import com.example.feelslikemonday.model.MoodEvent;
import com.example.feelslikemonday.model.MoodType;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private SwipeMenuListView SwipeMenuListView;
    private ArrayList<MoodEvent> myEmotionList = new ArrayList<>(); // the main Ride that handles all rides
    private EmotionBookAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {




        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {


            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //fill Dummy Data here
        MoodType myType;
        MoodEvent mymood;

        myType = new MoodType("Anger","\uD83D\uDE20");
       mymood = new MoodEvent("2000","00:00", "Anger", "No",  myType, "noSocial");
       myEmotionList.add(mymood);

        myType =      new MoodType("Disgust","\uD83E\uDD2E");
        mymood = new MoodEvent("2001","01:01", "Disgust", "No",  myType, "yesSocial");
        myEmotionList.add(mymood);

        myType = new MoodType("Fear","\uD83D\uDE31");
        mymood = new MoodEvent("2002","02:02", "Fear", "No",  myType, "noSocial");
        myEmotionList.add(mymood);


        myType = new MoodType("Surprise","\uD83D\uDE32");
        mymood = new MoodEvent("2003","03:03", "Surprise", "No",  myType, "noSocial");
        myEmotionList.add(mymood);


        // end fill Dummy Data

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
                openItem.setTitle("View");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
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
                        // Delete
                        removeEmotion(view, position);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });



    }

    public  void removeEmotion(@NonNull View view, int index){
        SwipeMenuListView = view.findViewById(R.id.listView);
        myEmotionList.remove(index);
        adapter = new EmotionBookAdapter(getContext(), R.layout.list_adapter_view, myEmotionList);
        SwipeMenuListView.setAdapter(adapter);

        // Send here the new UserObjecct to the DB
    }

    public  void viewEmotion(@NonNull View view, int index){

        MoodEvent CurrentModeEvent = myEmotionList.get(index);

        Intent intent = new Intent(getContext(), DisplayCurrentMood.class);

    //    String moodType = CurrentModeEvent.getMoodType().getEmoji();
  //      byte[] image = CurrentModeEvent.getImage();

        intent.putExtra("myDate", CurrentModeEvent.getDate());
        intent.putExtra("mytime", CurrentModeEvent.getTime());
        intent.putExtra("emotionalState", CurrentModeEvent.getEmotionalState());
        intent.putExtra("reason", CurrentModeEvent.getReason());
        intent.putExtra("socialSituation", CurrentModeEvent.getSocialSituation());
        intent.putExtra("moodType", CurrentModeEvent.getMoodType().getEmoji());

    startActivity(intent);




    }
}
