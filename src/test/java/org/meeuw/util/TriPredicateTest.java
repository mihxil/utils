package org.meeuw.util;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michiel Meeuwissen
 * @since ...
 */
public class TriPredicateTest {
    /**
	 * checks if first argument is bigger than the other two
     */

    static class My implements TriPredicate<String, Integer, Float> {
        @Override
        public boolean test(String s, Integer integer, Float aFloat) {
            float  f = Float.parseFloat(s);
            return f > aFloat && f > integer;
        }
        @Override
        public String toString() {
            return "My";
        }
    }
    /**
     * checks if first argument is bigger or equal than the other two
     */

    static class You implements TriPredicate<String, Integer, Float> {
        @Override
        public boolean test(String s, Integer integer, Float aFloat) {
            float  f = Float.parseFloat(s);
            return f >= aFloat && f >= integer;
        }
        @Override
        public String toString() {
            return "You";
        }
    }


    My my = new My();
    You you = new You();

    @Test
    public void test1() {
        assertThat(my.test("123", 120, 120f)).isTrue();
        assertThat(my.test("1", 120, 120f)).isFalse();
    }

    @Test
    public void and() {
        assertThat(my.and(you).test("123", 120, 120f)).isTrue();
        assertThat(my.and(you).test("120", 120, 120f)).isFalse();
        assertThat(you.and(my).test("120", 120, 120f)).isFalse();
    }

    @Test
    public void negate() {
        assertThat(my.negate().test("123", 120, 120f)).isFalse();
        assertThat(my.negate().test("1", 120, 120f)).isTrue();
    }

    @Test
    public void or() {
        assertThat(my.or(you).test("123", 120, 120f)).isTrue();
        assertThat(my.or(you).test("120", 120, 120f)).isTrue();
        assertThat(my.or(you).test("100", 120, 120f)).isFalse();
    }

    @Test
    public void withArg1() {
        assertThat(my.withArg1("123").test(120, 120f)).isTrue();
        assertThat(my.withArg1("123").test(124, 120f)).isFalse();
        assertThat(my.withArg1("123").toString()).isEqualTo("My(with arg1 123)");
        assertThat(my.withArg1("123")).isEqualTo(my.withArg1("123"));
        assertThat(my.withArg1("123")).isNotEqualTo(my.withArg1("124"));
        assertThat(my.withArg1("123")).isNotEqualTo(my.withArg2(124));


    }

    @Test
    public void withArg2() {
        assertThat(you.withArg2(120).test("124", 120f)).isTrue();
        assertThat(you.withArg2(123).test("122", 120f)).isFalse();
    }

    @Test
    public void withArg3() {
        assertThat(you.withArg3(120f).test("124", 120)).isTrue();
        assertThat(you.withArg3(123f).test("122", 120)).isFalse();
    }
}
