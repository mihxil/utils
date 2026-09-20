package org.meeuw.collections;

import java.util.Arrays;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CloseableSpliteratorTest {

    @Test
    void closesItsResourceOnlyOnce() throws Exception {
        AtomicInteger closes = new AtomicInteger();
        CloseableSpliterator<String> spliterator = CloseableSpliterator.of(
            Spliterators.spliterator(Arrays.asList("a", "b"), 0),
            closes::incrementAndGet);

        spliterator.close();
        spliterator.close();

        assertThat(closes).hasValue(1);
    }

    @Test
    void streamClosesItsResource() {
        AtomicInteger closes = new AtomicInteger();
        CloseableSpliterator<String> spliterator = CloseableSpliterator.of(
            Spliterators.spliterator(Arrays.asList("a", "b"), 0),
            closes::incrementAndGet);

        try (java.util.stream.Stream<String> stream = spliterator.stream()) {
            assertThat(stream).containsExactly("a", "b");
        }

        assertThat(closes).hasValue(1);
    }

    @Test
    void closeableIteratorProvidesCloseableSpliterator() throws Exception {
        AtomicInteger closes = new AtomicInteger();
        CloseableIterator<String> iterator = CloseableIterator.of(new AutoCloseableIterator(Arrays.asList("a", "b"), closes));

        try (CloseableSpliterator<String> spliterator = iterator.spliterator()) {
            assertThat(StreamSupport.stream(spliterator, false)).containsExactly("a", "b");
        }

        assertThat(closes).hasValue(1);
    }

    private static class AutoCloseableIterator implements java.util.Iterator<String>, AutoCloseable {
        private final java.util.Iterator<String> delegate;
        private final AtomicInteger closes;

        private AutoCloseableIterator(Iterable<String> values, AtomicInteger closes) {
            this.delegate = values.iterator();
            this.closes = closes;
        }

        @Override
        public boolean hasNext() {
            return delegate.hasNext();
        }

        @Override
        public String next() {
            return delegate.next();
        }

        @Override
        public void close() {
            closes.incrementAndGet();
        }
    }
}
