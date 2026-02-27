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
        assertThatThrownBy(() -> withThrows.apply("a", 1)
        ).isInstanceOf(IOException.class);

        ThrowingFunction<String, String, IOException> withArg2 = withThrows.withArg2(1);
        assertThatThrownBy(() -> withArg2.apply("a")).isInstanceOf(IOException.class);
        assertThat(withArg2.toString()).endsWith("(with arg2 1)");

        ThrowingFunction<Integer, String, IOException> withArg1 = withThrows.withArg1("x");
        assertThatThrownBy(() -> withArg1.apply(1)).isInstanceOf(IOException.class);
        assertThat(withArg1.toString()).endsWith("(with arg1 x)");

        ThrowingTriFunction<String, Integer, Float, String, IOException> ignoreArg3 = withThrows.ignoreArg3();
        assertThatThrownBy(() -> ignoreArg3.apply("a", 1, 1.0f)).isInstanceOf(IOException.class);
        assertThat(ignoreArg3.toString()).endsWith("(ignore arg3)");

        ThrowingTriFunction<String, Float, Integer, String, IOException> ignoreArg2 = withThrows.ignoreArg2();
        assertThatThrownBy(() -> ignoreArg2.apply("a", 1.0f, 1)).isInstanceOf(IOException.class);
        assertThat(ignoreArg2.toString()).endsWith("(ignore arg2)");

        ThrowingTriFunction<Float, String, Integer, String, IOException> ignoreArg1 = withThrows.ignoreArg1();
        assertThatThrownBy(() -> ignoreArg1.apply(1.0f, "a", 1)).isInstanceOf(IOException.class);
        assertThat(ignoreArg1.toString()).endsWith("(ignore arg1)");

    }

    @Test
    public void withoutThrows() {
        ThrowingBiFunction<String, Integer, String,  IOException> withoutThrows = (a, b) -> a.toUpperCase() + ":" + b;

        ThrowAnyBiFunction<String, Integer, String> withoutThrowsAny = (a, b) -> a.toUpperCase()+ ":" + b + ":any";

        // See that it works nice.
        someMethod(withoutThrows);
        someOtherMethod(withoutThrowsAny);

        assertThatThrownBy(() -> someMethod((a, b) -> {
            throw new IllegalArgumentException();
        })).isInstanceOf(IllegalArgumentException.class);

        someOtherMethod(withoutThrows);

        assertThatNoException().isThrownBy(() -> assertThat(withoutThrows.apply("a", 1)).isEqualTo("A:1"));
    }

    protected void someMethod(ThrowingBiFunction<String, Integer, String, ? extends Exception> test ) {
        System.out.println(test.apply("foobar", 1));
    }

    protected void someOtherMethod(ThrowingBiFunction<String, Integer, String, ? extends Exception> test ) {
        System.out.println("other:" + test.apply("foobar", 2));
    }

}
