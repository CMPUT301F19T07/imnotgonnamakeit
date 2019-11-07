package com.example.feelslikemonday.model;

/*
*This class represents the type of moods that are present in the app.
*/
public class MoodType {
    private String name;
    private String emoji;

    public MoodType(){}

    public MoodType(String name, String emoji) {
        this.name = name;
        this.emoji = emoji;
    }

    public String getName() {
        return name;
    }

    public String getEmoji() {
        return emoji;
    }
}
