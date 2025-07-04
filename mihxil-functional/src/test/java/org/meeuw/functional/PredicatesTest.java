package org.meeuw.functional;


import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.meeuw.functional.Predicates.*;


/**
 * @author Michiel Meeuwissen
 * @since 2.18
 */
public class PredicatesTest {

    @SuppressWarnings({"ConstantConditions", "EqualsBetweenInconvertibleTypes", "EqualsWithItself"})
    @Test
    public void testAlwaysFalse() {
        Predicate<String> always = Predicates.always(false);
        assertThat(Predicates.<String>alwaysFalse().test("a")).isFalse();
        assertThat(Predicates.<String>alwaysFalse().toString()).isEqualTo("FALSE");
        assertThat(Predicates.<String>alwaysFalse().equals(Predicates.alwaysFalse())).isTrue();
        assertThat(always.equals(always)).isTrue();
        assertThat(always.equals(Predicates.always(true))).isFalse();

        assertThat(always.equals(null)).isFalse();
        assertThat(always.equals("foobar")).isFalse();
        assertThat(Predicates.<String>alwaysFalse().equals(Predicates.alwaysTrue())).isFalse();

        assertThat(Predicates.<String>alwaysFalse().hashCode()).isEqualTo(Predicates.alwaysFalse().hashCode());
        assertThat(Predicates.<String>alwaysFalse().hashCode()).isNotEqualTo(Predicates.alwaysTrue().hashCode());

    }

    @Test
    public void alwaysTrue() {
        assertThat(Predicates.<String>always(true).test("a")).isTrue();
        assertThat(Predicates.<String>alwaysTrue().toString()).isEqualTo("TRUE");
    }

    @Test
    public void biAlwaysFalse() {
        assertThat(Predicates.biAlways(false).test("a", "b")).isFalse();
        assertThat(Predicates.biAlwaysFalse().toString()).isEqualTo("FALSE");
        assertThat(Predicates.<String, Integer>biAlways(false, "nietes").toString()).isEqualTo("nietes");
        assertThat(Predicates.<String, Integer>biAlways(false, null).toString()).isEqualTo("FALSE");

    }

    @Test
    public void biAlwaysTrue() {
        assertThat(Predicates.<String, Integer>biAlways(true).test("a", 2)).isTrue();
        assertThat(Predicates.<String, Integer>biAlwaysTrue().toString()).isEqualTo("TRUE");
        assertThat(Predicates.<String, Integer>biAlways(true, "waar").toString()).isEqualTo("waar");
        assertThat(Predicates.<String, Integer>biAlways(true, null).toString()).isEqualTo("TRUE");
    }

    @Test
    public void triAlwaysFalse() {
        assertThat(Predicates.<String, Integer, Float>triAlways(false).test("a", 2, 3.0f)).isFalse();
        assertThat(Predicates.<String, Integer, Float>triAlwaysFalse().toString()).isEqualTo("FALSE");
    }

    @Test
    public void triAlwaysTrue() {
        assertThat(Predicates.<String, Integer, Float>triAlways(true).test("a", 2, 3.0f)).isTrue();
        assertThat(Predicates.<String, Integer, Float>triAlwaysTrue().toString()).isEqualTo("TRUE");
        assertThat(Predicates.<String, Integer, Float>triAlways(true, "ja").toString()).isEqualTo("ja");
    }

    final Predicate<String> mono = (s) -> s.length() > 5;
    public static class Bi implements BiPredicate<String, Integer> {

        @Override
        public boolean test(String s, Integer length) {
            return s.length() == length;
        }
        @Override
        public String toString() {
            return "Bi";
        }
    }

    final Bi bi = new Bi();


    @Test
    public void monoIgnoreArg1() {
        assertThat(ignoreArg1(mono).test(new Object(), "1234")).isFalse();

        assertThat(ignoreArg1(mono).equals(ignoreArg1(mono))).isTrue();
        assertThat(ignoreArg1(mono).equals(ignoreArg1((Predicate<String>) (s) -> s.length() < 5))).isFalse();
    }

    @Test
    public void monoIgnoreArg2() {
        assertThat(ignoreArg2(mono).test("123456", new Object())).isTrue();
    }


    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void biIgnoreArg1() {
        assertThat(ignoreArg1(bi).test(new Object(), "1234", 4)).isTrue();

        assertThat(ignoreArg1(bi).equals(ignoreArg1(bi))).isTrue();
        assertThat(ignoreArg1(bi).equals(ignoreArg1((BiPredicate<String, String>) (s, t) -> s.length() < 5))).isFalse();
    }

    @Test
    public void biIgnoreArg2() {
        assertThat(ignoreArg2(bi).test("1234567", new Object(), 6)).isFalse();
    }

    @Test
    public void biIgnoreArg3() {
        assertThat(ignoreArg3(bi).test("123456", 6, new Object())).isTrue();
    }

    @Test
    public void biWithArg2() {
        assertThat(withArg2(bi, 1).test("x")).isTrue();

        assertThat(withArg2(bi, 1).equals(withArg2(bi, 1))).isTrue();
        assertThat(withArg2(bi, 1).equals(withArg2(bi, 2))).isFalse();
    }

    @Test
    public void biWithArg1() {
        assertThat(withArg1(bi, "x").test(1)).isTrue();
        assertThat(withArg1(bi, "xx").test(1)).isFalse();

        assertThat(withArg1(bi, "x").equals(withArg1(bi, "x"))).isTrue();
        assertThat(withArg1(bi, "x").equals(withArg1(bi, "xx"))).isFalse();

    }
}
