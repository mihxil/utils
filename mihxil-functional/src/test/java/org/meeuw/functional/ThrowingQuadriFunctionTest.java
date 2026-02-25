package org.meeuw.functional;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


@SuppressWarnings("rawtypes")
class ThrowingQuadriFunctionTest {


    @Test
    public void withThrows() {

        ThrowingQuadriFunction<String, Integer, Float, Character, String, IOException> withThrows = (a ,b, c, d) -> {
            throw new IOException();
        };
        assertThatThrownBy(() -> {
            withThrows.apply("a", 1, 2.0f, 'z');
            }
        ).isInstanceOf(IOException.class);
    }

    @Test
    public void withoutThrows() {
        ThrowingQuadriFunction<String, Integer, Float, Character, String,  IOException> withoutThrows = (a, b, c, d) -> a.toUpperCase() + ":" + b + ":" + c + ":" + d;

        ThrowAnyQuadriFunction<String, Integer, Float, Character, String> withoutThrowsAny = (a, b, c, d) -> a.toUpperCase()+ ":" + b + ":" + c + ":" + d + ":any";

        // See that it works nice.
        someMethod(withoutThrows);
        someOtherMethod(withoutThrowsAny);

        assertThatThrownBy(() -> {
            someMethod((a, b, c, d) -> {
                throw new IllegalArgumentException();
            });}).isInstanceOf(IllegalArgumentException.class);

        someOtherMethod(withoutThrows);

        assertThatNoException().isThrownBy(() -> {
            assertThat(withoutThrows.apply("a", 1, 3.0f, 'x')).isEqualTo("A:1:3.0:x");
        });
    }

    protected void someMethod(ThrowingQuadriFunction<String, Integer, Float, Character,  String, ? extends Exception> test ) {
        System.out.println(test.apply("foobar", 1, 4.0f, 'c'));
    }

    protected void someOtherMethod(ThrowingQuadriFunction<String, Integer, Float, Character, String, ? extends Exception> test ) {
        System.out.println("other:" + test.apply("foobar", 2, 5.0f, 'd'));
    }

    @Test
    public void withArg1() {
        ThrowingQuadriFunction<String, Integer, Float, Double, String, IOException> func = (s1, s2, s3, s4) -> s1 + ":" + s2 + ":" + s3 + ":" + s4;
        ThrowingTriFunction<Integer, Float, Double, String, IOException> withArg1 = func.withArg1("x");
        assertThat(withArg1.apply( 1, 2f, 1d)).isEqualTo("x:1:2.0:1.0");
        assertThat(((Unwrappable) withArg1).unwrap()).isSameAs(func);
    }
    @Test
    public void withArg2() {
        ThrowingQuadriFunction<String, Integer, Float, Double, String, IOException> func = (s1, s2, s3, s4) -> s1 + ":" + s2 + ":" + s3 + ":" + s4;
        ThrowingTriFunction<String, Float, Double, String, IOException> withArg2 = func.withArg2(3);
        assertThat(withArg2.apply("Hello", 1f, 1d)).isEqualTo("Hello:3:1.0:1.0");
        assertThat(((Unwrappable) withArg2).unwrap()).isSameAs(func);
    }

    @Test
    public void withArg3() {
        ThrowingQuadriFunction<String, Integer, Float, Double, String, IOException> func = (s1, s2, s3, s4) -> s1 + ":" + s2 + ":" + s3 + ":" + s4;
        ThrowingTriFunction<String, Integer, Double, String, IOException> withArg3 = func.withArg3(3.0f);
        assertThat(withArg3.apply("Hello", 1, 1d)).isEqualTo("Hello:1:3.0:1.0");
        assertThat(((Unwrappable) withArg3).unwrap()).isSameAs(func);
    }

    @Test
    public void withArg4() {
        ThrowingQuadriFunction<String, Integer, Float, Double, String, IOException> func = (s1, s2, s3, s4) -> s1 + ":" + s2 + ":" + s3 + ":" + s4;
        ThrowingTriFunction<String, Integer, Float, String, IOException> withArg4 = func.withArg4(3.0d);
        assertThat(withArg4.apply("Hello", 1, 1f)).isEqualTo("Hello:1:1.0:3.0");
        assertThat(((Unwrappable) withArg4).unwrap()).isSameAs(func);
    }

    @Test
    public void andThen() {
        ThrowingQuadriFunction<String, Integer, Float, Double, String, IOException> func = (s1, s2, s3, s4) -> s1 + ":" + s2 + ":" + s3 + ":" + s4;

        ThrowingQuadriFunction<String, Integer, Float, Double, String, IOException> andThen = func.andThen(s -> s + ":andthen");
        assertThat(andThen.apply("Hello", 1, 1f, 2d)).isEqualTo("Hello:1:1.0:2.0:andthen");
        assertThat(andThen.toString()).contains("(and then ");
        assertThat(((Unwrappable) andThen).unwrap()).isSameAs(func);
    }





}
