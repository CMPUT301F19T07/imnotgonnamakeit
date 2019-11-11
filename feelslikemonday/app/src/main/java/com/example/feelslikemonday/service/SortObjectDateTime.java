package com.example.feelslikemonday.service;

import com.example.feelslikemonday.model.followeeMoodEvent;
import java.util.Comparator;

public class SortObjectDateTime implements Comparator<followeeMoodEvent>
{
    public int compare(followeeMoodEvent s1, followeeMoodEvent s2)
    {
        String range1 = s1.getRecentMood().getDate()+ s1.getRecentMood().getTime();
        String range2 = s2.getRecentMood().getDate()+ s2.getRecentMood().getTime();
        return range2.compareTo(range1);
        
    }
}
