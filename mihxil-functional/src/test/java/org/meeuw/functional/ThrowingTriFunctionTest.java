package org.meeuw.functional;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class ThrowingTriFunctionTest {


    @Test
    public void withThrows() {

        ThrowingTriFunction<String, Integer, Float, String, IOException> withThrows = (a ,b, c) -> {
            throw new IOException();
        };
        assertThatThrownBy(() -> {
                withThrows.apply("a", 1, 2.0f);
            }
        ).isInstanceOf(IOException.class);
    }

    @Test
    public void withoutThrows() {
        ThrowingTriFunction<String, Integer, Float, String,  IOException> withoutThrows = (a, b, c) -> a.toUpperCase() + ":" + b + ":" + c;

        ThrowAnyTriFunction<String, Integer, Float, String> withoutThrowsAny = (a, b, c) -> a.toUpperCase()+ ":" + b + ":" + c + ":any";

        // See that it works nice.
        someMethod(withoutThrows);
        someOtherMethod(withoutThrowsAny);

        assertThatThrownBy(() -> {
            someMethod((a, b, c) -> {
                throw new IllegalArgumentException();
            });}).isInstanceOf(IllegalArgumentException.class);

        someOtherMethod(withoutThrows);

        assertThatNoException().isThrownBy(() -> {
            assertThat(withoutThrows.apply("a", 1, 3.0f)).isEqualTo("A:1:3.0");
        });
    }

    protected void someMethod(ThrowingTriFunction<String, Integer, Float,  String, ? extends Exception> test ) {
        System.out.println(test.apply("foobar", 1, 4.0f));
    }

    protected void someOtherMethod(ThrowingTriFunction<String, Integer, Float, String, ? extends Exception> test ) {
        System.out.println("other:" + test.apply("foobar", 2, 5.0f));
    }

}
