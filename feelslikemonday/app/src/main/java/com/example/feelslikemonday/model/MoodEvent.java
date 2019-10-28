package com.example.feelslikemonday.model;

import android.location.Location;

//The date and time are made as strings since it should be easier to perform operations to get them.
//It doesn't seem we perform any operations that doesn't warrant them not to be strings
public class MoodEvent {
    private final int MAX_REASON_LEN = 20;

    private String date;
    private String time;
    private String emotionalState;
    private String reason;
    private String socialSituation;
    private MoodType moodType;
    private byte[] image;

    //To be attached when editing
    private Location location;

    public MoodEvent(String date, String time, String emotionalState, String reason, MoodType moodType, String socialSituation) {
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
