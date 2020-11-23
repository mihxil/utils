package org.meeuw.functional;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.meeuw.functional.QuaternaryOperator.maxBy;
import static org.meeuw.functional.QuaternaryOperator.minBy;

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

    @Test
    void equalsHashCode() {
        Comparator<String> stringComparator = String::compareTo;
        QuaternaryOperator<String> stringQuaternaryOperator = minBy(stringComparator);
        assertThat(stringQuaternaryOperator).isEqualTo(minBy(stringComparator));
        assertThat(stringQuaternaryOperator.hashCode()).isEqualTo(minBy(stringComparator).hashCode());
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
            {"d", "d", "b", "a"},

            {"a", "b", "c", "d"}
        };
    }
    String[][] permutee() {
        return new String[][]{
            {"a", "b", "d", "e"},
            {"a", "b", "e", "d"},
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
