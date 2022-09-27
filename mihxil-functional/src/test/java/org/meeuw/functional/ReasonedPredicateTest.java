package org.meeuw.functional;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReasonedPredicateTest {

    ReasonedPredicate<String> testString = new ReasonedPredicate<String>() {
        @Override
        public boolean test(String input) {
            return "a".equals(input);
        }
        @Override
        public String toString() {
            return "equals('a')";

        }
    };

    ReasonedPredicate<String> testString2 = new ReasonedPredicate<String>() {
        @Override
        public TestResult testWithReason(String input) {
            String testString = "a";
            boolean applies = false;
            String reason;
            if (input == null) {
                reason = "input is null";
            } else if ("noreason".equals(input)) {
                applies = true;
                reason = null;
            } else if (input.length() < testString.length()) {
                reason = "input is too short";
            } else if (input.length() > testString.length()) {
                reason = "input is too long";
            } else {
                applies = testString.equals(input);
                reason = (applies ? "input is equal " : "input is not equal ") + testString;
            }
            return TestResult.of(applies, reason);
        }

        @Override
        public String toString() {
            return "equals('a')";

        }
    };


    @Test
    public void test() {
        assertThat(testString.test("b")).isFalse();
        assertThat(testString.test("a")).isTrue();
    }

    @Test
    public void testWithReason() {
        assertThat(testString.testWithReason("b").getAsBoolean()).isFalse();
        assertThat(testString.testWithReason("a").getAsBoolean()).isTrue();

    }

    @Test
    public void reason() {
        assertThat(testString.testWithReason("b").getReason()).isEqualTo("!equals('a')(b)");
        assertThat(testString.testWithReason("a").getReason()).isEqualTo("equals('a')(a)");
    }


    @Test
    public void test2() {
        assertThat(testString2.test("b")).isFalse();
        assertThat(testString2.test("a")).isTrue();
    }

    @Test
    public void test2WithReason() {
        assertThat(testString2.testWithReason("b").getAsBoolean()).isFalse();
        assertThat(testString2.testWithReason("a").getAsBoolean()).isTrue();
    }

    @Test
    public void reason2() {
        assertThat(testString2.testWithReason("b").getReason()).isEqualTo("input is not equal a");
        assertThat(testString2.testWithReason("b").toString()).isEqualTo("input is not equal a");
        assertThat(testString2.testWithReason("a").getReason()).isEqualTo("input is equal a");
        assertThat(testString2.testWithReason("noreason").toString()).isEqualTo("tests true");
    }

}
