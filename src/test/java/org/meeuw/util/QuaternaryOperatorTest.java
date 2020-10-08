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
        for (String[] args : permute()) {
            assertThat(minBy(String::compareTo).apply(args[0], args[1], args[2], args[3])).isEqualTo("a");
        }
    }


    @Test
    void testMaxBy() {
        for (String[] args : permute()) {
            assertThat(maxBy(String::compareTo).apply(args[0], args[1], args[2], args[3])).isEqualTo("d");
        }
    }

    String[][] permute() {
        return new String[][] {
            {"a", "b", "d", "d"},
            {"a", "b", "d", "d"},
            {"a", "d", "b", "d"},
            {"a", "d", "d", "b"},
            {"a", "d", "b", "d"},
            {"a", "d", "d", "b"},

            {"b", "a", "d", "d"},
            {"b", "a", "d", "d"},
            {"b", "d", "a", "d"},
            {"b", "d", "d", "a"},
            {"b", "d", "a", "d"},
            {"b", "d", "d", "a"},

            {"d", "a", "b", "d"},
            {"d", "a", "d", "b"},
            {"d", "b", "a", "d"},
            {"d", "b", "d", "a"},
            {"d", "d", "a", "b"},
            {"d", "d", "b", "a"},

            {"d", "a", "b", "d"},
            {"d", "a", "d", "b"},
            {"d", "b", "a", "d"},
            {"d", "b", "d", "a"},
            {"d", "d", "a", "b"},
            {"d", "d", "b", "a"}
        };
    }

}
