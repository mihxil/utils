package org.meeuw.collections;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TailAdderTest {

    @SuppressWarnings("ConstantValue")
    @Test
    void closesOnExhaustion() throws Exception {
        AtomicInteger closes = new AtomicInteger();
        CloseableIterator<String> iterator = new CloseableIterator<String>() {
            private final Iterator<String> delegate = Collections.singletonList("a").iterator();

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
        };

        TailAdder<String> adder = TailAdder.withFunctions(iterator, last -> "b");
        assertThat(adder.next()).isEqualTo("a");
        assertThat(adder.next()).isEqualTo("b");
        assertThat(adder.hasNext()).isFalse();
        assertThat(adder.hasNext()).isFalse();
        assertThat(closes).hasValue(1);
    }

    @Test
    void addTo() throws Exception {
        Iterator<String> i = Arrays.asList("a", "b").iterator();
        try (TailAdder<String> adder = TailAdder.<String>builder().wrapped(i).adder((s) -> "c").build()) {
            assertThat(adder.next()).isEqualTo("a");
            assertThat(adder.getCount()).isEqualTo(1);
            assertThat(adder.next()).isEqualTo("b");
            assertThat(adder.getCount()).isEqualTo(2);
            assertThat(adder.next()).isEqualTo("c");
            assertThat(adder.getCount()).isEqualTo(3);
            assertThat(adder.hasNext()).isFalse();
        }
    }

    @Test
    void onlyIfEmptyOnNotEmpty() throws Exception {
        Iterator<String> i = Arrays.asList("a", "b").iterator();
        try (TailAdder<String> adder = TailAdder.<String>builder()
            .wrapped(i)
            .onlyIfEmpty(true)
            .callableAdder(() -> "c")
            .build()) {
            assertThat(adder.next()).isEqualTo("a");
            assertThat(adder.next()).isEqualTo("b");
            assertThat(adder.hasNext()).isFalse();
        }
    }


    @Test
    void onlyIfEmptyOnEmpty() throws Exception {
        Iterator<String> i = Collections.emptyIterator();
        try (TailAdder<String> adder = TailAdder.<String>builder()
            .wrapped(i)
            .onlyIfEmpty(true)
            .callableAdder(() -> "c")
            .build()) {
            assertThat(adder.next()).isEqualTo("c");
            assertThat(adder.hasNext()).isFalse();
        }
    }


    @Test
    void onlyIfNotEmptyOnNotEmpty() throws Exception {
        Iterator<String> i = Arrays.asList("a", "b").iterator();
        try (TailAdder<String> adder = TailAdder.<String>builder().wrapped(i).onlyIfNotEmpty(true).adder((s) -> "c").build()) {
            assertThat(adder.next()).isEqualTo("a");
            assertThat(adder.next()).isEqualTo("b");
            assertThat(adder.hasNext()).isTrue();
            assertThat(adder.next()).isEqualTo("c");
        }

    }


    @Test
    void onlyIfNotEmptyOnEmpty() throws Exception {
        Iterator<String> i = Collections.emptyIterator();
        try (TailAdder<String> adder = TailAdder.<String>builder().wrapped(i).onlyIfNotEmpty(true).adder((s) -> "c").build()) {
            assertThat(adder.hasNext()).isFalse();
        }
    }

    @Test
    void tailNull() throws Exception {
        Iterator<String> i = Collections.emptyIterator();
        try (TailAdder<String> adder = TailAdder.<String>builder().wrapped(i).adder((a) -> null).build()) {
            assertThat(adder.next()).isNull();
            assertThat(adder.hasNext()).isFalse();
        }
    }


    @Test
    void tailException() throws Exception {
        Iterator<String> i = Collections.emptyIterator();
        try (TailAdder<String> adder = TailAdder.<String>builder()
            .wrapped(i)
            .callableAdder(() -> {
                throw new Exception();
            })
            .build()) {
            assertThat(adder.hasNext()).isFalse();
        }
    }

    @Test
    void combinesFunctionAndCallableAddersInOrder() {
        TailAdder<Integer> iterator = TailAdder.<Integer>builder()
            .wrapped(Collections.singletonList(1).iterator())
            .adder(last -> last + 1)
            .callableAdder(() -> 3)
            .build();

        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.next()).isEqualTo(2);
        assertThat(iterator.next()).isEqualTo(3);
        assertThat(iterator.hasNext()).isFalse();
    }


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
