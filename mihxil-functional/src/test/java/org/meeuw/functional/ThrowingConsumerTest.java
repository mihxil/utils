package org.meeuw.functional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ThrowingConsumerTest {


    @Test
    public void withThrows() {

        ThrowingConsumer<String, IOException> withThrows = string -> {
            throw new IOException();
        };
        assertThatThrownBy(() -> withThrows.accept("bla")).isInstanceOf(IOException.class);
    }

    @Test
    public void withThrowsAndThen() {

        ThrowingConsumer<String, IOException> withThrows = string -> {
            //throw new IOException();
        };
        ThrowingConsumer<String, IOException>  withThrows2 = withThrows.andThen(s -> {
            throw new IOException(s);
        });
        assertThatThrownBy(() -> withThrows2.accept("bla")).hasMessage("bla")
            .isInstanceOf(IOException.class)

        ;
    }

    @Test
    public void withoutThrows() {

        ThrowingConsumer<String, IOException> withoutThrows = System.out::println;

        assertThatNoException().isThrownBy(() -> withoutThrows.accept("bla"));
    }

     @Test
    public void withAnyThrows() {

        ThrowAnyConsumer<String> withThrows = string -> {
            throw new IOException();
        };
        assertThatThrownBy(() -> withThrows.accept("bla")).isInstanceOf(IOException.class);
    }

    @Test
    public void withoutAnyThrows() {

        ThrowAnyConsumer<String> withoutThrows = System.out::println;

        assertThatNoException().isThrownBy(() -> withoutThrows.accept("bla"));
    }

    @Test
    public void ignoreArg1() {
        ThrowAnyConsumer<String> withoutThrows = System.out::println;

        assertThatNoException().isThrownBy(() -> withoutThrows.ignoreArg1().accept("bloa", "bla"));
    }
    @Test
    public void ignoreArg2() {
        ThrowAnyConsumer<String> withoutThrows = System.out::println;

        assertThatNoException().isThrownBy(() -> withoutThrows.ignoreArg2().accept("bloa", 1));
    }

    @Test
    public void withArg1() {
        final List<String> list = new ArrayList<>();
        ThrowAnyConsumer<String> withoutThrows = list::add;

        assertThatNoException().isThrownBy(() -> withoutThrows.ignoreArg2().accept("bloa", 1));
        assertThat(list).containsExactly("bloa");
        withoutThrows.withArg1("foo").run();
        assertThat(list).containsExactly("bloa", "foo");



    }

}
