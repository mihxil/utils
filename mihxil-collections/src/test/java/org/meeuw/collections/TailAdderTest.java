package org.meeuw.collections;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("deprecation")
public class TailAdderTest {

    @Test
    public void closesOnExhaustion() throws Exception {
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
        assertEquals("a", adder.next());
        assertEquals("b", adder.next());
        assertFalse(adder.hasNext());
        assertFalse(adder.hasNext());
        assertEquals(1, closes.get());
    }

    @Test
    public void addTo() throws Exception {
        Iterator<String> i = Arrays.asList("a", "b").iterator();
        try (TailAdder<String> adder = TailAdder.<String>builder().wrapped(i).adder((s) -> "c").build()) {
            assertEquals("a", adder.next());
            assertEquals(1, adder.getCount());
            assertEquals("b", adder.next());
            assertEquals(2, adder.getCount());
            assertEquals("c", adder.next());
            assertEquals(3, adder.getCount());
            assertFalse(adder.hasNext());
        }
    }

    @Test
    public void onlyIfEmptyOnNotEmpty() throws Exception {
        Iterator<String> i = Arrays.asList("a", "b").iterator();
        try (TailAdder<String> adder = new TailAdder<>(i, true, () -> "c")) {
            assertEquals("a", adder.next());
            assertEquals("b", adder.next());
            assertFalse(adder.hasNext());
        }
    }


    @Test
    public void onlyIfEmptyOnEmpty() throws Exception {
        Iterator<String> i = Collections.emptyIterator();
        try (TailAdder<String> adder = new TailAdder<>(i, true, () -> "c")) {
            assertEquals("c", adder.next());
            assertFalse(adder.hasNext());
        }
    }


    @Test
    public void onlyIfNotEmptyOnNotEmpty() throws Exception {
        Iterator<String> i = Arrays.asList("a", "b").iterator();
        try (TailAdder<String> adder = TailAdder.<String>builder().wrapped(i).onlyIfNotEmpty(true).adder((s) -> "c").build()) {
            assertEquals("a", adder.next());
            assertEquals("b", adder.next());
            assertTrue(adder.hasNext());
            assertEquals("c", adder.next());
        }

    }


    @Test
    public void onlyIfNotEmptyOnEmpty() throws Exception {
        Iterator<String> i = Collections.emptyIterator();
        try (TailAdder<String> adder = TailAdder.<String>builder().wrapped(i).onlyIfNotEmpty(true).adder((s) -> "c").build()) {
            assertFalse(adder.hasNext());
        }
    }

    @Test
    public void tailNull() throws Exception {
        Iterator<String> i = Collections.emptyIterator();
        try (TailAdder<String> adder = new TailAdder<>(i, () -> null)) {
            assertNull(adder.next());
            assertFalse(adder.hasNext());
        }
    }


    @Test
    public void tailException() throws Exception {
        Iterator<String> i = Collections.emptyIterator();
        try (TailAdder<String> adder = new TailAdder<>(i, () -> {
            throw new Exception();
        })) {
            assertFalse(adder.hasNext());
        }
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
