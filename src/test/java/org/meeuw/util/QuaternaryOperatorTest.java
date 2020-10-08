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
            {"a", "b", "c", "d"},
            {"a", "b", "d", "c"},
            {"a", "c", "b", "d"},
            {"a", "c", "d", "b"},
            {"a", "d", "b", "c"},
            {"a", "d", "c", "b"},

            {"b", "a", "c", "d"},
            {"b", "a", "d", "c"},
            {"b", "c", "a", "d"},
            {"b", "c", "d", "a"},
            {"b", "d", "a", "c"},
            {"b", "d", "c", "a"},

            {"c", "a", "b", "d"},
            {"c", "a", "d", "b"},
            {"c", "b", "a", "d"},
            {"c", "b", "d", "a"},
            {"c", "d", "a", "b"},
            {"c", "d", "b", "a"},

            {"d", "a", "b", "c"},
            {"d", "a", "c", "b"},
            {"d", "b", "a", "c"},
            {"d", "b", "c", "a"},
            {"d", "c", "a", "b"},
            {"d", "c", "b", "a"}
        };
    }

}
