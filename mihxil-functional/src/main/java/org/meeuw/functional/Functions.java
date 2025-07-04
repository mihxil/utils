package org.meeuw.functional;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.*;

/**
 * A place for some 'Function' related utilities.
 * <p>
 * Provides e.g. function implementations which always return the same value, no matter their arguments (like, {@link #always(Object)}, {@link #biAlways(Object)}, {@link #triAlways(Object)}, {@link #quadriAlways(Object)}), with a nicer toString/equals then a standard lambda would do.
 * <p>
 * Also, this statically provides the several {@code withArgX} and {@code ignoreArgX} methods (so they are also accessible
 * for implementations of {@link Function} and {@link BiFunction}).
 *
 * @author Michiel Meeuwissen
 * @since 0.1
 */

public final class Functions {

    private Functions() {
    }

    /**
     * Provides an implementation of {@link Function} always returning the same value
     *
     * @param <A> the type of the argument to the function (which will be ignored)
     * @param <R> the type of the return value of the function
     * @param returnValue the desired value to always return
     * @param description a description of the function. Defaults to "always " + {@code returnValue}
     * @return a new function with the described behaviour
     */
    public static <A, R> Function<A, R> always(R returnValue, String description) {
        return new Always<>(returnValue, description);
    }

    /**
     * @param <A> the type of the argument to the function (which will be ignored)
     * @param <R> the type of the return value of the function
     * @param returnValue the desired value to always return
     * @return a new {@code BiFunction} with the described behaviour
     * @see #always(Object, String)
     */
    public static <A, R> Function<A, R> always(R returnValue) {
        return always(returnValue, "always " + returnValue);
    }

    /**
     * Provides an implementation of {@link BiFunction} always returning the same value
     *
     * @param <A1> the type of the first argument to the function (which will be ignored)
     * @param <A2> the type of the second argument to the function (which will be ignored)
     * @param <R>  the type of the return value
     * @param returnValue the desired value to always return
     * @param description a description of the function. Defaults to "always " + {@code returnValue}
     * @return a new {@code BiFunction} with the described behaviour
     */
    public static <A1, A2,  R> BiFunction<A1, A2, R> biAlways(R returnValue, String description) {
        return new BiAlways<>(returnValue, description);
    }

    /**
     * @param <A1> the type of the first argument to the function (which will be ignored)
     * @param <A2> the type of the second argument to the function (which will be ignored)
     * @param <R>  the type of the return value
     * @param returnValue the desired value to always return
     * @return a new {@code BiFunction} with the described behaviour
     * @see #biAlways(Object, String)
     */
    public static <A1, A2, R> BiFunction<A1, A2, R> biAlways(R returnValue) {
        return biAlways(returnValue, "always " + returnValue);
    }

    /**
     * Provides an implementation of {@link TriFunction} always returning the same value
     *
     * @param <A1> the type of the first argument to the function (which will be ignored)
     * @param <A2> the type of the second argument to the function (which will be ignored)
     * @param <A3> the type of the third argument to the function (which will be ignored)
     * @param <R>  the type of the return value
     * @param returnValue the desired value to always return
     * @param description a description of the function. Defaults to "always " + {@code returnValue}
     * @return a new {@code TriFunction} with the described behaviour
     */
    public static <A1, A2, A3,  R> TriFunction<A1, A2, A3,  R> triAlways(R returnValue, String description) {
        return new TriAlways<>(returnValue, description);
    }

    /**
     *
     * @param <A1> the type of the first argument to the function (which will be ignored)
     * @param <A2> the type of the second argument to the function (which will be ignored)
     * @param <A3> the type of the third argument to the function (which will be ignored)
     * @param <R>  the type of the return value
     * @param returnValue the desired value to always return
     * @return a new {@code TriFunction} with the described behaviour
     * @see #triAlways(Object, String)
     */
    public static <A1, A2, A3,  R> TriFunction<A1, A2, A3,  R> triAlways(R returnValue) {
        return triAlways(returnValue, "always " + returnValue);
    }

    /**
     * Provides an implementation of {@link QuadriFunction} always returning the same value
     * @param <A1> the type of the first argument to the function (which will be ignored)
     * @param <A2> the type of the second argument to the function (which will be ignored)
     * @param <A3> the type of the third argument to the function (which will be ignored)
     * @param <A4> the type of the fourth argument to the function (which will be ignored)
     * @param <R>  the type of the return value
     * @param returnValue the desired value to always return
     * @param description a description of the function. Defaults to "always " + {@code returnValue}
     * @return a new {@code QuadriFunction} with the described behaviour
     */
    public static <A1, A2, A3, A4,  R> QuadriFunction<A1, A2, A3, A4,  R> quadriAlways(R returnValue, String description) {
        return new QuadriAlways<>(returnValue, description);
    }

    /**
     * @param <A1> the type of the first argument to the function (which will be ignored)
     * @param <A2> the type of the second argument to the function (which will be ignored)
     * @param <A3> the type of the third argument to the function (which will be ignored)
     * @param <A4> the type of the fourth argument to the function (which will be ignored)
     * @param <R>  the type of the return value
     * @param returnValue the desired value to always return
     * @return a new {@code QuadriFunction} with the described behaviour
     * @see #quadriAlways(Object, String)
     */
    public static <A1, A2, A3, A4, R> QuadriFunction<A1, A2, A3,  A4, R> quadriAlways(R returnValue) {
        return quadriAlways(returnValue, "always " + returnValue);
    }

    /**
     * Morphs a given {@link Function} into a {@link Supplier}, with a certain given value for the first argument.
     *
     * @param <A1> the type of the argument to the function (which will be provided)
     * @param <R> the return value of the function, and of the resulting {@link Supplier}
     * @param function the function on which to base the new {@code Supplier} on
     * @param value the argument to always supply to the function
     * @return a new supplier, which will use the given function and argument for its implementation
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
     * Giving a {@link ThrowingFunction function}, morphs it into a {@link Callable} that ignores its argument and just calls the function always with {@code value}.
     *
     * @param <A1> the type of the argument to the function (which will be provided)
     * @param <R> the return value of the function, and of the resulting {@link Callable}
     * @param <E> the exception type that the function (and the callable) can throw
     * @param function the function on which to base the new {@code Callable} on
     * @param value the argument to always supply to the function
     * @return a new callable, which will use the given function and argument for its implementation
     * @since 0.14
     */
    public static <A1, R, E extends Exception> Callable<R> withArg1(ThrowingFunction<A1, R, E> function, A1 value) {
        return new CallableWrapper<ThrowingFunction<A1, R, E>, R>(function, "with  arg1 " + value) {
            @Override
            public R call() throws E {
                return wrapped.applyWithException(value);
            }
        };
    }

    /**
     * Giving a {@link ThrowingFunction function}, morphs it into a {@link Callable} that ignores its argument and just calls the function with {@code null}.
     *
     * @param <A1> the type of the argument to the function (which will be {@code null})
     * @param <R> the return value of the function, and of the resulting {@link Callable}
     * @param <E> the exception type that the function (and the callable) can throw
     * @param function the function on which to base the new {@code Callable} on
     * @return a new callable, which will use the given function and argument for its implementation
     * @since 0.14
     */
    public static <A1, R, E extends Exception> Callable<R> withNull(ThrowingFunction<A1, R, E> function) {
        return withArg1(function, null);
    }


    /**
     * Giving a {@link ThrowingFunction function}, morphs it into a {@link Callable} that ignores its argument and just calls the function with {@code null}.
     *
     * @param <R> the return value of the function, and of the resulting {@link Callable}
     * @param <E> the exception type that the function (and the callable) can throw
     * @param callable the function on which to base the new {@code Callable} on
     * @return a new function, which will use the given call its implementation. The argument of the function will be ignored, all calls just call the {@code Callable}.
     * @since 0.14
     */
    public static <R, E extends Exception > ThrowingFunction<Void, R, E> ignoreArg1(Callable<R> callable) {
        return new ThrowingMonoWrapper<Callable<R>, Void, R,  E>(callable, null, "function") {
            @Override
            public R applyWithException(Void o) throws E {
                try {
                    return callable.call();
                } catch (Exception e) {
                    return Sneaky.sneakyThrow(e);
                }
            }
        };
    }

    /**
     * Morphs a given {@link BiFunction} into a {@link Function}, with a certain given value for the second argument.
     * @param <A1> the type of the first argument to the function which will be the type of the only argument of the resulting {@code Function}
     * @param <A2> the type of the second argument to the function (which will be provided)
     * @param <R> the return value of the function, and of the resulting {@link Function}
     * @param function the function on which to base the new {@code Supplier} on
     * @param value the value to always supply to the {@code BiFunction}'s second argument
     * @return a new {@code Function}, which will use the given {@code BiFunction} and argument for its implementation
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
     * @param <A1> the type of the first argument to the function (which will be provided)
     * @param <A2> the type of the second argument to the function  which will be the type of the only argument of the resulting {@code Function}
     * @param <R> the return value of the function, and of the resulting {@link Function}
     * @param function the function on which to base the new {@code Supplier} on
     * @param value the value to always supply to the {@code BiFunction}'s first argument
     * @return a new {@code Function}, which will use the given {@code BiFunction} and argument for its implementation
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
     * @param <T> type of first argument to the resulting {@link BiFunction}
     * @param <U> type of second argument to the resulting {@link BiFunction} (ignored)
     * @param <R> type of result value
     * @param function The function used to implement the new {@code BiFunction}
     * @return a new {@code BiFunction}, which will use the given {@code Function} for its implementation
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
     * @param <T> type of first argument to the resulting {@link BiFunction} (ignored)
     * @param <U> type of second argument to the resulting {@link BiFunction}
     * @param <R> type of result value
     * @param function The function used to implement the new {@code BiFunction}
     * @return a new {@code BiFunction}, which will use the given {@code Function} for its implementation
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
     * Creates a new {@link TriFunction} but implement it using a {@link BiFunction}, simply completely ignoring the second argument
     * @param <T> type of first argument to the resulting {@link TriFunction}
     * @param <U> type of second argument to the resulting {@link TriFunction}
     * @param <V> type of third argument to the resulting {@link TriFunction}(ignored)
     * @param <R> type of result value
     * @param function The function used to implement the new {@code TriFunction}
     * @return a new {@code TriFunction}, which will use the given {@code BiFunction} for its implementation
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
     * @param <T> type of first argument to the resulting {@link TriFunction}
     * @param <U> type of second argument to the resulting {@link TriFunction} (ignored)
     * @param <V> type of third argument to the resulting {@link TriFunction}
     * @param <R> type of result value
     * @param function The function used to implement the new {@code TriFunction}
     * @return a new {@code TriFunction}, which will use the given {@code BiFunction} for its implementation
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
     * @param <T> type of first argument to the resulting {@link TriFunction} (ignored)
     * @param <U> type of second argument to the resulting {@link TriFunction}
     * @param <V> type of third argument to the resulting {@link TriFunction}
     * @param <R> type of result value
     * @param function The function used to implement the new {@code TriFunction}
     * @return a new {@code TriFunction}, which will use the given {@code BiFunction} for its implementation
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
     * @param <T> type of first argument to the resulting {@link QuadriFunction}
     * @param <U> type of second argument to the resulting {@link QuadriFunction}
     * @param <V> type of third argument to the resulting {@link QuadriFunction}
     * @param <W> type of fourth argument to the resulting {@link QuadriFunction} (ignored)
     * @param <R> type of result value
     * @param function the {@code TriFunction} to adapt
     * @return the new {@code QuadriFunction}
     * @see TriFunction#ignoreArg4()
     */
    public static <T, U, V, W, R> QuadriFunction<T, U, V, W,  R> ignoreArg4(TriFunction<T, U, V,  R> function) {
        return function.ignoreArg4();
    }

    /**
     * Creates a new {@link QuadriFunction} but implement it using a {@link TriFunction}, simply completely ignoring the third argument
     * Just calls {@link TriFunction#ignoreArg3()}
     * @param <T> type of first argument to the resulting {@link QuadriFunction}
     * @param <U> type of second argument to the resulting {@link QuadriFunction}
     * @param <V> type of third argument to the resulting {@link QuadriFunction} (ignored)
     * @param <W> type of fourth argument to the resulting {@link QuadriFunction}
     * @param <R> type of result value
     * @param function the {@code TriFunction} to adapt
     * @return the new {@code QuadriFunction}
     * @see TriFunction#ignoreArg3()
     */
    public static <T, U, V, W, R> QuadriFunction<T, U, V, W,  R> ignoreArg3(TriFunction<T, U, W,  R> function) {
        return function.ignoreArg3();
    }

    /**
     * Creates a new {@link QuadriFunction} but implement it using a {@link TriFunction}, simply completely ignoring the first argument
     * Just calls {@link TriFunction#ignoreArg2()}
     * @param <T> type of first argument to the resulting {@link QuadriFunction}
     * @param <U> type of second argument to the resulting {@link QuadriFunction} (ignored)
     * @param <V> type of third argument to the resulting {@link QuadriFunction}
     * @param <W> type of fourth argument to the resulting {@link QuadriFunction}
     * @param <R> type of result value
     * @param function the {@code TriFunction} to adapt
     * @return the new {@code QuadriFunction}
     * @see TriFunction#ignoreArg2()
     */
    public static <T, U, V, W, R> QuadriFunction<T, U, V, W,  R> ignoreArg2(TriFunction<T, V, W,  R> function) {
        return function.ignoreArg2();
    }

    /**
     * Creates a new {@link QuadriFunction} but implement it using a {@link TriFunction}, simply completely ignoring the first argument
     * Just calls {@link TriFunction#ignoreArg1()}
     * @param <T> type of first argument to the resulting {@link QuadriFunction} (ignored)
     * @param <U> type of second argument to the resulting {@link QuadriFunction}
     * @param <V> type of third argument to the resulting {@link QuadriFunction}
     * @param <W> type of fourth argument to the resulting {@link QuadriFunction}
     * @param <R> type of result value
     * @param function the {@code TriFunction} to adapt
     * @return the new {@code QuadriFunction}
     * @see TriFunction#ignoreArg1()
     */
    public static <T, U, V, W, R> QuadriFunction<T, U, V, W,  R> ignoreArg1(TriFunction<U, V, W,  R> function) {
        return function.ignoreArg1();
    }


    @SuppressWarnings("rawtypes")
    private static final UnaryOperator IDENTITY = new UnaryOperator() {
        @Override
        public Object apply(Object o) {
            return o;
        }

        @Override
        public String toString() {
            return "identity";
        }


        @Override
        public int hashCode() {
            return 0;
        }
    };

    /**
     * Returns a function that always returns its input argument.
     * @since 1.11
     * @param <T> the type of the input and output of the function
     * @return a function that returns its input argument
     */
    @SuppressWarnings("unchecked")
    public static <T> UnaryOperator<T> identity() {
        return (UnaryOperator<T>) IDENTITY;
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



    protected static abstract  class ThrowingMonoWrapper<W, X, R, E extends Exception>  extends ValueWrapper<W> implements ThrowingFunction<X, R, E> {
        public ThrowingMonoWrapper(W wrapped, Object value, String why) {
            super(wrapped, value, why);
        }
    }


    /**
     * A wrapper that is  {@link Callable}
     * @param <W>
     * @param <X>
     */
    protected static abstract  class CallableWrapper<W, X>  extends Wrapper<W> implements Callable<X> {
        public CallableWrapper(W wrapped, String why) {
            super(wrapped, why);
        }
    }




}
