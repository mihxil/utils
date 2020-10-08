package org.meeuw.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.meeuw.util.QuaternaryOperator.maxBy;
import static org.meeuw.util.QuaternaryOperator.minBy;

/**
 * @author Michiel Meeuwissen
 * @since 2.12
 */
class QuaternaryOperatorTest {

    @Test
    void testMinBy() {
        assertThat(minBy(String::compareTo).apply("a", "b", "c", "d")).isEqualTo("a");
        assertThat(minBy(String::compareTo).apply("a", "c", "b", "d")).isEqualTo("a");
        assertThat(minBy(String::compareTo).apply("b", "a", "c", "d")).isEqualTo("a");
        assertThat(minBy(String::compareTo).apply("b", "c", "a", "d")).isEqualTo("a");
        assertThat(minBy(String::compareTo).apply("c", "a", "b", "d")).isEqualTo("a");
        assertThat(minBy(String::compareTo).apply("c", "b", "a", "d")).isEqualTo("a");

    }


    @Test
    void testMaxBy() {
        assertThat(maxBy(String::compareTo).apply("a", "b", "c", "d")).isEqualTo("d");
        assertThat(maxBy(String::compareTo).apply("a", "c", "b", "d")).isEqualTo("d");
        assertThat(maxBy(String::compareTo).apply("b", "a", "c", "d")).isEqualTo("d");
        assertThat(maxBy(String::compareTo).apply("b", "c", "a", "d")).isEqualTo("d");
        assertThat(maxBy(String::compareTo).apply("c", "a", "b", "d")).isEqualTo("d");
        assertThat(maxBy(String::compareTo).apply("c", "b", "a", "d")).isEqualTo("d");

    }

}
