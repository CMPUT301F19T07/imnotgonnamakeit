package com.example.feelslikemonday.model;

//import java.io.Serializable;

import com.google.firebase.firestore.Blob;

import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible for storing information about a mood event
 */

/*The date and time are made as strings since it should be easier to perform operations to get them.
It doesn't seem we perform any operations that doesn't warrant them not to be strings*/
public class MoodEvent {
    public static final int MAX_REASON_LEN = 20;
    public static final List<MoodType> MOOD_TYPES = Arrays.asList(
            new MoodType("Anger", "\uD83D\uDE20"),
            new MoodType("Disgust", "\uD83E\uDD2E"),
            new MoodType("Fear", "\uD83D\uDE31"),
            new MoodType("Happiness", "\uD83D\uDE03"),
            new MoodType("Sadness", "\uD83D\uDE22"),
            new MoodType("Surprise", "\uD83D\uDE32")
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
    private String location;
    private MoodType moodType;
    private Blob image;

    //Used to deserialize
    public MoodEvent() {
    }

    /**
     * This is a class that keeps track of a mood event
     *
     * @param date            This is the date of the mood event
     * @param time            This is the time of the mood event
     * @param emotionalState  This is the emotional state of user when posting the mood event
     * @param reason          This is the reason why user posts the mood event
     * @param moodType        This is the mood type of the mood event
     * @param socialSituation This is the social situation of the user when posting the mood event
     * @param location        This is the location of the user when posting the mood event
     * @param image           Blob that stores an image relating to moods
     */
    public MoodEvent(String date, String time, String emotionalState, String reason, MoodType moodType, String socialSituation, String location, Blob image) {
        if (reason.length() > MAX_REASON_LEN) {
            throw new IllegalArgumentException("The reason is longer than " + MAX_REASON_LEN + " characters!");
        }
        this.date = date;
        this.time = time;
        this.emotionalState = emotionalState;
        this.reason = reason;
        this.moodType = moodType;
        this.socialSituation = socialSituation;
        this.location = location;
        this.image = image;
    }

    public MoodEvent(String date, String time, String emotionalState, String reason, MoodType moodType, String location) {
        this(date, time, emotionalState, reason, moodType, "", location, null);
    }

    public MoodEvent(String date, String time, String emotionalState, MoodType moodType, String socialSituation, String location) {
        this(date, time, emotionalState, "", moodType, socialSituation, location, null);
    }

    /**
     * this returns the date of the mood event
     *
     * @return return the string(date) of the mood event
     */
    public String getDate() {
        return date;
    }

    /**
     * this returns the time of the mood event
     *
     * @return return the string(time) of the mood event
     */
    public String getTime() {
        return time;
    }

    /**
     * this returns the emotional state of the mood event
     *
     * @return return the string(emotional state) of the mood event
     */
    public String getEmotionalState() {
        return emotionalState;
    }

    /**
     * this sets up the emotinal state for the mood event
     *
     * @param emotionalState This is the emotional state of user when posting the mood event
     */
    public void setEmotionalState(String emotionalState) {
        this.emotionalState = emotionalState;
    }

    /**
     * this returns the reason of my mood event
     *
     * @return return the reason of this mood event
     */
    public String getReason() {
        return reason;
    }

    /**
     * this sets up the reason of this mood event
     *
     * @param reason This is the reason why user posts the mood event
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * this returns the social situation of the mood event
     *
     * @return return the social situation of the mood event
     */
    public String getSocialSituation() {
        return socialSituation;
    }

    /**
     * this sets up the social situation of the current mood event
     *
     * @param socialSituation This is the emotional state of user when posting the mood event
     */
    public void setSocialSituation(String socialSituation) {
        this.socialSituation = socialSituation;
    }

    /**
     * this returns the type of the mood
     *
     * @return return the type of the mood event
     */
    public MoodType getMoodType() {
        return moodType;
    }

    /**
     * this sets up the mood type of the mood event
     *
     * @param moodType This is the mood type of the mood event
     */
    public void setMoodType(MoodType moodType) {
        this.moodType = moodType;
    }

    /**
     * this returns the image of the mood event
     *
     * @return return the image of the mood event
     */
    public Blob getImage() {
        return image;
    }

    /**
     * this sets up the image from the mood event
     *
     * @param image This is the image of the mood event
     */
    public void setImage(Blob image) {
        this.image = image;
    }

    /**
     * this returns this geo location of the mood event
     *
     * @return return the location of the mood event
     */
    public String getLocation() {
        return location;
    }

    /**
     * this sets up the geo location of the mood event
     *
     * @param location This is the location of the user when posting the mood event
     */
    public void setLocation(String location) {
        this.location = location;
    }
}
