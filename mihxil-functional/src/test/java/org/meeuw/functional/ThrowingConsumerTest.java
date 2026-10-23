package org.meeuw.functional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ThrowingConsumerTest {


    @Test
    void withThrows() {

        ThrowingConsumer<String, IOException> withThrows = string -> {
            throw new IOException();
        };
        assertThatThrownBy(() -> withThrows.accept("bla")).isInstanceOf(IOException.class);
        assertThatThrownBy(() -> withThrows.acceptThrows("bla")).isInstanceOf(IOException.class);

    }

    @Test
    void withThrowsAndThen() {

        ThrowingConsumer<String, IOException> withThrows = string -> {
            //throw new IOException();
        };
        ThrowingConsumer<String, IOException>  withThrows2 = withThrows.andThen(s -> {
            throw new IOException(s);
        });
        assertThatThrownBy(() -> withThrows2.accept("bla")).hasMessage("bla")
            .isInstanceOf(IOException.class)
        ;
        assertThatThrownBy(() -> withThrows2.acceptThrows("bla")).hasMessage("bla")
            .isInstanceOf(IOException.class)

        ;
    }

    @Test
    void withoutThrows() {

        ThrowingConsumer<String, IOException> withoutThrows = System.out::println;

        assertThatNoException().isThrownBy(() -> withoutThrows.accept("bla"));
        assertThatNoException().isThrownBy(() -> withoutThrows.acceptThrows("bla"));
    }

    @Test
    void withAnyThrows() {

        ThrowAnyConsumer<String> withThrows = string -> {
            throw new IOException();
        };
        assertThatThrownBy(() -> withThrows.accept("bla")).isInstanceOf(IOException.class);
        assertThatThrownBy(() -> withThrows.acceptThrows("bla")).isInstanceOf(IOException.class);

    }

    @Test
     void withoutAnyThrows() {

        ThrowAnyConsumer<String> withoutThrows = System.out::println;

        assertThatNoException().isThrownBy(() -> withoutThrows.accept("bla"));
        assertThatNoException().isThrownBy(() -> withoutThrows.acceptThrows("bla"));
    }

    @Test
    void ignoreArg1() {
        ThrowAnyConsumer<String> withoutThrows = System.out::println;

        assertThatNoException().isThrownBy(() -> withoutThrows.ignoreArg1().accept("bloa", "bla"));
    }

    @Test
    void ignoreArg2() {
        ThrowAnyConsumer<String> withoutThrows = System.out::println;

        assertThatNoException().isThrownBy(() -> withoutThrows.ignoreArg2().accept("bloa", 1));
        assertThatNoException().isThrownBy(() -> withoutThrows.ignoreArg2().acceptThrows("bloa", 1));

    }

    @Test
    void withArg1() {
        final List<String> list = new ArrayList<>();
        ThrowAnyConsumer<String> withoutThrows = list::add;

        assertThatNoException().isThrownBy(() -> withoutThrows.ignoreArg2().accept("bloa", 1));
        assertThatNoException().isThrownBy(() -> withoutThrows.ignoreArg2().acceptThrows("bloa", 1));
        assertThat(list).containsExactly("bloa");
        withoutThrows.withArg1("foo").run();
        assertThat(list).containsExactly("bloa", "foo");



    }

}
