package org.meeuw.functional;

import java.util.function.*;

/**
 * Provides several utility related to {@link Consumer}, {@link BiConsumer} and {@link TriConsumer} l
 * implementing {@link #hashCode()} and {@link #equals(Object)}.
 *
 * The point is that these kind of things won't happen if you use lambdas.
 * @author Michiel Meeuwissen
 * @since 0.1
 */
public final class Consumers {

    private Consumers() {
    }

    /**
     * Creates a new {@link TriConsumer} but implement it using a {@link BiConsumer}, simply completely ignoring the third argument
     */
    public static <T, U, V> TriConsumer<T, U, V> ignoreArg3(BiConsumer<T, U> biConsumer) {
        return new TriWrapper<BiConsumer<T, U>, T, U, V>(biConsumer, null, "ignore arg3") {
            @Override
            public void accept(T t, U u, V v) {
                wrapped.accept(t, u);
            }
        };
    }

    /**
     * Creates a new {@link TriConsumer} but implement it using a {@link BiConsumer}, simply completely ignoring the second argument
     */
    public static <T, U, V> TriConsumer<T, U, V> ignoreArg2(BiConsumer<T, V> biConsumer) {
        return new TriWrapper<BiConsumer<T, V>, T, U, V>(biConsumer, null, "ignore arg2") {
            @Override
            public void accept(T t, U u, V v)  {
                wrapped.accept(t, v);
            }
        };
    }

    /**
     * Creates a new {@link TriConsumer} but implement it using a {@link BiConsumer}, simply completely ignoring the first argument
     */
    public static <T, U, V> TriConsumer<T, U, V> ignoreArg1(BiConsumer<U, V> biConsumer) {
        return new TriWrapper<BiConsumer<U, V>, T, U, V>(biConsumer, null, "ignore arg1") {
            @Override
            public void accept(T t, U u, V v)  {
                wrapped.accept(u, v);
            }
        };
    }

    /**
     * Creates a new {@link BiConsumer} but implement it using a {@link Consumer}, simply completely ignoring the second argument
     */
    public static <T, U> BiConsumer<T, U> ignoreArg2(Consumer<T> consumer) {
        return new BiWrapper<Consumer<T>, T, U>(consumer, null, "ignore arg2") {
            @Override
            public void accept(T t, U u) {
                wrapped.accept(t);
            }
        };
    }

    /**
     * Creates a new {@link BiPredicate} but implement it using a {@link Predicate}, simply completely ignoring the first argument
     */
    public static <T, U> BiConsumer<T, U> ignoreArg1(Consumer<U> consumer) {
        return new BiWrapper<Consumer<U>, T, U>(consumer, null, "ignore arg1") {
            @Override
            public void accept(T t, U u) {
                wrapped.accept(u);
            }
        };
    }

    /**
     * Morphs a given {@link BiConsumer} into a {@link Consumer}, which a certain given value for the first argument.
     *
     * @see  TriConsumer#withArg1(Object)
     */
    public static <U, V> Consumer<V> withArg1(BiConsumer<U, V> biConsumer, U value) {
        return new MonoWrapper<BiConsumer<U, V>, V>(biConsumer, value, "with arg1 " + value) {
            @Override
            public void accept(V v) {
                wrapped.accept(value, v);
            }
        };
    }

    /**
     * Morphs a given {@link BiConsumer} into a {@link Consumer}, which a certain given value for the first argument.
     *
     * @see TriConsumer#withArg1(Object)
     */
    public static <U, V> Consumer<V> withArg1Supplier(BiConsumer<U, V> biConsumer, Supplier<U> u) {
        return new MonoWrapper<BiConsumer<U, V>, V>(biConsumer, u, "with arg1 " + u) {
            @Override
            public void accept(V v) {
                wrapped.accept(u.get(), v);
            }
        };
    }

    /**
     * Morphs a given {@link BiConsumer} into a {@link Consumer}, which a certain given value for the second argument.
     *
     * @see TriConsumer#withArg2(Object)
     */
    public static <U, V> Consumer<U> withArg2(BiConsumer<U, V> biConsumer, V value) {
        return new MonoWrapper<BiConsumer<U, V>, U>(biConsumer, value, "with arg2 " + value) {
            @Override
            public void accept(U u) {
                wrapped.accept(u, value);
            }
        };
    }

    /**
     * Morphs a given {@link BiConsumer} into a {@link Consumer}, which a certain given value for the second argument.
     *
     * @see TriConsumer#withArg2(Object)
     */
    public static <U, V> Consumer<U> withArg2Supplier(BiConsumer<U, V> biConsumer, Supplier<V> v) {
        return new MonoWrapper<BiConsumer<U, V>, U>(biConsumer, v, "with arg2 " + v) {
            @Override
            public void accept(U u) {
                wrapped.accept(u, v.get());
            }
        };
    }

    /**
     * Abstract base class for implementing {@link TriConsumer}s based on wrapping something else.
     */
    protected static abstract  class TriWrapper<W, X, Y, Z> extends ValueWrapper<W> implements TriConsumer<X, Y, Z> {
        public TriWrapper(W wrapped, Object value,  String reason) {
            super(wrapped, value, reason);
        }
    }

    /**
     * Abstract base class for implementing {@link BiConsumer}s based on wrapping something else.
     */
    protected static abstract  class BiWrapper<W, X, Y> extends ValueWrapper<W> implements BiConsumer<X, Y> {

        public BiWrapper(W wrapped, Object value, String reason) {
            super(wrapped, value, reason);
        }
    }

    /**
     * Abstract base class for implementing {@link Consumer}s based on wrapping something else.
     */
    protected static abstract class MonoWrapper<W, X> extends ValueWrapper<W> implements Consumer<X> {

        public MonoWrapper(W wrapped, Object value,  String reason) {
            super(wrapped, value, reason);
        }
    }

}
