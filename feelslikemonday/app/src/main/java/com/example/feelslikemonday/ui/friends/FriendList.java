package com.example.feelslikemonday.ui.friends;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.feelslikemonday.DAO.FollowPermissionCallback;
import com.example.feelslikemonday.DAO.FollowPermissionDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.FollowPermission;
import com.example.feelslikemonday.ui.login.SignupActivity;

import java.util.ArrayList;
import java.util.List;

/**
 *This class acts as a array adapter for the following friends list
 */
public class FriendList extends ArrayAdapter<String> {

    private Context context;
    private List<String> friendsUsernames;
    private static FollowPermissionDAO followPermissionDAO;
    private SharedPreferences pref;
    private String myUserID;

    /**
     * This constructor initializes following friends fragment
     * @param context
     * This is the current context. This value must never be null
     * @param users
     * This is the objects to represent in the ListView. This value must never be null
     */
    public FriendList(@NonNull Context context, ArrayList<String> users) {
        super(context, 0, users);
        this.friendsUsernames = users;
        this.context = context;
    }

    /**
     * This gets a View that displays the data at the specified position in the data set
     * @param position
     * This is the position of the item within the adapter's data set of the item whose view we want
     * @param convertView
     * This is the view. This value must never be null
     * @param parent
     * This is the view group. This value must never be null
     * @return
     *  return a View of following friends corresponding to the data at the specified position
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        followPermissionDAO = FollowPermissionDAO.getInstance();
        pref = getContext().getSharedPreferences(SignupActivity.PREFS_NAME, 0);
        myUserID = pref.getString(SignupActivity.USERNAME_KEY,null);

        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content_follow_friends, parent, false);
        }
        final String friendUsername = friendsUsernames.get(position);

        TextView userName = view.findViewById(R.id.following_friend);
        userName.setText(friendUsername);

        Button unfollowButton = view.findViewById(R.id.unfollow_button);

        unfollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                followPermissionDAO.get(myUserID, new FollowPermissionCallback(){
                    @Override
                    public void onCallback(FollowPermission followPermission) {
                        if(followPermission.getFollowerUsername().equals(myUserID)){
                            friendsUsernames = followPermission.getFolloweeUsernames();
                            friendsUsernames.remove(friendUsername);

                            followPermissionDAO.createOrUpdate(myUserID, followPermission, new VoidCallback() {
                                @Override
                                public void onCallback() {
                                }
                            });

                            FriendList.this.remove(friendUsername);
                        }

                    }
                }, null);
            }
        });



        return view;
    }
}
