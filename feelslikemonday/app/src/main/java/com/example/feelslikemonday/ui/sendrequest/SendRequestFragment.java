package com.example.feelslikemonday.ui.sendrequest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.feelslikemonday.DAO.FollowPermissionCallback;
import com.example.feelslikemonday.DAO.FollowPermissionDAO;
import com.example.feelslikemonday.DAO.FollowRequestCallback;
import com.example.feelslikemonday.DAO.FollowRequestDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.FollowPermission;
import com.example.feelslikemonday.model.FollowRequest;
import com.example.feelslikemonday.ui.login.SignupActivity;

import java.util.List;

/**
 * This fragment is responsible for  sending request to the user that you want to follow
 */
public class SendRequestFragment extends Fragment {

    private Button resetButton;
    private Button sendButton;
    private EditText usernameEditText;
    private String recipientUsername;
    private List<String> requesterUsernames;
    private FollowRequest followRequest_ok;
    private SharedPreferences pref;
    private String myUserID;

    private SendRequestViewModel sendRequestViewModel;
    private static FollowRequestDAO followRequestDAO; // = FollowRequestDAO.getInstance();
    private static FollowPermissionDAO followPermissionDAO;

    /**
     * This initializes SendRequestFragment
     *
     * @param inflater           This is a layoutInflater object that can be used to inflate any views in the fragment
     * @param container          This is a parent view that the fragment's UI should be attached to
     * @param savedInstanceState This is a previous saved state.
     * @return return the View for the fragment's UI or null
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sendRequestViewModel = ViewModelProviders.of(this).get(SendRequestViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send_request, container, false);

        resetButton = root.findViewById(R.id.send_request_reset);
        sendButton = root.findViewById(R.id.send_request_send);
        usernameEditText = root.findViewById(R.id.send_request_username);
        pref = getActivity().getApplicationContext().getSharedPreferences(SignupActivity.PREFS_NAME, 0);
        myUserID = pref.getString(SignupActivity.USERNAME_KEY, null);

        sendRequestViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //do stuff here
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameEditText.setText("");
            }
        });

        usernameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //https://developer.android.com/guide/topics/ui/notifiers/toasts.html
        sendButton.setOnClickListener(new View.OnClickListener() {
            //TODO: pass filename(requesterUsername) variable from MainActivity

            @Override
            public void onClick(View view) {

                recipientUsername = usernameEditText.getText().toString();
                if (recipientUsername.matches("")) {
                    Toast toast = Toast.makeText(getActivity(), "Please input a username", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                } else if (recipientUsername.matches(myUserID)) {
                    Toast toast = Toast.makeText(getActivity(), "Error: You cannot send a request to yourself", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    usernameEditText.setText("");
                } else {
                    followRequestDAO = FollowRequestDAO.getInstance();
                    followPermissionDAO = FollowPermissionDAO.getInstance();

                    followRequestDAO.get(recipientUsername, new FollowRequestCallback() {
                        @Override
                        public void onCallback(FollowRequest followRequest) {
                            if (followRequest.getRecipientUsername().equals(recipientUsername)) {
                                //showValidUserToast();
                                followPermissionDAO.get(myUserID, new FollowPermissionCallback() {
                                    @Override
                                    public void onCallback(FollowPermission followPermission) {
                                        if (followPermission.getFolloweeUsernames().contains(recipientUsername)) {
                                            showAlreadyFollowedUserToast();
                                        } else {
                                            followRequestDAO.get(recipientUsername, new FollowRequestCallback() {
                                                @Override
                                                public void onCallback(FollowRequest followRequest) {
                                                    if (followRequest.getRequesterUsernames().contains(myUserID)) {
                                                        showAlreadySentRequestToast();
                                                    } else {
                                                        followRequest_ok = followRequest;
                                                        requesterUsernames = followRequest.getRequesterUsernames();
                                                        requesterUsernames.add(myUserID);
                                                        followRequestDAO.createOrUpdate(recipientUsername, followRequest_ok, new VoidCallback() {
                                                            @Override
                                                            public void onCallback() {
                                                            }
                                                        });
                                                        showSuccessfulSendToast();
                                                    }

                                                }
                                            }, null);
                                        }
                                    }
                                }, null);
                            }

                        }
                    }, new VoidCallback() {
                        @Override
                        public void onCallback() {
                            showIndvalidUserToast();
                        }
                    });

                }
            }
        });
        return root;
    }

    /**
     * This is a method that shows the message when the recipient is followed
     */
    private void showAlreadyFollowedUserToast() {
        Toast toast = Toast.makeText(getActivity(), "You have already followed " + recipientUsername, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
        usernameEditText.setText("");
    }

    /**
     * This is a method that shows the message when the recipient is sent request
     */
    private void showAlreadySentRequestToast() {
        Toast toast = Toast.makeText(getActivity(), "Request already sent to " + recipientUsername, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
        usernameEditText.setText("");
    }

    /**
     * This is a method that shows the message when the recipient is a valid user
     */
    private void showValidUserToast() {
        Toast toast = Toast.makeText(getActivity(), "Valid User", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    /**
     * This is a method that shows the message when the recipient is not a valid user
     */
    public void showIndvalidUserToast() {
        Toast toast = Toast.makeText(getActivity(), "This user does not exist, invite your friends", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
        usernameEditText.setText("");
    }

    /**
     * This is a method that shows the message when user send request successfully to the recipient
     */
    public void showSuccessfulSendToast() {
        Toast toast1 = Toast.makeText(getActivity(), "Sent Request Successfully to " + recipientUsername, Toast.LENGTH_LONG);
        toast1.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast1.show();
        usernameEditText.setText("");
    }
}
