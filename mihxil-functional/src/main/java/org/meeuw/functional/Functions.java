package org.meeuw.functional;

import java.util.Objects;
import java.util.function.*;

/**
 * Provides functions implementation which always return the same value, no matter their arguments.
 *
 * With a nicer toString/equals then a standard lambda would do.
 *
 * Also a place for some other 'Function' related utilities.
 *
 * @author Michiel Meeuwissen
 * @since 2.12
 */

public final class Functions {

    private Functions() {
    }

    public static <A, R> Function<A, R> always(R v, String s) {
        return new Always<>(v, s);
    }

    public static <A, R> Function<A, R> always(R v) {
        return always(v, "always " + v);
    }

    public static <A1, A2,  R> BiFunction<A1, A2, R> biAlways(R v, String s) {
        return new BiAlways<>(v, s);
    }

    public static <A1, A2, R> BiFunction<A1, A2, R> biAlways(R v) {
        return biAlways(v, "always " + v);
    }


    public static <A1, A2, A3,  R> TriFunction<A1, A2, A3,  R> triAlways(R v, String s) {
        return new TriAlways<>(v, s);
    }

    public static <A1, A2, A3,  R> TriFunction<A1, A2, A3,  R> triAlways(R v) {
        return triAlways(v, "always " + v);
    }

    /**
     * Morphs a given {@link Function} into a {@link Supplier}, which a certain given value for the first argument.
     *
     * See {@link TriFunction#withArg1(Object)}
     */
    public static <A1, R> Supplier<R> withArg1(Function<A1, R> function, A1 value) {
        return () -> function.apply(value);
    }

    /**
     * Morphs a given {@link BiFunction} into a {@link Function}, which a certain given value for the first argument.
     *
     * See {@link TriFunction#withArg1(Object)}
     */
    public static <A1, A2, R> Function<A2, R> withArg1(BiFunction<A1, A2, R> function, A1 value) {
        return new MonoWrapper<BiFunction<A1, A2, R>, A2, R>(function, value, "with arg1 " + value) {

            @Override
            public R apply(A2 a2) {
                return wrapped.apply(value, a2);
            }
        };
    }

    /**
     * Morphs a given {@link BiFunction} into a {@link Function}, which a certain given value for the second argument.
     *
     * See {@link TriFunction#withArg2(Object)}
     */
    public static <A1, A2, R> Function<A1, R> withArg2(BiFunction<A1, A2, R> function, A2 value) {
        return new MonoWrapper<BiFunction<A1, A2, R>, A1, R>(function, value, "with arg2 " + value) {

            @Override
            public R apply(A1 a1) {
                return wrapped.apply(a1, value);
            }
        };
    }

    /**
     * Creates a new {@link BiFunction} but implement it using a {@link Function}, simply completely ignoring the second argument
     */
    public static <T, U, R> BiFunction<T, U, R> ignoreArg2(Function<T, R> function) {
        return new BiWrapper<Function<T, R>, T, U, R>(function, null, "ignore arg2") {
            @Override
            public R apply(T t, U u) {
                return function.apply(t);
            }
        };
    }

    /**
     * Creates a new {@link BiFunction} but implement it using a {@link Function}, simply completely ignoring the first argument
     */
    public static <T, U, R> BiFunction<T, U, R> ignoreArg1(Function<U, R> function) {
        return new BiWrapper<Function<U, R>, T, U, R>(function, null, "ignore arg1") {
            @Override
            public R apply(T t, U u) {
                return function.apply(u);
            }
        };
    }

    /**
     * Creates a new {@link .TriFunction} but implement it using a {@link BiFunction}, simply completely ignoring the second argument
     */
    public static <T, U, V, R> TriFunction<T, U, V, R> ignoreArg3(BiFunction<T, U, R> function) {
        return new TriWrapper<BiFunction<T, U, R>,  T, U, V, R>(function, null, "ignore arg3") {
            @Override
            public R apply(T t, U u, V v) {
                return function.apply(t, u);
            }
        };
    }

    /**
     * Creates a new {@link TriFunction} but implement it using a {@link BiFunction}, simply completely ignoring the second argument
     */
    public static <T, U, V, R> TriFunction<T, U, V, R> ignoreArg2(BiFunction<T, V, R> function) {
        return new TriWrapper<BiFunction<T, V, R>, T, U, V, R>(function, null, "ignore arg2") {
            @Override
            public R apply(T t, U u, V v) {
                return function.apply(t, v);
            }
        };
    }



    /**
     * Creates a new {@link TriFunction} but implement it using a {@link BiFunction}, simply completely ignoring the first argument
     */
    public static <T, U, V, R> TriFunction<T, U, V, R> ignoreArg1(BiFunction<U, V, R> function) {
        return new TriWrapper<BiFunction<U, V, R>, T, U, V, R>(function, null, "ignore arg1") {
            @Override
            public R apply(T t, U u, V v) {
                return function.apply(u, v);
            }
        };
    }

     /**
     * Creates a new {@link QuadriFunction} but implement it using a {@link TriFunction}, simply completely ignoring the fourth argument
     */
    public static <T, U, V, W, R> QuadriFunction<T, U, V, W,  R> ignoreArg4(TriFunction<T, U, V,  R> function) {
        return new QuadriWrapper<TriFunction<T, U, V, R>, T, U, V, W, R>(function, null, "ignore arg4") {
            @Override
            public R apply(T t, U u, V v, W w) {
                return function.apply(t, u, v);
            }
        };
    }

    /**
     * Creates a new {@link QuadriFunction} but implement it using a {@link TriFunction}, simply completely ignoring the third argument
     */
    public static <T, U, V, W, R> QuadriFunction<T, U, V, W,  R> ignoreArg3(TriFunction<T, U, W,  R> function) {
        return new QuadriWrapper<TriFunction<T, U, W, R>, T, U, V, W, R>(function, null, "ignore arg1") {
            @Override
            public R apply(T t, U u, V v, W w) {
                return function.apply(t, u, w);
            }
        };
    }

    /**
     * Creates a new {@link QuadriFunction} but implement it using a {@link TriFunction}, simply completely ignoring the first argument
     */
    public static <T, U, V, W, R> QuadriFunction<T, U, V, W,  R> ignoreArg2(TriFunction<T, V, W,  R> function) {
        return new QuadriWrapper<TriFunction<T, V, W, R>, T, U, V, W, R>(function, null, "ignore arg1") {
            @Override
            public R apply(T t, U u, V v, W w) {
                return wrapped.apply(t, v, w);
            }
        };
    }

    /**
     * Creates a new {@link QuadriFunction} but implement it using a {@link TriFunction}, simply completely ignoring the first argument
     */
    public static <T, U, V, W, R> QuadriFunction<T, U, V, W,  R> ignoreArg1(TriFunction<U, V, W,  R> function) {
        return new QuadriWrapper<TriFunction<U, V,W, R>, T, U, V, W, R>(function, null, "ignore arg1") {
            @Override
            public R apply(T t, U u, V v, W w) {
                return function.apply(u, v, w);
            }
        };
    }



    protected static abstract class AbstractAlways<R>  {
        protected final R val;
        private final String s;

        public AbstractAlways(R val, String s) {
            this.val = val;
            this.s = s;
        }
        @Override
        public String toString() {
            return s;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AbstractAlways<?> always = (AbstractAlways<?>) o;
            return Objects.equals(val, always.val);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(val);
        }
    }

    protected static final class Always<A, R> extends AbstractAlways<R> implements Function<A, R> {
        public Always(R val, String s) {
            super(val, s);
        }

        @Override
        public R apply(A a) {
            return val;
        }
    }

    protected static final class BiAlways<A1, A2, R> extends AbstractAlways<R> implements BiFunction<A1, A2, R> {
        public BiAlways(R val, String s) {
            super(val, s);
        }
        @Override
        public R apply(A1 a1, A2 a2) {
            return val;
        }
    }

    protected static final class TriAlways<A1, A2, A3, R> extends AbstractAlways<R> implements TriFunction<A1, A2, A3, R> {
        public TriAlways(R val, String s) {
            super(val, s);
        }

        @Override
        public R apply(A1 a1, A2 a2, A3 a3) {
            return val;
        }
    }

    protected static abstract  class TriWrapper<W, X, Y, Z, R> extends ValueWrapper<W> implements TriFunction<X, Y, Z, R> {
        public TriWrapper(W wrapped, Object value, String why) {
            super(wrapped, value, why);
        }
    }

    protected static abstract  class QuadriWrapper<W, A, B, C, D, R> extends ValueWrapper<W> implements QuadriFunction<A, B, C, D, R> {
        public QuadriWrapper(W wrapped, Object value, String why) {
            super(wrapped, value, why);
        }
    }

    protected static abstract  class BiWrapper<W, X, Y, R>  extends ValueWrapper<W> implements BiFunction<X, Y, R> {
        public BiWrapper(W wrapped, Object value, String why) {
            super(wrapped, value, why);
        }
    }

    protected static abstract  class MonoWrapper<W, X, R>  extends ValueWrapper<W> implements Function<X, R> {
        public MonoWrapper(W wrapped, Object value, String why) {
            super(wrapped, value, why);
        }
    }


}
