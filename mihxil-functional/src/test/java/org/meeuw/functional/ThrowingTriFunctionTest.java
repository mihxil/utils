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

    @Test
    public void withArg3() {
        ThrowingTriFunction<String, Integer, Float, String, IOException> func = (s1, s2, s3) -> s1 + ":" + s2 + ":" + s3;
        ThrowingBiFunction<String, Integer, String, IOException> withArg3 = func.withArg3(3.0f);
        assertThat(withArg3.apply("Hello", 1)).isEqualTo("Hello:1:3.0");
    }

    @Test
    public void withArg2() {
        ThrowingTriFunction<String, Integer, Float, String, IOException> func = (s1, s2, s3) -> s1 + ":" + s2 + ":" + s3;
        ThrowingBiFunction<String, Float, String, IOException> withArg2 = func.withArg2(1);
        assertThat(withArg2.apply("Hello", 3.0f)).isEqualTo("Hello:1:3.0");
    }

    @Test
    public void withArg1() {
        ThrowingTriFunction<String, Integer, Float, String, IOException> func = (s1, s2, s3) -> s1 + ":" + s2 + ":" + s3;
        ThrowingBiFunction<Integer, Float, String, IOException> withArg1 = func.withArg1("foo");
        assertThat(withArg1.apply(1,  3.0f)).isEqualTo("foo:1:3.0");
    }

}
