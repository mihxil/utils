package org.meeuw.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michiel Meeuwissen
 * @since 2.16
 */
class TriConsumerTest {

    static class Tri implements TriConsumer<String, Integer, Float> {
        final List<String> consumers = new ArrayList<>();

        @Override
        public void accept(String s, Integer integer, Float aFloat) {
            consumers.add(s + ":" + integer + ":" + aFloat);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            return true;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public String toString() {
            return "Tri";
        }
    }

    @Test
    void andThen() {
        Tri tri1 = new Tri();
        Tri tri2 = new Tri();

        tri1.andThen(tri2).accept("a", 1, 2.0f);
        assertThat(tri1.consumers).containsExactly("a:1:2.0");
        assertThat(tri2.consumers).containsExactly("a:1:2.0");
    }

    @Test
    void withArg1() {
        Tri tri = new Tri();
        tri.withArg1("foo").accept(1, 2.0f);

        assertThat(tri.consumers).containsExactly("foo:1:2.0");

        assertThat(tri.withArg1("foo").toString()).isEqualTo("Tri(with arg1 foo)");
        assertThat(tri.withArg1("foo")).isEqualTo(tri.withArg1("foo"));
        assertThat(tri.withArg1("foo")).isNotEqualTo(tri.withArg1("bar"));



    }

    @Test
    void withArg2() {
        Tri tri = new Tri();
        tri.withArg2(1).accept("foobar", 2.0f);

        assertThat(tri.consumers).containsExactly("foobar:1:2.0");
        assertThat(tri.withArg2(1).toString()).isEqualTo("Tri(with arg2 1)");
        assertThat(tri.withArg2(1)).isEqualTo(tri.withArg2(1));
        assertThat(tri.withArg2(1)).isNotEqualTo(tri.withArg1("foot"));

    }

    @Test
    void withArg3() {
        Tri tri = new Tri();
        tri.withArg3(3.0f).accept("bla", 2);

        assertThat(tri.consumers).containsExactly("bla:2:3.0");
    }
}
