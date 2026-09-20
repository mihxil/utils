package org.meeuw.collections;

import java.util.*;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class HeadAdderTest {

    @Test
    void addTo() {
        Iterator<String> i = Arrays.asList("a", "b").iterator();
        HeadAdder<String> adder = HeadAdder.<String>builder().wrapped(i).adder((s) -> s + "c").build();
        assertThat(adder.next()).isEqualTo("ac");
        assertThat(adder.next()).isEqualTo("a");
        assertThat(adder.next()).isEqualTo("b");
        assertThat(adder.hasNext()).isFalse();
    }

    @Test
    void onlyIfEmptyOnNotEmpty() {
        Iterator<String> i = Arrays.asList("a", "b").iterator();
        HeadAdder<String> adder = HeadAdder.<String>builder().wrapped(i).adder((s) -> s + "c").onlyIfEmpty(true).build();
        assertThat(adder.next()).isEqualTo("a");
        assertThat(adder.next()).isEqualTo("b");
        assertThat(adder.hasNext()).isFalse();
    }


    @Test
    void onlyIfEmptyOnEmpty() {
        Iterator<String> i = Collections.<String>emptyList().iterator();
        HeadAdder<String> adder = HeadAdder.<String>builder().wrapped(i).adder((s) -> s + "c").onlyIfEmpty(true).build();
        assertThat(adder.next()).isEqualTo("nullc");
        assertThat(adder.hasNext()).isFalse();
    }


    @Test
    void onlyIfNotEmptyOnNotEmpty() {
        Iterator<String> i = Arrays.asList("a", "b").iterator();
        HeadAdder<String> adder = HeadAdder.<String>builder().wrapped(i).adder((s) -> s + "c").onlyIfNotEmpty(true).build();
        assertThat(adder.next()).isEqualTo("ac");
        assertThat(adder.next()).isEqualTo("a");
        assertThat(adder.next()).isEqualTo("b");

    }


    @Test
    void onlyIfNotEmptyOnEmpty() {
        Iterator<String> i = Collections.<String>emptyList().iterator();
        HeadAdder<String> adder = HeadAdder.<String>builder().wrapped(i).adder((s) -> s + "c").onlyIfNotEmpty(true).build();
        assertThat(adder.hasNext()).isFalse();
    }

    @Test
    void headNull() {
        Iterator<String> i = Collections.<String>emptyList().iterator();
        HeadAdder<String> adder = HeadAdder.<String>builder().wrapped(i).adder((s) -> null).build();

        assertThat(adder.next()).isNull();
        assertThat(adder.hasNext()).isFalse();
    }


    @Test
    void headException() {
        Iterator<String> i = Collections.<String>emptyList().iterator();
        HeadAdder<String> adder = HeadAdder.<String>builder().wrapped(i).adder((s) -> {
            throw new RuntimeException();
        }).build();

        assertThat(adder.hasNext()).isFalse();
    }

    @Test
    void nextOnAnEmptyIteratorThrows() {
        HeadAdder<String> adder = HeadAdder.<String>builder()
            .wrapped(Collections.<String>emptyList().iterator())
            .build();

        assertThatThrownBy(adder::next).isInstanceOf(NoSuchElementException.class);
    }
}
