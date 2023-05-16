package org.meeuw.functional;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ThrowingAutocloseableTest {

    @Test
    public void withThrows() {

        ThrowingAutocloseable<IOException> withThrows = () -> {
            throw new IOException();
        };
        assertThatThrownBy(withThrows::close
        ).isInstanceOf(IOException.class);
    }

    @Test
    public void withoutThrows() {

        ThrowingAutocloseable<IOException> withoutThrows = () -> {};

        assertThatNoException().isThrownBy(withoutThrows::close);
    }

}
