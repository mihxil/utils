package org.meeuw.functional;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michiel Meeuwissen
 * @since 0.1
 */
@Execution(ExecutionMode.SAME_THREAD)
class SuppliersTest {

    static int i = 0;

    static class I implements CloseableSupplier<Integer> {
        boolean closed = false;
        @Override
        public Integer get() {
            return i;
        }
        @Override
        public String toString() {
            return "I";
        }

        @Override
        public void close() throws Exception {
            closed = true;
        }
    }

    static class SlowI extends  I {
        @Override
        public Integer get() {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return i;
        }
    }

    @SuppressWarnings({"EqualsWithItself", "EqualsBetweenInconvertibleTypes", "ConstantConditions"})
    @Test
    void memoize() throws Exception {
        I isup = new I();
        i++;
        try (UnwrappableCloseableSupplier<Integer, Supplier<Integer>> memoize = Suppliers.memoize(isup)) {
            assertThat(memoize.get()).isEqualTo(1);
            Supplier<Integer> another = Suppliers.memoize(isup);
            assertThat(memoize.equals(another)).isTrue();

            assertThat(memoize.equals("")).isFalse();
            assertThat(memoize.equals(memoize)).isTrue();
            assertThat(memoize.equals(Suppliers.memoize(() -> i))).isFalse();

            assertThat(memoize.hashCode()).isEqualTo(another.hashCode());

            i++;
            assertThat(memoize.get()).isEqualTo(1);

            assertThat(memoize.equals(memoize)).isTrue();
            assertThat(memoize.equals("something else")).isFalse();
            assertThat(memoize.equals(null)).isFalse();
            assertThat(memoize.unwrap().toString()).isEqualTo("I(memoize)");
            assertThat(memoize.toString()).isEqualTo("I(memoize)");

            Supplier<Integer> memoize2 = Suppliers.memoize(isup);
            assertThat(memoize.equals(memoize2)).isFalse();
            assertThat(memoize2.get()).isEqualTo(2);
        }
        assertThat(isup.closed).isTrue();
    }

    @SuppressWarnings({"EqualsBetweenInconvertibleTypes", "EqualsWithItself"})
    @Test
    void memoizeSlow() throws InterruptedException {
        Supplier<Integer> isup = new SlowI();
        i = 1;
        AtomicInteger result1= new AtomicInteger();
        AtomicInteger result2= new AtomicInteger();

        Supplier<Integer> memoize = Suppliers.memoize(isup);

        assertThat(memoize.equals("")).isFalse();
        assertThat(memoize.equals(memoize)).isTrue();

        new Thread(() -> {
            result1.set(memoize.get());
            synchronized (SuppliersTest.this) {
                SuppliersTest.this.notifyAll();
            }
            i++;
        }).start();
        new Thread(() -> {
            result2.set(memoize.get());
            synchronized (SuppliersTest.this) {
                SuppliersTest.this.notifyAll();
            }
            i++;
        }).start();

        while (result1.get() == 0 || result2.get() == 0) {
            synchronized (this) {
                wait();
            }
        }
        assertThat(result1).hasValue(1);
        assertThat(result2).hasValue(1);

    }

    @Test
    void closeable() throws Exception {
        I isup = new I();

        try (CloseableSupplier<Integer> closeable = Suppliers.closeable(isup)) {
           assertThat(closeable.get()).isEqualTo(i);
        }
        assertThat(isup.closed).isTrue();
    }
    @Test
    void closeableWithCloser() throws Exception {
        I isup = new I();

        try (CloseableSupplier<Integer> closeable = Suppliers.closeable(isup, s -> isup.close())) {
            assertThat(closeable.get()).isEqualTo(i);
        }
        assertThat(isup.closed).isTrue();
    }

    /**
     * default close() should not throw an exception or so
     */
    @Test
    void closeableEmpty() throws Exception {
        try (CloseableSupplier<Integer> closeable = () -> 1000) {
            assertThat(closeable.get()).isEqualTo(1000);
        }
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

    @Test
    void ignoreArg() {
        Supplier<String> a1 = Suppliers.always("a");

        Function<Integer, String> fa1 = Suppliers.ignoreArg(a1);

        assertThat(fa1).isEqualTo(fa1);
        assertThat(fa1).isEqualTo(Suppliers.ignoreArg(a1));
        assertThat(fa1.apply(1)).isEqualTo("a");
        assertThat(fa1.apply(2)).isEqualTo("a");
    }


}
