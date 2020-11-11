package org.meeuw.functional;

import java.util.Objects;
import java.util.function.*;

/**
 * A place for some 'Function' related utilities.
 *
 * Provides functions implementation which always return the same value, no matter their arguments.
 *
 * With a nicer toString/equals then a standard lambda would do.
 *
 * Also, this statically provides the several {@code withArgX} and {@code ignoreArgX} methods (so they are also accessible
 * for implementions of {@link Function} and {@link BiFunction}).
 *
 * @author Michiel Meeuwissen
 * @since 0.1
 */

public final class Functions {

    private Functions() {
    }

    /**
     * Provides an implementation of {@link Function} always returning the same value
     * @param returnValue The desired value to always return
     * @param description A description of the function. Defaults to "always " + {@code returnValue}
     */
    public static <A, R> Function<A, R> always(R returnValue, String description) {
        return new Always<>(returnValue, description);
    }

    /**
     * @see #always(Object, String)
     */
    public static <A, R> Function<A, R> always(R returnValue) {
        return always(returnValue, "always " + returnValue);
    }

    /**
     * Provides an implementation of {@link BiFunction} always returning the same value
     * @param returnValue The desired value to always return
     * @param description A description of the function. Defaults to "always " + {@code returnValue}
     */
    public static <A1, A2,  R> BiFunction<A1, A2, R> biAlways(R returnValue, String description) {
        return new BiAlways<>(returnValue, description);
    }

    /**
     * @see #biAlways(Object, String)
     */
    public static <A1, A2, R> BiFunction<A1, A2, R> biAlways(R returnValue) {
        return biAlways(returnValue, "always " + returnValue);
    }


    /**
     * Provides an implementation of {@link TriFunction} always returning the same value
     * @param returnValue The desired value to always return
     * @param description A description of the function. Defaults to "always " + {@code returnValue}
     */
    public static <A1, A2, A3,  R> TriFunction<A1, A2, A3,  R> triAlways(R returnValue, String description) {
        return new TriAlways<>(returnValue, description);
    }

    /**
     * @see #triAlways(Object, String)
     */
    public static <A1, A2, A3,  R> TriFunction<A1, A2, A3,  R> triAlways(R v) {
        return triAlways(v, "always " + v);
    }

    /**
     * Provides an implementation of {@link QuadriFunction} always returning the same value
     * @param returnValue The desired value to always return
     * @param description A description of the function. Defaults to "always " + {@code returnValue}
     */
    public static <A1, A2, A3, A4,  R> QuadriFunction<A1, A2, A3, A4,  R> quadriAlways(R returnValue, String description) {
        return new QuadriAlways<>(returnValue, description);
    }

     /**
     * @see #quadriAlways(Object, String)
     */
    public static <A1, A2, A3, A4, R> QuadriFunction<A1, A2, A3,  A4, R> quadriAlways(R v) {
        return quadriAlways(v, "always " + v);
    }

    /**
     * Morphs a given {@link Function} into a {@link Supplier}, with a certain given value for the first argument.
     *
     * @see TriFunction#withArg1(Object)
     */
    public static <A1, R> Supplier<R> withArg1(Function<A1, R> function, A1 value) {
        return new Suppliers.SupplierWrapper<R, Function<A1, R>>(function, "with  arg1 " + value) {
            @Override
            public R get() {
                return wrapped.apply(value);
            }
        };
    }

    /**
     * Morphs a given {@link BiFunction} into a {@link Function}, with a certain given value for the second argument.
     *
     * @see TriFunction#withArg2(Object)
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
     * Morphs a given {@link BiFunction} into a {@link Function}, with a certain given value for the first argument.
     *
     * @see TriFunction#withArg1(Object)
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
     * Just calls {@link TriFunction#ignoreArg4()}
     */
    public static <T, U, V, W, R> QuadriFunction<T, U, V, W,  R> ignoreArg4(TriFunction<T, U, V,  R> function) {
        return function.ignoreArg4();
    }

    /**
     * Creates a new {@link QuadriFunction} but implement it using a {@link TriFunction}, simply completely ignoring the third argument
     * Just calls {@link TriFunction#ignoreArg3()}
     */
    public static <T, U, V, W, R> QuadriFunction<T, U, V, W,  R> ignoreArg3(TriFunction<T, U, W,  R> function) {
        return function.ignoreArg3();
    }

    /**
     * Creates a new {@link QuadriFunction} but implement it using a {@link TriFunction}, simply completely ignoring the first argument
     * Just calls {@link TriFunction#ignoreArg2()}
     */
    public static <T, U, V, W, R> QuadriFunction<T, U, V, W,  R> ignoreArg2(TriFunction<T, V, W,  R> function) {
        return function.ignoreArg2();
    }

    /**
     * Creates a new {@link QuadriFunction} but implement it using a {@link TriFunction}, simply completely ignoring the first argument
     * Just calls {@link TriFunction#ignoreArg1()}
     */
    public static <T, U, V, W, R> QuadriFunction<T, U, V, W,  R> ignoreArg1(TriFunction<U, V, W,  R> function) {
        return function.ignoreArg1();
    }

    /**
     * Abstract implementation of function returning always the same value, regardless of their arguments.
     */
    protected static abstract class AbstractAlways<R>  {
        protected final R val;
        private final String toString;

        /**
         * @param val The value to always return
         */
        public AbstractAlways(R val, String toString) {
            this.val = val;
            this.toString = toString;
        }
        @Override
        public String toString() {
            return toString;
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

    /**
     * Represent a fixed value as a  {@link Function}. So this function effectively ignores its parameter.
     */
    protected static final class Always<A, R> extends AbstractAlways<R> implements Function<A, R> {
        public Always(R val, String s) {
            super(val, s);
        }

        @Override
        public R apply(A a) {
            return val;
        }
    }


    /**
     * Represent a fixed value as a  {@link BiFunction}. So this function effectively ignores its parameters.
     */
    protected static final class BiAlways<A1, A2, R> extends AbstractAlways<R> implements BiFunction<A1, A2, R> {
        public BiAlways(R val, String s) {
            super(val, s);
        }
        @Override
        public R apply(A1 a1, A2 a2) {
            return val;
        }
    }

    /**
     * Represent a fixed value as a  {@link TriFunction}. So this function effectively ignores all its parameters.
     */
    protected static final class TriAlways<A1, A2, A3, R> extends AbstractAlways<R> implements TriFunction<A1, A2, A3, R> {
        public TriAlways(R val, String s) {
            super(val, s);
        }

        @Override
        public R apply(A1 a1, A2 a2, A3 a3) {
            return val;
        }
    }

     /**
     * Represent a fixed value as a  {@link QuadriFunction}. So this function effectively ignores all its parameters.
     */
    protected static final class QuadriAlways<A1, A2, A3, A4, R> extends AbstractAlways<R> implements QuadriFunction<A1, A2, A3, A4, R> {
        public QuadriAlways(R val, String s) {
            super(val, s);
        }

        @Override
        public R apply(A1 a1, A2 a2, A3 a3, A4 a4) {
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
