package com.gooroomee;

public class App {
    public static void main( String[] args ) {
        ClassLoader classLoader = App.class.getClassLoader();
        System.out.println(classLoader);
        System.out.println(classLoader.getParent());
        System.out.println(classLoader.getParent().getParent());
        // push test : switch user
    }
}
