package org.meeuw.functional;

import java.util.OptionalLong;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("OptionalGetWithoutIsPresent")
class OptionalsTest {

    @Test
    void ofNullable() {
        assertFalse(Optionals.ofNullable(null).isPresent());
        assertEquals(2L, Optionals.ofNullable(2L).getAsLong());
    }

    @Test
    void boxed() {
        assertEquals(10L, Optionals.boxed(OptionalLong.of(10)).get());
        assertFalse(Optionals.boxed(OptionalLong.empty()).isPresent());
    }

    @Test
    void orElse() {
        assertNull(Optionals.orElse(OptionalLong.empty(), null));
    }
}
