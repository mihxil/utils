package org.meeuw.functional;

import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.meeuw.functional.Suppliers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michiel Meeuwissen
 * @since 0.1
 */
class SuppliersTest {

    static int i = 0;

    static class I implements Supplier<Integer> {

        @Override
        public Integer get() {
            return i;
        }
        @Override
        public String toString() {
            return "I";
        }
    }

    @Test
    void memoize() {
        Supplier<Integer> isup = new I();
        i++;
        Supplier<Integer> memoize = Suppliers.memoize(isup);
        assertThat(memoize.get()).isEqualTo(1);
        Supplier<Integer> another = Suppliers.memoize(isup);
        assertThat(memoize).isEqualTo(another);
        assertThat(memoize.hashCode()).isEqualTo(another.hashCode());

        i++;
        assertThat(memoize.get()).isEqualTo(1);
        assertThat(memoize).isEqualTo(memoize);
        assertThat(memoize.toString()).isEqualTo("I(memoize)");

        Supplier<Integer> memoize2 = Suppliers.memoize(isup);
        assertThat(memoize).isNotEqualTo(memoize2);
        assertThat(memoize2.get()).isEqualTo(2);

    }

    @Test
    void always() {
        Supplier<String> a1 = Suppliers.always("a");
        Supplier<String> a2 = Suppliers.always("a");
        assertThat(a1).isEqualTo(a2);
        assertThat(a1.hashCode()).isEqualTo(a2.hashCode());
        assertThat(a1.get()).isEqualTo("a");
        Supplier<String> b = Suppliers.always("b");
        assertThat(a1).isNotEqualTo(b);


    }
}
