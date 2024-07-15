package com.gooroomee;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        Class<Book> bookClass = Book.class;

        Book book = new Book();
        Class<? extends Book> getBookClass = book.getClass();

        try {
            Class<?> bookClassForName = Class.forName("com.gooroomee.Book");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 접근제어자 public만 가져옴
        Arrays.stream(bookClass.getFields()).forEach(System.out::println);
        // 다 가져옴
        Arrays.stream(bookClass.getDeclaredFields()).forEach(System.out::println);

        Arrays.stream(bookClass.getDeclaredFields()).forEach(f -> {
            try {
                f.setAccessible(true); // 접근제어자 무시하고 다 가져옴
                System.out.printf("%s %s\n", f, f.get(book));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println(MyBook.class.getSuperclass());
        Arrays.stream(MyBook.class.getInterfaces()).forEach(System.out::println);

        Arrays.stream(Book.class.getDeclaredFields()).forEach(f -> {
            int modifiers = f.getModifiers();
            System.out.println(f);
            System.out.println(Modifier.isPublic(modifiers));
            System.out.println(Modifier.isPublic(modifiers));
            System.out.println(Modifier.isProtected(modifiers));
        });

        Arrays.stream(Book.class.getMethods()).forEach(m -> {
            System.out.println(m);
        });
    }
}
