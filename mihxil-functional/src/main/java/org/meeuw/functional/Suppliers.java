package org.meeuw.functional;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Michiel Meeuwissen
 * @since 2.12
 */
public class Suppliers {

    private Suppliers() {
    }

    static <T> Supplier<T> always(T value) {
        return new Always<>(value, "always");
    }

    static <T> Supplier<T> memoize(Supplier<T> supplier) {
        return new MemoizeSupplier<>(supplier);
    }

    protected static class MemoizeSupplier<T> extends Wrapper<Supplier<T>> implements Supplier<T> {

        T value;

        boolean evaluated = false;

        public MemoizeSupplier(Supplier<T> supplier) {
            super(supplier, "memoize");
        }

        @Override
        public T get() {
            if (! evaluated) {
                synchronized (this) {
                    if (! evaluated) {
                        value = wrapped.get();
                        evaluated = true;
                    }
                }
            }
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MemoizeSupplier<?> that = (MemoizeSupplier<?>) o;
            return Objects.equals(get(), that.get());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(get());
        }

    }

    protected static class Always<W> extends Wrapper<W> implements Supplier<W> {

        public Always(W wrapped, String why) {
            super(wrapped, why);
        }

        @Override
        public W get() {
            return wrapped;
        }
    }

}
