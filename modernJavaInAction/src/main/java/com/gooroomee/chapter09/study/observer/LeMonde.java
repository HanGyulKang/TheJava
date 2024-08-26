package com.gooroomee.chapter09.study.observer;

import com.gooroomee.chapter09.study.strategy.IsTweet;
import com.gooroomee.chapter09.study.strategy.Validator;

public class LeMonde implements Observer {
    @Override
    public void notify(String tweet) {
        Validator tweetValidator = new Validator(new IsTweet());

        if (tweetValidator.validate(tweet)) {
            String replace = tweet.replace("tweet@", "");
            System.out.println("Today cheese, wine and new!! " + replace);
        }
    }
}
