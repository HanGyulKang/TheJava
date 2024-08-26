package com.gooroomee.chapter09;

import com.gooroomee.chapter09.study.OnlineBanking;
import com.gooroomee.chapter09.study.TemplateMethod;
import com.gooroomee.domain.Customer;
import com.gooroomee.domain.Database;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DesignPattern {
    /**
     * 전략 : strategy
     * 템플릿 메서드 : template method
     * 옵저버 : observer
     * 의무 체인 : chain of responsibility
     * 팩토리 : factory
     */

    /**
     * [ Strategy Pattern ]
     */
    @FunctionalInterface
    public interface ValidationStrategy {
        boolean execute(String s);
    }

    public static class IsAllLowerCase implements ValidationStrategy {
        @Override
        public boolean execute(String s) {
            return s.matches("[a-z]+");
        }
    }

    public static class IsNumeric implements ValidationStrategy {
        @Override
        public boolean execute(String s) {
            return s.matches("\\d+");
        }
    }

    public static class Validator {
        private final ValidationStrategy strategy;

        public Validator(ValidationStrategy strategy) {
            this.strategy = strategy;
        }

        public boolean validate(String s) {
            return strategy.execute(s);
        }
    }

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
        onlineBanking.processCustomer(3, (Customer c) -> System.out.println("Happy! : " + c.getCustomerName()));
    }
}
