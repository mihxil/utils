package org.meeuw.collections;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Michiel Meeuwissen
 * @since 1.8
 */
class FilteringIteratorTest {

    private static final Predicate<String> notC = input -> !"c".equals(input);

    @Test
    void filters() {
        List<String> list = Arrays.asList("a", "b", "c", null, "d");
        AtomicInteger i = new AtomicInteger(0);
        Iterator<String> iterator = new FilteringIterator<>(list.iterator(), notC,
            FilteringIterator.keepAliveWithoutBreaks(2, value -> i.getAndIncrement()));
        StringBuilder build = new StringBuilder();
        while (iterator.hasNext()) {
            iterator.hasNext(); // check that you can call it multiple times
            build.append(iterator.next());
        }
        assertThat(build).hasToString("abnulld");
        assertThat(i.get()).isEqualTo(2);
    }

    @Test
    void acceptsNulls() {
        List<String> list = Arrays.asList("a", "b", "c", null, "d");

        Iterator<String> iterator = new FilteringIterator<>(list.iterator(), null);
        StringBuilder build = new StringBuilder();
        while (iterator.hasNext()) {
            build.append(iterator.next());
        }
        assertThat(build).hasToString("abcnulld");

    }

    @Test
    void noSuchElement() {
        assertThatThrownBy(() -> {
            Iterator<String> iterator = new FilteringIterator<>(Arrays.asList("a", "b", "c", null, "d").iterator(), notC);
            assertThat(iterator.next()).isEqualTo("a");
            assertThat(iterator.next()).isEqualTo("b");
            assertThat(iterator.next()).isNull();
            assertThat(iterator.next()).isEqualTo("d");

            iterator.next();
        }).isInstanceOf(NoSuchElementException.class);
    }


    @Test
    void removesAcceptedElement() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", null, "d"));

        Iterator<String> iterator = new FilteringIterator<>(list.iterator(), null);
        while (iterator.hasNext()) {
            if ("b".equals(iterator.next())) {
                iterator.remove();
            }
        }
        assertThat(list).containsExactly("a", "c", null, "d");

    }

    @Test
    void removesAllAcceptedElements() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", null, "d"));

        Iterator<String> iterator = new FilteringIterator<>(list.iterator(), input -> input == null || input.equals("b"));
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        assertThat(list).containsExactly("a", "c", "d");

    }


    @Test
    void rejectsRemoveAfterHasNext() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", null, "d"));

        Iterator<String> iterator = new FilteringIterator<>(list.iterator(), input -> input == null || input.equals("b"));
        iterator.hasNext();
        assertThatThrownBy(iterator::remove).isInstanceOf(UnsupportedOperationException.class);
    }

}
