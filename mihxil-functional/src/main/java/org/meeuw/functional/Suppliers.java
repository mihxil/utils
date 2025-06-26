package org.meeuw.functional;

import java.util.Objects;
import java.util.function.Function;
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
     * @param value the value to supply
     * @param <T> the type of the value to supply
     * @return a new supplier always supplying the given value
     */
    public static <T> Supplier<T> always(T value) {
        return new Always<>(value, "always");
    }

    /**
     * Morphs a {@link Supplier} into a {@link java.util.function.Function}, where it's argument ignored and the
     * return value supplied by the supplier.
     * @param <T> the type of the argument to the resulting function (ignored)
     * @param <R> the type of the return value of the function and of the supplier
     * @param supplier the supplier to implement the function
     * @return a new function that ignores its argument but just returns the value of the given supplier
     */
    public static <T, R> Function<T, R> ignoreArg(Supplier<R> supplier) {
        return new Functions.MonoWrapper<Supplier<R>, T, R>(supplier, null, "ignore arg") {
            @Override
            public R apply(T t) {
                return wrapped.get();
            }
        };
    }

    /**
     * Wrap a given supplier. The result of the suppletion is memoized after the first call. Subsequent calls will give the same value, without calling the supplier again.
     *
     * @param supplier the supplier to memoize
     * @param <T> The type of the objects to supply
     * @return a new supplier that uses the argument supplier only once
     */
    public static <T> Supplier<T> memoize(Supplier<T> supplier) {
        return new MemoizeSupplier<>(supplier);
    }



    /**
     * Extension of {@link Wrapper} that implements {@link Supplier}.
     */
    protected static abstract class SupplierWrapper<T, W> extends Wrapper<W> implements CloseableSupplier<T> {
        public SupplierWrapper(W wrapped, String reason) {
            super(wrapped, reason);
        }
    }

    protected static class MemoizeSupplier<T> extends SupplierWrapper<T, Supplier<T>> {

        transient T value;

        transient volatile boolean evaluated = false;

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
