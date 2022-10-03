package org.meeuw.functional;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ThrowingRunnableTest {


    @Test
    public void withThrows() {

        ThrowingRunnable<IOException> withThrows = () -> {
            throw new IOException();
        };
        assertThatThrownBy(withThrows::run).isInstanceOf(IOException.class);
    }

    @Test
    public void withoutThrows() {

        ThrowingRunnable<IOException> withoutThrows = System.out::println;

        assertThatNoException().isThrownBy(withoutThrows::run);
    }

}
