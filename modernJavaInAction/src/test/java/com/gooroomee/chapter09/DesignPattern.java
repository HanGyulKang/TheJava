package com.gooroomee.chapter09;

import com.gooroomee.chapter09.study.observer.Feed;
import com.gooroomee.chapter09.study.observer.Guardian;
import com.gooroomee.chapter09.study.observer.LeMonde;
import com.gooroomee.chapter09.study.observer.NYTimes;
import com.gooroomee.chapter09.study.strategy.IsAllLowerCase;
import com.gooroomee.chapter09.study.strategy.IsNumeric;
import com.gooroomee.chapter09.study.strategy.Validator;
import com.gooroomee.chapter09.study.templateMethod.OnlineBanking;
import com.gooroomee.chapter09.study.templateMethod.TemplateMethod;
import com.gooroomee.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class DesignPattern {
    /**
     * 전략 : strategy
     * 템플릿 메서드 : template method
     * 옵저버 : observer
     * 의무 체인 : chain of responsibility
     * 팩토리 : factory
     */
    @Test
    public void testStrategyPattern() {
        String str1 = "abcAbDsb";
        String str2 = "abcdefg";
        String str3 = "15029401";
        Validator numericValidator = new Validator(new IsNumeric());
        boolean numericValidator1 = numericValidator.validate(str1);
        boolean numericValidator2 = numericValidator.validate(str2);
        boolean numericValidator3 = numericValidator.validate(str3);
        assertFalse(numericValidator1);
        assertFalse(numericValidator2);
        assertTrue(numericValidator3);

        Validator lowerCaseValidator = new Validator(new IsAllLowerCase());
        boolean lowerCaseValidator1 = lowerCaseValidator.validate(str1);
        boolean lowerCaseValidator2 = lowerCaseValidator.validate(str2);
        boolean lowerCaseValidator3 = lowerCaseValidator.validate(str3);
        assertFalse(lowerCaseValidator1);
        assertTrue(lowerCaseValidator2);
        assertFalse(lowerCaseValidator3);

        // with lambda
        Validator numericValidatorWithFunctionalInterface = new Validator((String s) -> s.matches("\\d+"));
        boolean numericValidate1 = numericValidatorWithFunctionalInterface.validate(str1);
        boolean numericValidate2 = numericValidatorWithFunctionalInterface.validate(str2);
        boolean numericValidate3 = numericValidatorWithFunctionalInterface.validate(str3);
        assertFalse(numericValidate1);
        assertFalse(numericValidate2);
        assertTrue(numericValidate3);
    }

    @Test
    public void templateMethodTest() {
        // 이 알고리즘을 사용하고 싶은데 그대로는 안 되고 조금 고쳐야하는 상황에서 사용하기 적합
        OnlineBanking onlineBanking = new TemplateMethod();
        onlineBanking.processCustomer(3, (Customer c) -> assertEquals("Kim", c.customerName));
    }

    @Test
    public void obserbongPatternTest() {
        // 이벤트 발생 시 한 객체(주제)가 다른 객체 리스트(옵저서)에 자동으로 등록/알림을 보내야하는 상황에서 사용
        Feed f = new Feed();
        f.registerObserver(new NYTimes());
        f.registerObserver(new Guardian());
        f.registerObserver(new LeMonde());
        f.notifyObservers("tweet@sad han gyul kang");
    }
}
