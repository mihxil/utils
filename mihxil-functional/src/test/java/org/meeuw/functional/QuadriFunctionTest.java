package org.meeuw.functional;

import org.junit.jupiter.api.Test;
import org.meeuw.functional.QuadriFunction;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michiel Meeuwissen
 * @since 1
 */
class QuadriFunctionTest {

    static class Quadri implements QuadriFunction<String, Float, Double, Integer, String> {

        @Override
        public String apply(String s, Float aFloat, Double aDouble, Integer integer) {
            return s + ":" + aFloat + ":" + aDouble + ":" + integer;
        }
    }

    Quadri quadri = new Quadri();
    @Test
    void withArg1() {

        assertThat(quadri.withArg1("foo").apply(1.0f, 2.0, 3)).isEqualTo("foo:1.0:2.0:3");
        assertThat(quadri.withArg1("foo")).isEqualTo(quadri.withArg1("foo"));
        assertThat(quadri.withArg1("foo")).isNotEqualTo(quadri.withArg1("bar"));

    }

    @Test
    void withArg2() {
         assertThat(quadri.withArg2(1.0f).apply("foo", 2.0, 3)).isEqualTo("foo:1.0:2.0:3");
    }

    @Test
    void withArg3() {
        assertThat(quadri.withArg3(2.0).apply("foo", 1.0f, 3)).isEqualTo("foo:1.0:2.0:3");
    }

    @Test
    void withArg4() {
        assertThat(quadri.withArg4(3).apply("foo", 1.0f, 2.0)).isEqualTo("foo:1.0:2.0:3");
    }

    @Test
    void andThen() {
        assertThat(quadri.andThen((s) -> "[" + s + "]").apply("foo", 1.0f, 2.0, 3)).isEqualTo("[foo:1.0:2.0:3]");
    }
}
