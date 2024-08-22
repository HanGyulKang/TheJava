package com.gooroomee.chapter09;

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
    }
}
