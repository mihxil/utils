package org.meeuw.collections;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michiel Meeuwissen
 * @since 0.32
 */
@SuppressWarnings("OptionalGetWithoutIsPresent")
class MergedSortedIteratorTest {

    @Test
    void merges() {
        List<String> l1 = Arrays.asList("a", "d");
        List<String> l2 = Arrays.asList("b", "c", "e");

        CountedIterator<String> merged = MergedSortedIterator.merge(Comparator.naturalOrder(), CountedIterator.of(l1), CountedIterator.of(l2));

        assertThat(merged.stream().collect(java.util.stream.Collectors.toList())).isEqualTo(Arrays.asList("a", "b", "c", "d", "e"));
        assertThat(merged.getSize().get()).isEqualTo(5L);
        assertThat(merged.getTotalSize().get()).isEqualTo(5L);


    }

    @Test
    void mergesInSameThread() {
        List<String> l1 = Arrays.asList("a", "d");
        List<String> l2 = Arrays.asList("b", "c", "e");

        CountedIterator<String> merged = MergedSortedIterator.mergeInSameThread(Comparator.naturalOrder(), CountedIterator.of(l1), CountedIterator.of(l2));

        assertThat(merged.stream().collect(java.util.stream.Collectors.toList())).isEqualTo(Arrays.asList("a", "b", "c", "d", "e"));
        assertThat(merged.getSize().get()).isEqualTo(5L);
        assertThat(merged.getTotalSize().get()).isEqualTo(5L);


    }

    @Test
    void mergesInSameThreadWithReversedSources() {
        List<String> l1 = Arrays.asList("a", "d");
        List<String> l2 = Arrays.asList("b", "c", "e");

        CountedIterator<String> merged = MergedSortedIterator.mergeInSameThread(Comparator.naturalOrder(), CountedIterator.of(l2), CountedIterator.of(l1));

        assertThat(merged.stream().collect(java.util.stream.Collectors.toList())).isEqualTo(Arrays.asList("a", "b", "c", "d", "e"));
        assertThat(merged.getSize().get()).isEqualTo(5L);
        assertThat(merged.getTotalSize().get()).isEqualTo(5L);

    }

    @Test
    void closingMergedIteratorClosesEverySource() throws Exception {
        AtomicInteger closes = new AtomicInteger();
        CountedIterator<String> first = closeableIterator(Arrays.asList("a", "c").iterator(), closes);
        CountedIterator<String> second = closeableIterator(Collections.singletonList("b").iterator(), closes);

        try (CountedIterator<String> merged = MergedSortedIterator.merge(Comparator.naturalOrder(), first, second)) {
            assertThat(merged.next()).isEqualTo("a");
        }

        assertThat(closes).hasValue(2);
    }

    private static <T> CountedIterator<T> closeableIterator(Iterator<T> delegate, AtomicInteger closes) {
        return new CountedIterator<T>() {
            private long count;

            @Override
            public boolean hasNext() {
                return delegate.hasNext();
            }

            @Override
            public T next() {
                count++;
                return delegate.next();
            }

            @Override
            public Optional<Long> getSize() {
                return Optional.empty();
            }

            @Override
            public Long getCount() {
                return count;
            }

            @Override
            public void close() {
                closes.incrementAndGet();
            }
        };
    }

}
