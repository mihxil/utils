package org.meeuw.util;

import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michiel Meeuwissen
 * @since 0.1
 */
class SuppliersTest {

    static int i = 0;

    @Test
    void memoize() {
        Supplier<Integer> memoize = Suppliers.memoize(() -> ++i);
        assertThat(memoize.get()).isEqualTo(1);
        assertThat(memoize.get()).isEqualTo(1);
    }
}
