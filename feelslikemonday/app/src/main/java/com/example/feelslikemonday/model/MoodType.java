package com.example.feelslikemonday.model;

/**
 * this is  class that show the different mood type
 */
public class MoodType {
    private String name;
    private String emoji;

    public MoodType(){}

    public MoodType(String name, String emoji) {
        this.name = name;
        this.emoji = emoji;
    }

    /**
     * this return the name of the mood type that user choose
     * @return
     *   return the string of mood type
     */
    public String getName() {
        return name;
    }

    /**
     * this retun the corresponding emoji name for the mood type user choose
     * @return
     *     return the string of emoji name
     */
    public String getEmoji() {
        return emoji;
    }
}
