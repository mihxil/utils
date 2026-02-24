package org.meeuw.functional;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ThrowingConsumerTest {


    @Test
    public void withThrows() {

        ThrowingConsumer<String, IOException> withThrows = string -> {
            throw new IOException();
        };
        assertThatThrownBy(() -> {
            withThrows.accept("bla");
        }).isInstanceOf(IOException.class);
    }

    @Test
    public void withThrowsAndThen() {

        ThrowingConsumer<String, IOException> withThrows = string -> {
            //throw new IOException();
        };
        ThrowingConsumer<String, IOException>  withThrows2 = withThrows.andThen(s -> {
            throw new IOException(s);
        });
        assertThatThrownBy(() -> {
            withThrows2.accept("bla");
        }).hasMessage("bla")
            .isInstanceOf(IOException.class)

        ;
    }

    @Test
    public void withoutThrows() {

        ThrowingConsumer<String, IOException> withoutThrows = System.out::println;

        assertThatNoException().isThrownBy(() -> {
            withoutThrows.accept("bla");
        });
    }

     @Test
    public void withAnyThrows() {

        ThrowAnyConsumer<String> withThrows = string -> {
            throw new IOException();
        };
        assertThatThrownBy(() -> {
            withThrows.accept("bla");
        }).isInstanceOf(IOException.class);
    }

    @Test
    public void withoutAnyThrows() {

        ThrowAnyConsumer<String> withoutThrows = System.out::println;

        assertThatNoException().isThrownBy(() -> {
            withoutThrows.accept("bla");
        });
    }

}
