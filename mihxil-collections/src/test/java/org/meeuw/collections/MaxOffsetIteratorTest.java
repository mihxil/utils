package org.meeuw.collections;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MaxOffsetIteratorTest {

    @Test
    void appliesMax() {
        List<String> test = Arrays.asList("a", "b", "c", "d");
        assertThat(new MaxOffsetIterator<>(test.iterator(), 2).stream().collect(java.util.stream.Collectors.toList())).containsExactly("a", "b");
    }

    @Test
    void appliesNoMaxForNull() {
        List<String> test = Arrays.asList("a", "b", "c", "d");
        assertThat(new MaxOffsetIterator<>(test.iterator(), null).stream().collect(java.util.stream.Collectors.toList())).containsExactly("a", "b", "c", "d");
    }

    @Test
    void appliesMaxAndOffset() {
        List<String> test = Arrays.asList("a", "b", null, "c", "d");
        assertThat(new MaxOffsetIterator<>(test.iterator(), 2, 1).stream().collect(java.util.stream.Collectors.toList())).containsExactly("b", null);
    }

    @Test
    void doesNotCountNullsTowardsMaxAndOffset() {
        List<String> test = Arrays.asList("a", null, "b", "c", null, "d", "e");
        assertThat(new MaxOffsetIterator<>(test.iterator(), 2, 2, false).stream().collect(java.util.stream.Collectors.toList())).containsExactly("c", null, "d");
    }

    @Test
    void autoClose() {
        final boolean[] booleans = new boolean[2];
        AutoCloseable autoCloseable = () -> booleans[0] = true;
        Runnable callback = () -> booleans[1] = true;
        MaxOffsetIterator<String> i = MaxOffsetIterator
            .<String>builder()
            .wrapped(Arrays.asList("a", "b", "c").iterator())
            .max(2)
            .callback(callback)
            .build()
            .autoClose(autoCloseable);

        assertThat(i.stream().collect(java.util.stream.Collectors.toList())).containsExactly("a", "b");
        assertThat(booleans[0]).isTrue();
        assertThat(booleans[1]).isTrue();
    }

    @Test
    void explicitCloseRunsEveryConfiguredCleanupOnce() throws Exception {
        AtomicInteger callbackCalls = new AtomicInteger();
        AtomicInteger closeCalls = new AtomicInteger();
        MaxOffsetIterator<String> iterator = MaxOffsetIterator
            .<String>builder()
            .wrapped(Collections.singletonList("a").iterator())
            .callback(callbackCalls::incrementAndGet)
            .build()
            .autoClose(closeCalls::incrementAndGet);

        iterator.close();
        iterator.close();

        assertThat(callbackCalls).hasValue(1);
        assertThat(closeCalls).hasValue(1);
    }

    @Test
    void countExcludesSkippedOffsetElements() {
        MaxOffsetIterator<String> iterator = new MaxOffsetIterator<>(Arrays.asList("a", "b", "c").iterator(), 2, 1);

        assertThat(iterator.next()).isEqualTo("b");
        assertThat(iterator.getCount()).isEqualTo(1);
    }


    @Test
    void formatsToString() {
        List<String> test = Arrays.asList("a", "b", "c", "d");
        assertThat(new MaxOffsetIterator<>(test.iterator(), 2).toString()).matches("Closeable\\[.*]\\[0,2]");

        assertThat(MaxOffsetIterator.<String>builder().wrapped(test.iterator()).offset(1).build().toString()).matches("Closeable\\[.*]\\[1,]");

    }

    @Test
    void peeking() {
        List<String> list = Arrays.asList("a", "b", "c", "d");
        PeekingIterator<String> i = MergedSortedIterator.peekingIterator(list.iterator());
        assertThat(i.peek()).isEqualTo("a");

        MaxOffsetIterator<String> mo = MaxOffsetIterator
            .<String>builder()
            .wrapped(i)
            .max(2)
            .offset(1)
            .build();
        assertThat(mo.peek()).isEqualTo("b");
        assertThat(mo.peekingWrapped().hasNext()).isTrue();

        assertThat(mo.next()).isEqualTo("b");
        assertThat(mo.next()).isEqualTo("c");
        assertThatThrownBy(mo::peek).isInstanceOf(NoSuchElementException.class);
        assertThat(mo.peekingWrapped().peek()).isEqualTo("d");
    }

    @Test
    void predicate() throws Exception {
        List<String> list = Arrays.asList("a", "b", "c", "d", "e");

        try (MaxOffsetIterator<String> mo = MaxOffsetIterator
            .<String>builder()
            .wrapped(list.iterator())
            .max(2)
            .countPredicate(s -> !"c".equals(s))
            .offset(1)
            .build()) {
            assertThat(mo.next()).isEqualTo("b");
            assertThat(mo.next()).isEqualTo("c");// not counted, but returned!
            assertThat(mo.count).isEqualTo(2);
            assertThat(mo.next()).isEqualTo("d");

            assertThatThrownBy(mo::next).isInstanceOf(NoSuchElementException.class);
        }

    }


}
