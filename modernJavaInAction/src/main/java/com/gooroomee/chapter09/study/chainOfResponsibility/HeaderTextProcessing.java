package com.gooroomee.chapter09.study.chainOfResponsibility;

public class HeaderTextProcessing extends ProcessingObject<String> {

    @Override
    protected String handleWork(String text) {
        return "From bong : " + text;
    }
}
