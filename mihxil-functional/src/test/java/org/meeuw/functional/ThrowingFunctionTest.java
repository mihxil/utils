package org.meeuw.functional;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class ThrowingFunctionTest {


    @Test
    public void withThrows() {

        ThrowingFunction<String, String, IOException> withThrows = (a) -> {
            throw new IOException();
        };
        assertThatThrownBy(() -> {
                withThrows.apply("a");
            }
        ).isInstanceOf(IOException.class);
    }

    @Test
    public void withoutThrows() {
        ThrowingFunction<String, String,  IOException> withoutThrows = String::toUpperCase;

        ThrowAnyFunction<String, String> withoutThrowsAny = (a) -> a.toUpperCase()+ ":any";

        // See that it works nice.
        someMethod(withoutThrows);
        someOtherMethod(withoutThrowsAny);

        assertThatThrownBy(() -> {
            someMethod((a) -> {
                throw new IllegalArgumentException();
            });}).isInstanceOf(IllegalArgumentException.class);

        someOtherMethod(withoutThrows);

        assertThatNoException().isThrownBy(() -> {
            assertThat(withoutThrows.apply("a")).isEqualTo("A");
        });
    }

    protected void someMethod(ThrowingFunction<String, String, ? extends Exception> test ) {
        System.out.println(test.apply("foobar"));
    }

    protected void someOtherMethod(ThrowingFunction<String, String, ? extends Exception> test ) {
        System.out.println("other:" + test.apply("foobar"));
    }

}
