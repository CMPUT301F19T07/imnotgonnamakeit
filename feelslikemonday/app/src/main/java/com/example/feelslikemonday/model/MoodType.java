package com.example.feelslikemonday.model;

public class MoodType {
    private String name;
    private String emoji;

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
