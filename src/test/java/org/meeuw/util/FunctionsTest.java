package org.meeuw.util;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Michiel Meeuwissen
 * @since ...
 */
class FunctionsTest {

    @Test
    void always() {
        assertThat(Functions.always("x").apply(new Object())).isEqualTo("x");
        assertThat(Functions.always("x")).isEqualTo(Functions.always("x"));
        assertThat(Functions.always("x")).isNotEqualTo(Functions.always("y"));
        assertThat(Functions.always("x").toString()).isEqualTo("always x");
        assertThat(Functions.always("x", "X").toString()).isEqualTo("X");

    }

    @Test
    void biAlways() {
        assertThat(Functions.biAlways("x").apply("y", "z")).isEqualTo("x");
        assertThat(Functions.biAlways("x")).isEqualTo(Functions.biAlways("x"));
        assertThat(Functions.biAlways("x")).isNotEqualTo(Functions.biAlways("y"));
        assertThat(Functions.biAlways("x")).isNotEqualTo(Functions.always("y"));
        assertThat(Functions.biAlways("x").toString()).isEqualTo("always x");
        assertThat(Functions.biAlways("x", "X").toString()).isEqualTo("X");
    }


    @Test
    void triAlways() {
        assertThat(Functions.triAlways("x").apply("y", "z", 1)).isEqualTo("x");
        assertThat(Functions.triAlways("x")).isEqualTo(Functions.triAlways("x"));
        assertThat(Functions.triAlways("x")).isNotEqualTo(Functions.triAlways("y"));
        assertThat(Functions.triAlways("x")).isNotEqualTo(Functions.always("y"));
        assertThat(Functions.triAlways("x").toString()).isEqualTo("always x");
        assertThat(Functions.triAlways("x", "X").toString()).isEqualTo("X");
    }

    @Test
    void withArg1() {
        Function<Float, String> mono = Object::toString;
        assertThat(Functions.withArg1(mono, 1.0f).get()).isEqualTo(("1.0"));
    }

    static class Bi implements BiFunction<String, Double, String> {

        @Override
        public String apply(String s, Double aDouble) {
            return s + "+" + aDouble;
        }
    }
    @Test
    void biWithArg1() {
        Bi bi = new Bi();
        assertThat(Functions.withArg1(bi, "s").apply(2.0)).isEqualTo("s+2.0");

        assertThat(Functions.withArg1(bi, "s")).isEqualTo(Functions.withArg1(bi, "s"));
    }

    @Test
    void withArg2() {
    }

    @Test
    void ignoreArg2() {
    }

    @Test
    void ignoreArg1() {
    }

    @Test
    void ignoreArg3() {
    }

    @Test
    void testIgnoreArg2() {
    }

    @Test
    void testIgnoreArg1() {
    }

    @Test
    void ignoreArg4() {
    }

    @Test
    void testIgnoreArg3() {
    }

    @Test
    void testIgnoreArg21() {
    }

    @Test
    void testIgnoreArg11() {
    }
}
