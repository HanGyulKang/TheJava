package com.gooroomee;

public class Masulsa {

    // 바이트코드 조작 라이브러리
    // 1. ASM : https://asm.ow2.io/
    // 2. Javassist: https://www.javassist.org/
    // 3. ByteBuddy : https://bytebuddy.net/#/
    // ::: ByteBuddy 추천

    // Masulsa 클래스 실행할 때 MasulsaAgent를 말은 jar 경로를 VM option에 넣어서 실행
    // -javaagent:/Users/gooroomee/inflearn/project/MasulsaAgent/target/MasulsaAgent-1.0-SNAPSHOT.jar
    // 기존 class 코드를 바꾸지 않고 처리 함 (=byte코드에 직접 변화를 주지 않고 메모리에서 가져올 때만 작동하게 함 : 비침투적임)
    public static void main(String... args) {
        System.out.println(new Moja().pullOut());
    }
}
