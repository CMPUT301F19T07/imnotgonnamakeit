package com.example.feelslikemonday.ui.followrequest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.feelslikemonday.DAO.FollowRequestCallback;
import com.example.feelslikemonday.DAO.FollowRequestDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.FollowRequest;
import com.example.feelslikemonday.ui.followrequest.ArraryAdapter.RequestList;
import com.example.feelslikemonday.ui.login.SignupActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is resposible for receiving feedback on the follower requests
 */
public class FollowerRequestFragment extends Fragment {
    private ListView userList;
    private ArrayAdapter<String> userAdapter;
    private List<String> requesterUsernames;
    private static FollowRequestDAO DAO;
    private SharedPreferences pref;
    private String myUserID;
    private FollowerRequestViewModel followerViewModel;

    /**
     * This initializes follower request fragment
     *
     * @param inflater           This is a layoutInflater object that can be used to inflate any views in the fragment
     * @param container          This is a parent view that the fragment's UI should be attached to
     * @param savedInstanceState This is a previous saved state.
     * @return return the View for the fragment's UI, or null
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        followerViewModel = ViewModelProviders.of(this).get(FollowerRequestViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_follower_request, container, false);
        userList = (ListView) root.findViewById(R.id.request_username_list);
        pref = getActivity().getApplicationContext().getSharedPreferences(SignupActivity.PREFS_NAME, 0);
        myUserID = pref.getString(SignupActivity.USERNAME_KEY, null);

        DAO = FollowRequestDAO.getInstance();

        DAO.get(myUserID, new FollowRequestCallback() {
            @Override
            public void onCallback(FollowRequest followRequest) {
                if (followRequest.getRecipientUsername().equals(myUserID)) {

                    requesterUsernames = followRequest.getRequesterUsernames();
                    userAdapter = new RequestList(getContext(), new ArrayList<String>(requesterUsernames));
                    userList.setAdapter(userAdapter);
                    userAdapter.notifyDataSetChanged();

                }

            }
        }, new VoidCallback() {
            @Override
            public void onCallback() {
                Toast toast = Toast.makeText(getActivity(), "User does not exist", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        });

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("testListView", "click");
                //TODO: show the detail of the requester
            }
        });


        followerViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //do stuff here
            }
        });


        return root;
    }

}


