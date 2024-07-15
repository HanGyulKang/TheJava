package com.gooroomee;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;

import java.io.File;
import java.io.IOException;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class Masulsa {

    // 바이트코드 조작 라이브러리
    // 1. ASM : https://asm.ow2.io/
    // 2. Javassist: https://www.javassist.org/
    // 3. ByteBuddy : https://bytebuddy.net/#/
    // ::: ByteBuddy 추천
    public static void main(String... args) {
        try {
            new ByteBuddy().redefine(Moja.class)
                           .method(named("pullOut"))
                           .intercept(FixedValue.value("Rabbit!"))
                           .make()
                           .saveIn(new File("/Users/gooroomee/inflearn/project/classloadersample/target/classes/"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(new Moja().pullOut());
    }
}
