package org.meeuw.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LazyIteratorTest {

    @Test
    void createsTheSourceOnlyWhenNeeded() {
        AtomicInteger supplied = new AtomicInteger();
        LazyIterator<String> iterator = LazyIterator.of(() -> {
            supplied.incrementAndGet();
            return Arrays.asList("a", "b").iterator();
        });

        assertThat(supplied).hasValue(0);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(supplied).hasValue(1);
        assertThat(iterator.next()).isEqualTo("a");
        assertThat(iterator.getCount()).isEqualTo(1);
        assertThat(iterator.unwrap()).isNotNull();
    }

    @Test
    void closesAnInstantiatedSource() throws Exception {
        AtomicInteger closes = new AtomicInteger();
        LazyIterator<String> iterator = LazyIterator.of(() -> new AutoCloseableIterator(Arrays.asList("a"), closes));

        iterator.close();
        assertThat(closes).hasValue(0);

        iterator.hasNext();
        iterator.close();

        assertThat(closes).hasValue(1);
    }

    @Test
    void exposesTheSourceSizeWhenAvailable() {
        LazyIterator<String> iterator = LazyIterator.of(() -> CountedIterator.of(Arrays.asList("a", "b")));

        assertThat(iterator.getSize()).contains(2L);
    }

    private static class AutoCloseableIterator implements Iterator<String>, AutoCloseable {
        private final Iterator<String> delegate;
        private final AtomicInteger closes;

        private AutoCloseableIterator(Iterable<String> values, AtomicInteger closes) {
            delegate = values.iterator();
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
