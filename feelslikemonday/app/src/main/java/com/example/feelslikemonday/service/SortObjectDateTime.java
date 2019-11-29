package com.example.feelslikemonday.service;

import com.example.feelslikemonday.model.FolloweeMoodEvent;

import java.util.Comparator;

/**
 * This class contains a method that will compare mood events within a mood history
 * It will sort them in reverse chronological order (based on the date and time the mood was posted)
 */
public class SortObjectDateTime implements Comparator<FolloweeMoodEvent> {

    /**
     * This compare two mood event based on the date and time
     *
     * @param s1 This is a candidate mood event 1
     * @param s2 This is a candidate mood event 2
     * @return return 0 if both the strings are equal, positive if the first string is lexicographically greater, negative if the second string is lexicographically greater
     */
    public int compare(FolloweeMoodEvent s1, FolloweeMoodEvent s2) {
        String range1 = s1.getRecentMood().getDate() + s1.getRecentMood().getTime();
        String range2 = s2.getRecentMood().getDate() + s2.getRecentMood().getTime();
        return range2.compareTo(range1);
    }
}
