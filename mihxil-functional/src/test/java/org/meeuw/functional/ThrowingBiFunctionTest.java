package org.meeuw.functional;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class ThrowingBiFunctionTest {


    @Test
    public void withThrows() {

        ThrowingBiFunction<String, Integer, String, IOException> withThrows = (a ,b) -> {
            throw new IOException();
        };
        assertThatThrownBy(() -> {
                withThrows.apply("a", 1);
            }
        ).isInstanceOf(IOException.class);

        ThrowingFunction<String, String, IOException> withArg2 = withThrows.withArg2(1);
        assertThatThrownBy(() -> {
            withArg2.apply("a");
        }).isInstanceOf(IOException.class);
        assertThat(withArg2.toString()).endsWith("(with arg2 1)");

    }

    @Test
    public void withoutThrows() {
        ThrowingBiFunction<String, Integer, String,  IOException> withoutThrows = (a, b) -> a.toUpperCase() + ":" + b;

        ThrowAnyBiFunction<String, Integer, String> withoutThrowsAny = (a, b) -> a.toUpperCase()+ ":" + b + ":any";

        // See that it works nice.
        someMethod(withoutThrows);
        someOtherMethod(withoutThrowsAny);

        assertThatThrownBy(() -> {
            someMethod((a, b) -> {
                throw new IllegalArgumentException();
            });}).isInstanceOf(IllegalArgumentException.class);

        someOtherMethod(withoutThrows);

        assertThatNoException().isThrownBy(() -> {
            assertThat(withoutThrows.apply("a", 1)).isEqualTo("A:1");
        });
    }

    protected void someMethod(ThrowingBiFunction<String, Integer, String, ? extends Exception> test ) {
        System.out.println(test.apply("foobar", 1));
    }

    protected void someOtherMethod(ThrowingBiFunction<String, Integer, String, ? extends Exception> test ) {
        System.out.println("other:" + test.apply("foobar", 2));
    }

}
