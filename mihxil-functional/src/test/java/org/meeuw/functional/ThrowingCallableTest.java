package org.meeuw.functional;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ThrowingCallableTest {


    @Test
    public void withThrows() {

        ThrowingCallable<String, IOException> withThrows = () -> {
            throw new IOException();
        };
        assertThatThrownBy(withThrows::call).isInstanceOf(IOException.class);
    }

    @Test
    public void withoutThrows() {

        ThrowingCallable<String, IOException> withoutThrows = () -> {
            return "Hello";
        };

        assertThatNoException().isThrownBy(withoutThrows::call);
    }

}
