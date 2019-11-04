package com.example.feelslikemonday.ui.sendrequest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import com.example.feelslikemonday.DAO.FollowRequestCallback;
import com.example.feelslikemonday.DAO.FollowRequestDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.FollowRequest;
import com.example.feelslikemonday.ui.followrequest.FollowerRequestViewModel;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SendRequestFragment extends Fragment {

    private Button resetButton;
    private Button sendButton;
    private EditText usernameEditText;
    private String recipientUsername;
    private List<String> requesterUsernames;
    private FollowRequest followRequest_ok;

    private SendRequestViewModel sendRequestViewModel;
    private static FollowRequestDAO DAO; // = FollowRequestDAO.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sendRequestViewModel = ViewModelProviders.of(this).get(SendRequestViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send_request, container, false);

        resetButton =  root.findViewById(R.id.send_request_reset);
        sendButton =  root .findViewById(R.id.send_request_send);
        usernameEditText =  root .findViewById(R.id.send_request_username);

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

                final CountDownLatch signal = new CountDownLatch(1);
                recipientUsername = usernameEditText.getText().toString();
                //FollowRequest followRequest = new FollowRequest(recipientUsername);  //TODO: sharedPreference??


                DAO = FollowRequestDAO.getInstance();

                //Check Input User exists in the database
                DAO.get(recipientUsername, new FollowRequestCallback(){
                    @Override
                    public void onCallback(FollowRequest followRequest) {
                        if(followRequest.getRecipientUsername().equals(recipientUsername)){
                            requesterUsernames = followRequest.getRequesterUsernames();
                            requesterUsernames.add("leleTest");   //TODO: check you have not request twice.
                            requesterUsernames.add("leleTest1");
                            requesterUsernames.add("leleTest2");
                            requesterUsernames.add("leleTest3");
                            Toast toast = Toast.makeText(getActivity(), "Valid User", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                            followRequest_ok = followRequest;

                            DAO.createOrUpdate(recipientUsername, followRequest_ok, new VoidCallback() {
                                @Override
                                public void onCallback() {
                                    signal.countDown();
                                }
                            });
                            usernameEditText.setText("");

                            Toast toast1 = Toast.makeText(getActivity(), "Send Request Successfully to " + recipientUsername, Toast.LENGTH_LONG);
                            toast1.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast1.show();
                        }

                    }
                }, new VoidCallback() {
                    @Override
                    public void onCallback() {
                        Toast toast = Toast.makeText(getActivity(), "User Not Exist, Invite your friend", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                        usernameEditText.setText("");
                        signal.countDown();
                    }
                });

                //requesterUsernames = followRequest.getRequesterUsernames();
                //requesterUsernames.add("leleTest");
                //requesterUsernames.add("leleTest1");
                //requesterUsernames.add("leleTest2");
                //requesterUsernames.add("leleTest3");
            }
        });
        return root;
    }




    /**
     * Since Login/Register Activity haven't complete, so I use <filename> that I create manually. (which is Xiaole)
     * Once sharedPreference create in the Login/Register Activity, I need to get the variable <file name>
     * from mainActivity and pass the file name in SendRequestFragment --> TODO
     *
     * Login/Register should pass the filename variable to the MainActivity(Home Page).
     */


    private void storeRecipientUsername(String friendName){
        //TODO: pass filename variable from MainActivity
        SharedPreferences mySharedPreferences = this.getActivity().getSharedPreferences("Xiaole", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = mySharedPreferences.edit();
        myEditor.putString("recipientUsername", friendName);
        myEditor.apply();
    }

    private String getRecipientUsername(){
        SharedPreferences mySharedPrefrences = this.getActivity().getSharedPreferences("Xiaole", Context.MODE_PRIVATE);
        //String RecipientUsername = mySharedPrefrences.getString("recipientUsername", this.getActivity().);
        String RecipientUsername = "Xiaole2";
        return RecipientUsername;
    }



}
