package org.meeuw.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TransformingIteratorTest {

    @Test
    void transformsAndRemovesSourceValues() {
        ArrayList<Integer> source = new ArrayList<>(Arrays.asList(1, 2));
        TransformingIterator<String, Integer> iterator = TransformingIterator.<String, Integer>builder()
            .wrapped(source.iterator())
            .transformer(value -> "value-" + value)
            .build();

        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo("value-1");
        iterator.remove();

        assertThat(source).containsExactly(2);
        assertThat(iterator.next()).isEqualTo("value-2");
    }

    @Test
    void closesAnAutoCloseableSource() throws Exception {
        AtomicInteger closes = new AtomicInteger();
        TransformingIterator<String, Integer> iterator = TransformingIterator.<String, Integer>builder()
            .wrapped(new AutoCloseableIterator(Arrays.asList(1), closes))
            .transformer(String::valueOf)
            .build();

        iterator.close();

        assertThat(closes).hasValue(1);
    }

    private static class AutoCloseableIterator implements Iterator<Integer>, AutoCloseable {
        private final Iterator<Integer> delegate;
        private final AtomicInteger closes;

        private AutoCloseableIterator(Iterable<Integer> values, AtomicInteger closes) {
            delegate = values.iterator();
            this.closes = closes;
        }

        @Override
        public boolean hasNext() {
            return delegate.hasNext();
        }

        @Override
        public Integer next() {
            return delegate.next();
        }

        @Override
        public void close() {
            closes.incrementAndGet();
        }
    }
}
