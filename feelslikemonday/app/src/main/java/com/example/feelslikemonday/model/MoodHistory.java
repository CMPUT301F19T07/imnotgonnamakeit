package com.example.feelslikemonday.model;
import java.util.ArrayList;
import java.util.List;

//The reason why the MoodHistory is an object is so that operations such as sorting and any
// aggregate operations can be performed here. Should be useful for sorting or filtering.
//This object composition may be overkill, and might be able extend the ArrayList class.

//Todo: Extend the ArrayList class?
public class MoodHistory {
    private List<MoodEvent> list;
    public MoodHistory() {
        this.list = new ArrayList<>();
    }

    public List<MoodEvent> getList() {
        return list;
    }

    public MoodEvent getMoodEvent(int i){
        return list.get(i);
    }

    public void editMoodEvent(int i, MoodEvent newMoodEvent){
        list.set(i,newMoodEvent);
    }

    public void deleteMoodEvent(int i){
        list.remove(i);
    }

    public void addMoodEvent(MoodEvent moodEvent){
        list.add(moodEvent);
    }
}
