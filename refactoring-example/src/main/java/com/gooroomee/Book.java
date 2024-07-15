package com.gooroomee;

public class Book {

    private String a = "BOOK-A";
    private static String B = "BOOK-B";
    private static final String C = "BOOK-C";
    public String d = "BOOK-D";
    protected String e = "BOOK-E";

    public Book() {
    }

    public Book(String a, String d, String e) {
        this.a = a;
        this.d = d;
        this.e = e;
    }

    private void f() {
        System.out.println("F");
    }

    public void g() {
        System.out.println("G");
    }

    public int h() {
        return 100;
    }
}
