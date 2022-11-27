package org.meeuw.functional;

import java.io.IOException;
import java.util.function.Supplier;

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

        ThrowAnySupplier<String> withoutThrowsAny = () -> "foobarany";

        // See that it works nice.
        someMethod(withoutThrows);
        someOtherMethod(withoutThrowsAny);

        assertThatThrownBy(() -> {
            someMethod(() -> {
                throw new IllegalArgumentException();
            });}).isInstanceOf(IllegalArgumentException.class);

        someOtherMethod(withoutThrows);

        assertThatNoException().isThrownBy(() -> {
            assertThat(withoutThrows.get()).isEqualTo("foobar");
        });
    }

    protected void someMethod(ThrowingSupplier<String, ? extends Exception> test ) {
        System.out.println(test.get());
    }

    protected void someOtherMethod(Supplier<String> test ) {
        System.out.println("other:" + test.get());
    }

}
