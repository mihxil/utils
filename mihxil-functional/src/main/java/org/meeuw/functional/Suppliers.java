package org.meeuw.functional;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Utilities related to {@link Supplier}s.
 * @author Michiel Meeuwissen
 * @since 0.1
 */
public class Suppliers {

    private Suppliers() {
    }

    /**
     * Aways supply the same value
     */
    static <T> Supplier<T> always(T value) {
        return new Always<>(value, "always");
    }

    /**
     * Wrap a given supplier. The result of the suppletion is memoized after the first call. Subsequent calls will give the same value, without calling the supplier again.
     */
    static <T> Supplier<T> memoize(Supplier<T> supplier) {
        return new MemoizeSupplier<>(supplier);
    }

    protected static abstract class SupplierWrapper<T, W> extends Wrapper<W> implements Supplier<T> {

        public SupplierWrapper(W wrapped, String reason) {
            super(wrapped, reason);
        }
    }

    protected static class MemoizeSupplier<T> extends SupplierWrapper<T, Supplier<T>> {

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
