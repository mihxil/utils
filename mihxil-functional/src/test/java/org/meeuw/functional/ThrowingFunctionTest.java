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

        ThrowingSupplier<String, IOException> withArg1 = withThrows.withArg1("a");
        assertThatThrownBy(withArg1::get).isInstanceOf(IOException.class);
        assertThat(withArg1.toString()).endsWith("(with arg1 a)");
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

    @Test
    public void andThen() {
        ThrowingFunction<String, String, IOException> func = (s1) -> s1 + ":" + s1.length();

        ThrowingFunction<String,  String, IOException> andThen = func.andThen(s -> s + ":andthen");
        assertThat(andThen.apply("Hello")).isEqualTo("Hello:5:andthen");
        assertThat(andThen.toString()).contains("(and then ");
        assertThat(((Unwrappable) andThen).unwrap()).isSameAs(func);
    }


    @Test
    public void ignoreArg() {
        ThrowingFunction<String, String, IOException> func = (s1) -> s1 + ":" + s1.length();

        ThrowingBiFunction<Float, String,  String, IOException> ignoreArg1 = func.ignoreArg1();
        ThrowingBiFunction<String, Double, String, IOException> ignoreArg2 = func.ignoreArg2();

        assertThat(ignoreArg1.apply(1.0f, "Hello")).isEqualTo("Hello:5");
        assertThat(ignoreArg2.apply("Hello", 2d)).isEqualTo("Hello:5");
    }




}
