package com.example.feelslikemonday.model;

//import java.io.Serializable;
import android.location.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*The date and time are made as strings since it should be easier to perform operations to get them.
It doesn't seem we perform any operations that doesn't warrant them not to be strings*/
public class MoodEvent{
    public static final int MAX_REASON_LEN = 20;

    //these should be moved to the database, should not he here
    // more dynamic
    // this is for moodtype and mood situtaion

    public static final List<MoodType> MOOD_TYPES = Arrays.asList(
                                                        new MoodType("Anger","\uD83D\uDE20"),
                                                        new MoodType("Disgust","\uD83E\uDD2E"),
                                                        new MoodType("Fear","\uD83D\uDE31"),
                                                        new MoodType("Happiness","☺️"),
                                                        new MoodType("Sadness","\uD83D\uDE22"),
                                                        new MoodType("Surprise","\uD83D\uDE32")
                                                    );
    public static final List<String> SOCIAL_SITUATIONS = Arrays.asList(
                                                        "Alone",
                                                        "With one person",
                                                        "With two to several people",
                                                        "With a crowd"
                                                    );

    //DD/MM/YYYY
    private String date;
    //use 24 hour time: HH:MM AM
    private String time;
    private String emotionalState;
    private String reason;
    private String socialSituation;
    private MoodType moodType;
    private byte[] image;

    //To be attached when editing
    private Location location;

    public MoodEvent(String date, String time, String emotionalState, String reason, MoodType moodType, String socialSituation) {
        if(reason.length()>MAX_REASON_LEN){
            throw new IllegalArgumentException("The reason is longer than "+MAX_REASON_LEN+" characters!");
        }
        this.date = date;
        this.time = time;
        this.emotionalState = emotionalState;
        this.reason = reason;
        this.moodType = moodType;
        this.socialSituation = socialSituation;
    }

    public MoodEvent(String date, String time, String emotionalState, String reason, MoodType moodType) {
        this(date,time,emotionalState,reason,moodType,"");
    }
    public MoodEvent(String date, String time, String emotionalState, MoodType moodType,String socialSituation) {
        this(date,time,emotionalState,"",moodType,socialSituation);
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getEmotionalState() {
        return emotionalState;
    }

    public void setEmotionalState(String emotionalState) {
        this.emotionalState = emotionalState;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        if(reason.length()>MAX_REASON_LEN){
            throw new IllegalArgumentException("The reason is longer than "+MAX_REASON_LEN+" characters!");
        }
        this.reason = reason;
    }

    public String getSocialSituation() {
        return socialSituation;
    }

    public void setSocialSituation(String socialSituation) {
        this.socialSituation = socialSituation;
    }

    public MoodType getMoodType() {
        return moodType;
    }

    public void setMoodType(MoodType moodType) {
        this.moodType = moodType;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
