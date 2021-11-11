package org.meeuw.functional;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ThrowingConsumerTest {


    @Test
    public void test() {

        ThrowingConsumer<String, IOException> c = elem -> {
            throw new IOException();
        };
        Assertions.assertThatThrownBy(() -> {
            c.accept("bla");
        }).isInstanceOf(IOException.class);
    }

}
