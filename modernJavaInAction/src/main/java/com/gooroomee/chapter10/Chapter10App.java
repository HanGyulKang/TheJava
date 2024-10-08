package com.gooroomee.chapter10;

/**
 * 람다를 이용한 도메인 전용 언어(DSL : domain-specific languages)
 * <br>
 * {@link com.gooroomee.chapter10.Chapter10AppTest}
 */
public class Chapter10App {

    /**
     * 1. 도메인 전용 언어(DSL : domain-specific languages)란 무엇이며 어떤 형식으로 구성되는가?
     * 2. DSL을 API에 추가할 때의 장단점
     * 3. JVM에서 활용할 수 있는 자바 기반 DSL을 깔끔하게 만드는 대안
     * 4. 최신 자바 인터페이스와 클래스에 적용된 DSL 에서 배움
     * 5. 효과적인 자바 기반 DSL을 구현하는 패턴과 기법
     * 6. 이들 패턴을 자바 라이브러리와 도구에서 얼마나 흔히 사용하는가?
     *
     * ========================================================
     * @DSL 이란 특정 비즈니스 도메인을 인터페이스로 만든 API 라고 생각할 수 있다.
     * @DSL의 특징
     *      1. 의사 소통의 왕 : 명확한 의사전달, 프로그래머가 아닌 사람도 이해할 수 있어야함. 코드가 비즈니스 요구사항에 부합하는지 확인할 수 있음
     *      2. 한 번 코드를 구현하지만 여러 번 읽는다 : 가독성
     *
     * ================================
     * 대표적으로 만날 수 있는게 QueryDSL이다!!!!!!
     * ================================
     *
     * Stream interface : 데이터 리스트를 조작하는 DSL
     * Collector interface : 데이터 수집을 수행하는 DSL
     */
}
