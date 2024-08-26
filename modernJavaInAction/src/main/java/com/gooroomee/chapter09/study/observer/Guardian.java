package com.gooroomee.chapter09.study.observer;

import com.gooroomee.chapter09.study.strategy.IsTweet;
import com.gooroomee.chapter09.study.strategy.Validator;

public class Guardian implements Observer {
    @Override
    public void notify(String tweet) {
        Validator tweetValidator = new Validator(new IsTweet());

        if (tweetValidator.validate(tweet)) {
            String replace = tweet.replace("tweet@", "");
            System.out.println("Yet more news from London... " + replace);
        }
    }
}
