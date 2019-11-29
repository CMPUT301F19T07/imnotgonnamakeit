package com.example.feelslikemonday.model;

/**
 * This model class represents the moods events that can be created by a user
 */

public class MoodType {
    private String name;
    private String emoji;

    /**
     * This empty constructor allows Firebase to deserialize an object
     */
    public MoodType() {
    }

    /**
     * This is a class that keeps track of a mood type
     * @param name  This is a candidate mood type
     * @param emoji This is the candidate emoji
     */
    public MoodType(String name, String emoji) {
        this.name = name;
        this.emoji = emoji;
    }

    /**
     * This returns the name of the mood type that user choose
     * @return return the string of mood type
     */
    public String getName() {
        return name;
    }

    /**
     * This returns the corresponding emoji name for the mood type user choose
     * @return return the string of emoji name
     */
    public String getEmoji() {
        return emoji;
    }
}
