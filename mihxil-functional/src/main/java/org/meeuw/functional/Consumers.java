package org.meeuw.functional;

import java.util.function.*;

/**
 * Provides several utilities related to {@link Consumer}, {@link BiConsumer} and {@link TriConsumer}.
 * <p>
 * The resulting new object consistently implement {@link #hashCode()} and {@link #equals(Object)}. The point is that these kind of things won't happen if you use lambdas.
 *
 * @author Michiel Meeuwissen
 * @since 0.1
 */
public final class Consumers {

    private Consumers() {
    }

    /**
     * Creates a new Runnable implemented using a {@link Consumer}, simply always consuming that same value.
     * @since 1.11
     * @param <U> the type {@link Consumer} argument
     * @param consumer The consumer to adapt
     * @param value the value to consume
     * @return A new Runnable that will call the given {@link Consumer} with the given value every time it is run.
     */
    public static <U> Runnable withArg1(Consumer<U> consumer, U value) {
        return new RunnableWrapper<Consumer<U>>(consumer, value, "with arg1 " + value) {
            @Override
            public void run() {
                wrapped.accept(value);
            }
        };
    }

    /**
     * Creates a new Runnable implemented using a {@link Consumer}, simply always consuming that same value.
     * @since 1.11
     * @param <U> the type {@link Consumer} argument
     * @param consumer The consummer to adapt
     * @param valueSupplier supplier for the value to consume
     * @return A new Runnable that will call the given {@link Consumer} with value supplied by the given supplier
     */
    public static <U> Runnable withArg1Supplier(Consumer<U> consumer, Supplier<U> valueSupplier) {
        return new RunnableWrapper<Consumer<U>>(consumer, valueSupplier, "with arg1 " + valueSupplier) {
            @Override
            public void run() {
                wrapped.accept(valueSupplier.get());
            }
        };
    }


    /**
     * Creates a new {@link TriConsumer} but implement it using a {@link BiConsumer}, simply completely ignoring the third argument.
     * <p>
     * The resulting object implements {@link #equals(Object)} and {@link #hashCode()} based on the parameter.
     *
     * @param <T> the type of the first argument to the resulting {@code TriConsumer} and of the {@code BiConsumer} argument
     * @param <U> the type of the second argument of the resulting {@code TriConsumer} and of the {@code BiConsumer} argument
     * @param <V> the type of the third argument of the resulting {@code TriConsumer} (which will be ignored)
     * @param biConsumer the {@code BiConsumer} to adapt
     * @return a new {@code TriConsumer} that passes its first and second argument to the given {@link BiConsumer}
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
     * Creates a new {@link TriConsumer} but implement it using a {@link BiConsumer}, simply completely ignoring the second argument.
     * <p>
     * The resulting object implements {@link #equals(Object)} and {@link #hashCode()} based on the parameter.
     *
     * @param <T> the type of the first argument of the resulting {@code TriConsumer} and of the {@code BiConsumer} argument
     * @param <U> the type of the second argument of the resulting {@code TriConsumer} (which will be ignored)
     * @param <V> the type of the thirds argument of the resulting {@code TriConsumer} and the second one of the {@code BiConsumer} argument
     * @param biConsumer the {@code BiConsumer} to adapt
     * @return a new {@code TriConsumer} that passes its first and third argument to the given {@link BiConsumer}
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
     * Creates a new {@link TriConsumer} but implement it using a {@link BiConsumer}, simply completely ignoring the first argument.
     * <p>
     * The resulting object implements {@link #equals(Object)} and {@link #hashCode()} based on the parameter.
     *
     * @param <T> the type of the first argument of the resulting {@code TriConsumer} which will be ignored
     * @param <U> the type of the second argument of the resulting {@code TriConsumer} and the first one of the {@code BiConsumer} argument
     * @param <V> the type of the third argument of the resulting {@code TriConsumer} and the second one of the {@code BiConsumer} argument
     * @param biConsumer The {@code BiConsumer} to adapt
     * @return A new {@code TriConsumer} that passes its second and third argument to the given {@link BiConsumer}
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
     * Creates a new {@link BiConsumer} but implement it using a {@link Consumer}, simply completely ignoring the second argument.
     * <p>
     * The resulting object implements {@link #equals(Object)} and {@link #hashCode()} based on the parameter.
     *
     * @param <T> the type of the first argument of the resulting {@code BiConsumer} and of the {@code Consumer} argument
     * @param <U> the type of the second argument of the resulting {@code BiConsumer} (which will be ignored)
     * @param consumer The {@code Consumer} to adapt
     * @return A new {@code BiConsumer} that passes first argument to the given {@link Consumer}
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
     * Creates a new {@link BiPredicate} but implement it using a {@link Predicate}, simply completely ignoring the first argument.
     * <p>
     * The resulting object implements {@link #equals(Object)} and {@link #hashCode()} based on the parameter.
     * @param <T> the type of the first argument of the resulting {@code BiConsumer} (which will be ignored)
     * @param <U> the type of the second argument of the resulting {@code BiConsumer} and the type of the input of the {@code Consumer} argument
     * @param consumer the {@code Consumer} to adapt
     * @return a new {@code BiConsumer} that passes second argument to the given {@link Consumer}
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
     * <p>
     * The resulting object implements {@link #equals(Object)} and {@link #hashCode()} based on the two parameters.
     *
     * @param <U> the type of the first argument of the given {@code BiConsumer}, which value is fixed
     * @param <V> the type of first argument of the resulting {@code Consumer}, and of the second argument of the given {@code BiConsumer}
     * @param biConsumer The {@code BiConsumer} that will be used to implement a new {@code Consumer}
     * @param value The fixed value for the first argument of the given {@code BiConsumer}
     * @return a new {@code Consumer} that is calling the given {@code BiConsumer}, and uses its own argument for the second parameter, and a fixed value for the first parameter
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
     * <p>
     * The resulting object implements {@link #equals(Object)} and {@link #hashCode()} based on the two parameters.
     *
     * @param <U> the type of the first argument of the given {@code BiConsumer}, which value is determined by the given {@code Supplier<U>}
     * @param <V> the type of the argument of the resulting {@code Consumer}, and of the second argument of the given {@code BiConsumer}
     * @param biConsumer the {@code BiConsumer} that will be used to implement a new {@code Consumer}
     * @param valueSupplier a supplier for the value for the first argument of the given {@code BiConsumer}, which will be called on every call to the resulting {@code Consumer}
     * @return a new {@code Consumer} that is calling the given {@code BiConsumer}, and uses its own argument for the second parameter, and a fixed value for the first parameter
     *
     * @see TriConsumer#withArg1(Object)
     */
    public static <U, V> Consumer<V> withArg1Supplier(BiConsumer<U, V> biConsumer, Supplier<U> valueSupplier) {
        return new MonoWrapper<BiConsumer<U, V>, V>(biConsumer, valueSupplier, "with arg1 " + valueSupplier) {
            @Override
            public void accept(V v) {
                wrapped.accept(valueSupplier.get(), v);
            }
        };
    }

    /**
     * Morphs a given {@link BiConsumer} into a {@link Consumer}, which a certain given value for the second argument.
     * <p>
     * The resulting object implements {@link #equals(Object)} and {@link #hashCode()} based on the two parameters.
     *
     * @param <U> the type of the first argument of the given {@code BiConsumer}, which will be argument of the resulting {@code Consumer}
     * @param <V> the type of the second argument of the given {@code BiConsumer}, of which the value will be fixed to {@code value}
     * @param biConsumer The {@code BiConsumer} that will be used to implement a new {@code Consumer}
     * @param value The fixed value for the first argument of the given {@code BiConsumer}
     * @return a new {@code Consumer} that is calling the given {@code BiConsumer}, and uses its own argument for the second parameter, and a fixed value for the first parameter
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
     * Morphs a given {@link BiConsumer} into a {@link Consumer}, which a certain supplied value for the second argument.
     * The resulting object implements {@link #equals(Object)} and {@link #hashCode()} based on the two parameters.
     *
     * @param <U> the type of the first argument of the given {@code BiConsumer}, which will be the argument of the resulting {@code Consumer}
     * @param <V> the type of the second argument of the given {@code BiConsumer}, which value is determined by the given {@code Supplier<U>}
     * @param biConsumer the {@code BiConsumer} that will be used to implement a new {@code Consumer}
     * @param valueSupplier a supplier for the value for the second argument of the given {@code BiConsumer}, which will be called on every call to the resulting {@code Consumer}
     * @return a new {@code Consumer} that is calling the given {@code BiConsumer}, and uses its own argument for the second parameter, and a fixed value for the first parameter
     * @see TriConsumer#withArg2(Object)
     */
    public static <U, V> Consumer<U> withArg2Supplier(BiConsumer<U, V> biConsumer, Supplier<V> valueSupplier) {
        return new MonoWrapper<BiConsumer<U, V>, U>(biConsumer, valueSupplier, "with arg2 " + valueSupplier) {
            @Override
            public void accept(U u) {
                wrapped.accept(u, valueSupplier.get());
            }
        };
    }

    /**
     * Produces consumer that complete ignores what it consumes.
     * <p>
     * All such 'nop' are equal, if at least they consume objects of the same class.
     *
     * @param <U> The type of the objects this NOP Consumer 'consumes'
     * @param clazz The class of this type
     * @return A new {@link NOPConsumer}
     */
    public static  <U> Consumer<U> nop(Class<U> clazz) {
        return new NOPConsumer<>(clazz);
    }

    /**
     * Produces {@link BiConsumer} that complete ignores what it consumes.
     * <p>
     * All such 'nop' are equal, if at least they consume objects of the same class.
     *
     * @param <U> The first type of the objects this BiConsumer 'consumes'
     * @param <V> The second type of the objects this BiConsumer 'consumes'
     * @param clazz1 The class of this first type
     * @param clazz2 The class of this second type
     * @return A new {@link NOPBiConsumer}
     */
    public static  <U, V> BiConsumer<U, V> biNop(Class<U> clazz1, Class<V> clazz2) {
        return new NOPBiConsumer<>(clazz1, clazz2);
    }

    /**
     * Produces {@link TriConsumer} that complete ignores what it consumes.
     * <p>
     * All such 'nop' are equal, if at least they consume objects of the same class.
     *
     * @param <U> The first type of the objects this NOP Consumer 'consumes'
     * @param <V> The second type of the objects this NOP Consumer 'consumes'
     * @param <W> The third type of the objects this NOP Consumer 'consumes'
     * @param clazz1 The class of this first type
     * @param clazz2 The class of this second type
     * @param clazz3 The class of this third type
     * @return A new {@link NOPTriConsumer}
     */
    public static  <U, V, W> TriConsumer<U, V, W> triNop(Class<U> clazz1, Class<V> clazz2, Class<W> clazz3) {
        return new NOPTriConsumer<>(clazz1, clazz2, clazz3);
    }




    private static final Consumer<Object> NOP = nop(Object.class);

    private static  final BiConsumer<Object, Object> BINOP = biNop(Object.class, Object.class);
    private static  final TriConsumer<Object, Object, Object> TRINOP = triNop(Object.class, Object.class, Object.class);

    /**
     * Produces consumer that complete ignores what it consumes.
     *
     * @param <U> The type of objects to consume
     * @return Always the same instance, a consumer accepting and ignoring everything
     */
    @SuppressWarnings("unchecked")
    public static  <U> Consumer<U> nop() {
        return (Consumer<U>) NOP;
    }

    /**
     * Returns {@link BiConsumer} that complete ignores what it consumes.
     * @param <U> The first type of the objects this BiConsumer 'consumes'
     * @param <V> The second type of the objects this BiConsumer 'consumes'
     * @return Always the same instance, a BiConsumer accepting and ignoring everything
     */
    @SuppressWarnings("unchecked")
    public static  <U, V> BiConsumer<U, V> biNop() {
        return (BiConsumer<U, V>) BINOP;
    }

    /**
     * Produces consumer that complete ignores what it consumes.
     *
     * @param <U> The type of objects to consume
     * @param <V> The type of objects to consume
     * @param <W> The type of objects to consume
     * @return Always the same instance, a consumer accepting and ignoring everything
     */
    @SuppressWarnings("unchecked")
    public static  <U, V, W> TriConsumer<U, V, W> triNop() {
        return (TriConsumer<U, V, W>) TRINOP;
    }

    protected static class NOPConsumer<U> implements ThrowAnyConsumer<U> {
        final Class<U> clazz;

        public NOPConsumer(Class<U> clazz) {
            this.clazz = clazz;
        }

        @Override
        public void acceptThrows(U u) {
        }

        @Override
        public int hashCode() {
            return clazz.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof NOPConsumer && ((NOPConsumer<?>) o).clazz.equals(clazz);
        }

        @Override
        public String toString() {
            return "NOP";
        }
    }

      protected static class NOPBiConsumer<U, V> implements ThrowAnyBiConsumer<U, V> {
        final Class<U> clazz1;
        final Class<V> clazz2;

        public NOPBiConsumer(Class<U> clazz1, Class<V> clazz2) {
            this.clazz1 = clazz1;
            this.clazz2 = clazz2;
        }

        @Override
        public void acceptThrows(U u, V v) {
        }

        @Override
        public int hashCode() {
            return clazz1.hashCode() + 13 * clazz2.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof NOPBiConsumer &&
                ((NOPBiConsumer<?, ?>) o).clazz1.equals(clazz1) &&
                ((NOPBiConsumer<?, ?>) o).clazz2.equals(clazz2);
        }

        @Override
        public String toString() {
            return "NOP";
        }
    }

    protected static class NOPTriConsumer<U, V, W> implements ThrowAnyTriConsumer<U, V, W> {
        final Class<U> clazz1;
        final Class<V> clazz2;
        final Class<W> clazz3;


        public NOPTriConsumer(Class<U> clazz1, Class<V> clazz2, Class<W> clazz3) {
            this.clazz1 = clazz1;
            this.clazz2 = clazz2;
            this.clazz3 = clazz3;
        }

        @Override
        public void acceptThrows(U u, V v, W w) {
        }

        @Override
        public int hashCode() {
            return clazz1.hashCode() + 13 * clazz2.hashCode() + 21 * clazz3.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof NOPTriConsumer &&
                ((NOPTriConsumer<?, ?, ?>) o).clazz1.equals(clazz1) &&
                ((NOPTriConsumer<?, ?, ?>) o).clazz2.equals(clazz2) &&
                ((NOPTriConsumer<?, ?, ?>) o).clazz3.equals(clazz3)
                ;
        }

        @Override
        public String toString() {
            return "NOP";
        }
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

    /**
     * Abstract base class for implementing {@link Consumer}s based on wrapping something else.
     * @param <W>
     */
    protected static abstract class RunnableWrapper<W> extends ValueWrapper<W> implements Runnable {

        public RunnableWrapper(W wrapped, Object value,  String reason) {
            super(wrapped, value, reason);
        }
    }

    protected static abstract class ThrowingMonoWrapper<W, T, E extends Exception> extends ValueWrapper<W> implements ThrowingConsumer<T, E> {

        public ThrowingMonoWrapper(W wrapped, Object value,  String reason) {
            super(wrapped, value, reason);
        }
    }
    protected static abstract class ThrowingBiWrapper<W, T, U, E extends Exception> extends ValueWrapper<W> implements ThrowingBiConsumer<T, U, E> {

        public ThrowingBiWrapper(W wrapped, Object value,  String reason) {
            super(wrapped, value, reason);
        }
    }


    protected static abstract class ThrowingTriWrapper<W, T, U, V, E extends Exception> extends ValueWrapper<W> implements ThrowingTriConsumer<T, U, V, E> {

        public ThrowingTriWrapper(W wrapped, Object value,  String reason) {
            super(wrapped, value, reason);
        }
    }

}
