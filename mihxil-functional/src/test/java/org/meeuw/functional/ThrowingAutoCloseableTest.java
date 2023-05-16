package org.meeuw.functional;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ThrowingAutoCloseableTest {

    @Test
    public void withThrows() {

        ThrowingAutoCloseable<IOException> withThrows = () -> {
            throw new IOException();
        };
        assertThatThrownBy(withThrows::close
        ).isInstanceOf(IOException.class);
    }

    @Test
    public void withoutThrows() {

        ThrowingAutoCloseable<IOException> withoutThrows = () -> {};

        assertThatNoException().isThrownBy(withoutThrows::close);
    }

}
