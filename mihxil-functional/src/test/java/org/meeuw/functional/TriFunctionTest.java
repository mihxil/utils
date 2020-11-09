package org.meeuw.functional;

import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michiel Meeuwissen
 * @since 2.16
 */
class TriFunctionTest {

    static class Tri implements TriFunction<Integer, String, Float, String> {
        @Override
        public String apply(Integer integer, String s, Float aFloat) {
            return integer + ":" + s + ":" + aFloat;
        }
        @Override
        public String toString() {
            return "Tri";
        }
    }

    Tri tri = new Tri();




    @Test
    void withArg1() {

        assertThat(tri.withArg1(1).apply("a", 2.0f)).isEqualTo("1:a:2.0");
        assertThat(tri.withArg1(1)).isEqualTo(tri.withArg1(1));


        assertThat(tri.withArg1(1)).isNotEqualTo(tri.withArg1(2));
        assertThat(tri.withArg1(1)).isNotEqualTo(tri.withArg2("two"));
    }

	@Test
	void withArg2() {
        assertThat(tri.withArg2("foo").apply(1, 2.0f)).isEqualTo("1:foo:2.0");
    }

	@Test
	void withArg3() {
        assertThat(tri.withArg3(3.0f).apply(1, "bar")).isEqualTo("1:bar:3.0");
    }


    UnaryOperator<String> brackets = new UnaryOperator<String>() {
        @Override
        public String apply(String s) {
            return "[" + s + "]";
        }
        @Override
        public String toString() {
            return "brackets";
        }
    };

    @Test
    void andThen() {
        assertThat(tri.andThen(brackets).apply(1, "2", 3.0f)).isEqualTo("[1:2:3.0]");
        assertThat(tri.andThen(brackets).toString()).isEqualTo("Tri( and then brackets)");
    }
}
