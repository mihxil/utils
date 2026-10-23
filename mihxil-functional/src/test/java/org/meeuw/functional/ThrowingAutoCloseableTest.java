package org.meeuw.functional;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ThrowingAutoCloseableTest {

    @Test
    void withThrows() {
        ThrowingAutoCloseable<IOException> withThrows = () -> {
            throw new IOException();
        };
        assertThatThrownBy(withThrows::close
        ).isInstanceOf(IOException.class);
    }

    @Test
    void withoutThrows() {

        ThrowingAutoCloseable<IOException> withoutThrows = () -> {};

        assertThatNoException().isThrownBy(withoutThrows::close);
    }

    @Test
    void withAnyThrows() {

        ThrowAnyAutoCloseable withThrows = () -> {
            throw new IOException();
        };
        assertThatThrownBy(withThrows::close
        ).isInstanceOf(IOException.class);
    }

    @Test
    void withoutAnyThrows() {
        ThrowAnyAutoCloseable withoutThrows = () -> {};

        assertThatNoException().isThrownBy(withoutThrows::close);
    }

}
