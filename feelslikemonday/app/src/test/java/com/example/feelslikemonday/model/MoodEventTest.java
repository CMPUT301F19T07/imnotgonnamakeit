package com.example.feelslikemonday.model;

import org.junit.Test;

public class MoodEventTest {
    @Test
    public void canConstruct() {
        MoodEvent moodEvent = new MoodEvent("30/02/2019","23:22", "Sad", "Cat died", MoodEvent.MOOD_TYPES.get(0), MoodEvent.SOCIAL_SITUATIONS.get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void canThrowReasonLengthError() {
        String longString = "VeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVery Long Reason, this should throw an exception";
        MoodEvent moodEvent = new MoodEvent("30/02/2019","23:22", "Sad", longString, MoodEvent.MOOD_TYPES.get(0), MoodEvent.SOCIAL_SITUATIONS.get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void canThrowReasonLengthErrorOnSet() {
        String longString = "VeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVery Long Reason, this should throw an exception";
        MoodEvent moodEvent = new MoodEvent("30/02/2019","23:22", "Sad", "Dog died", MoodEvent.MOOD_TYPES.get(0), MoodEvent.SOCIAL_SITUATIONS.get(0));
        moodEvent.setReason(longString);
    }


}
