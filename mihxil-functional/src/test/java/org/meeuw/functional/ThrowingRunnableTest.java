package org.meeuw.functional;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ThrowingRunnableTest {


    @Test
    void withThrows() {

        ThrowingRunnable<IOException> withThrows = () -> {
            throw new IOException();
        };
        assertThatThrownBy(withThrows::run).isInstanceOf(IOException.class);
        assertThatThrownBy(withThrows::runThrows).isInstanceOf(IOException.class);
    }

    @Test
    void withoutThrows() {

        ThrowingRunnable<IOException> withoutThrows = System.out::println;

        assertThatNoException().isThrownBy(withoutThrows::run);
        assertThatNoException().isThrownBy(withoutThrows::runThrows);

    }

}
