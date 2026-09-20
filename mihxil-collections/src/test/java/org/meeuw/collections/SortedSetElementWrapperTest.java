package org.meeuw.collections;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SortedSetElementWrapperTest {

    @Test
    void adaptsElementsAndRangeViews() {
        SortedSet<Integer> source = new TreeSet<>(Arrays.asList(1, 2, 3));
        SortedSet<String> wrapped = new StringWrapper(source);

        assertThat(wrapped).containsExactly("1", "2", "3");
        assertThat(wrapped.subSet("1", "3")).containsExactly("1", "2");
        assertThat(wrapped.comparator().compare("2", "10")).isLessThan(0);

        assertThat(wrapped.remove("2")).isTrue();
        assertThat(source).containsExactly(1, 3);
    }

    @Test
    void sameElementWrapperAddsToTheSource() {
        SortedSet<Integer> source = new TreeSet<>();
        SortedSetSameElementWrapper<Integer> wrapped = new SortedSetSameElementWrapper<Integer>(source) {
            @Override
            protected Integer adapt(Integer element) {
                return element;
            }
        };

        assertThat(wrapped.add(2)).isTrue();
        assertThat(source).containsExactly(2);
    }

    private static class StringWrapper extends SortedSetElementWrapper<Integer, String> {
        private StringWrapper(SortedSet<Integer> wrapped) {
            super(wrapped);
        }

        @Override
        protected String adapt(Integer element) {
            return Integer.toString(element);
        }
    }
}
