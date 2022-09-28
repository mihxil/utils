package org.meeuw.functional;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class ThrowingSupplierTest {


    @Test
    public void withThrows() {

        ThrowingSupplier<String, IOException> withThrows = () -> {
            throw new IOException();
        };
        assertThatThrownBy(
            withThrows::get
        ).isInstanceOf(IOException.class);
    }

    @Test
    public void withoutThrows() {

        ThrowingSupplier<String, IOException> withoutThrows = () -> "foobar";

        someMethod(withoutThrows);
        someMethod(() -> {
            if (true) {
                return "BarFoo";
            }
            throw new IllegalArgumentException();
        });


        assertThatNoException().isThrownBy(() -> {
            assertThat(withoutThrows.get()).isEqualTo("foobar");
        });
    }

    protected void someMethod(ThrowingSupplier<String, ? extends Exception> test ) {
        System.out.println(test.get());
    }

}
