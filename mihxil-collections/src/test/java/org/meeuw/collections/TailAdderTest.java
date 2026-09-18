package org.meeuw.collections;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TailAdderTest {

    @Test
    void countChangesOnlyAfterNext() {
        TailAdder<Integer> iterator = TailAdder.withFunctions(
            Arrays.asList(1).iterator(),
            last -> last + 1,
            last -> last + 2);

        assertThat(iterator.getCount()).isZero();
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.getCount()).isEqualTo(1);

        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.getCount()).isEqualTo(1);
        assertThat(iterator.next()).isEqualTo(2);
        assertThat(iterator.getCount()).isEqualTo(2);

        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.getCount()).isEqualTo(2);
        assertThat(iterator.next()).isEqualTo(3);
        assertThat(iterator.getCount()).isEqualTo(3);
    }

    @Test
    void sizeIncludesEveryApplicableTail() {
        TailAdder<Integer> iterator = TailAdder.<Integer>builder()
            .wrapped(CountedIterator.of(Arrays.asList(1)))
            .adder(last -> last + 1)
            .adder(last -> last + 2)
            .build();

        assertThat(iterator.getSize()).contains(3L);

        TailAdder<Integer> onlyIfNotEmpty = TailAdder.<Integer>builder()
            .wrapped(CountedIterator.of(Collections.<Integer>emptyList()))
            .onlyIfNotEmpty(true)
            .adder(last -> 1)
            .build();

        assertThat(onlyIfNotEmpty.getSize()).contains(0L);
    }
}
