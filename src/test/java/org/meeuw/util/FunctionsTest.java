package org.meeuw.util;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.meeuw.util.Functions.*;


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

        assertThat(Functions.always("x").hashCode()).isEqualTo(Functions.always("x").hashCode());
        assertThat(Functions.always("x").hashCode()).isNotEqualTo(Functions.always("y").hashCode());

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
    Function<Float, String> mono = Object::toString;


    @Test
    void monoWithArg1() {
        assertThat(withArg1(mono, 1.0f).get()).isEqualTo(("1.0"));
    }

    @Test
    void monoIgnoreArg1() {
        assertThat(Functions.ignoreArg1(mono).apply(new Object(), 1.0f)).isEqualTo("1.0");

    }


    @Test
    void monoIgnoreArg2() {
        assertThat(Functions.ignoreArg2(mono).apply(1.0f, new Object())).isEqualTo("1.0");

    }


    static class Bi implements BiFunction<String, Double, String> {
        @Override
        public String apply(String s, Double aDouble) {
            return s + "+" + aDouble;
        }

        @Override
        public String toString() {
            return "Bi";
        }
    }
    Bi bi = new Bi();

    static class Tri implements TriFunction<String, Double, Float, String> {

        @Override
        public String apply(String s, Double aDouble, Float aFloat) {
            return s + "+" + aDouble + "+" + aFloat;
        }
        @Override
        public String toString() {
            return "Tri";
        }
    }
    Tri tri = new Tri();

    @Test
    void biWithArg1() {
        assertThat(withArg1(bi, "s").apply(2.0)).isEqualTo("s+2.0");

        assertThat(withArg1(bi, "s")).isEqualTo(withArg1(bi, "s"));
        assertThat(withArg1(bi, "s").hashCode()).isEqualTo(withArg1(bi, "s").hashCode());
        assertThat(withArg1(bi, "s").hashCode()).isNotEqualTo(withArg1(bi, "t").hashCode());

        assertThat(withArg1(bi, "s")).isNotEqualTo(withArg1(bi, "t"));
        assertThat(withArg1(bi, "s").toString()).isEqualTo("Bi(with arg1 s)");


    }

    @Test
    void biWithArg2() {
        assertThat(withArg2(bi, 2.0).apply("s")).isEqualTo("s+2.0");

        assertThat(withArg2(bi, 2.0)).isEqualTo(withArg2(bi, 2.0));
        assertThat(withArg2(bi, 2.0)).isNotEqualTo(withArg2(bi, 3.0));
    }

    @Test
    void biIgnoreArg3() {
        assertThat(ignoreArg3(bi).apply("a", 2.0, new Object())).isEqualTo("a+2.0");
    }
    @Test
    void biIgnoreArg2() {
        assertThat(ignoreArg2(bi).apply("a", new Object(), 2.0)).isEqualTo("a+2.0");
    }

    @Test
    void biIgnoreArg1() {
        assertThat(ignoreArg1(bi).apply(new Object(), "a", 2.0)).isEqualTo("a+2.0");
    }


    @Test
    void triIgnoreArg4() {
        assertThat(ignoreArg4(tri).apply("a", 1.0, 2.0f, new Object())).isEqualTo("a+1.0+2.0");

    }

    @Test
    void triIgnoreArg3() {
        assertThat(ignoreArg3(tri).apply("a", 1.0,new Object(), 2.0f)).isEqualTo("a+1.0+2.0");

    }

    @Test
    void triIgnoreArg2() {
        assertThat(ignoreArg2(tri).apply("a", new Object(), 1.0, 2.0f)).isEqualTo("a+1.0+2.0");
    }

    @Test
    void triIgnoreArg1() {
        assertThat(ignoreArg1(tri).apply(new Object(), "a", 1.0, 2.0f)).isEqualTo("a+1.0+2.0");

    }

    @Test
    void quadriIgnoreArg4() {
    }

    @Test
    void quadriIgnoreArg3() {
    }

    @Test
    void quadriIgnoreArg2() {
    }

    @Test
    void quadriIgnoreArg1() {
    }
}
