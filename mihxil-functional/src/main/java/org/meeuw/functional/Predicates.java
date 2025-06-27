package org.meeuw.functional;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Provides several utility related to {@link Predicate}, {@link BiPredicate} and {@link TriPredicate} like versions of {@link #alwaysFalse} and {@link #alwaysTrue}, with nicer {@link #toString()}, and also
 * implementing {@link #hashCode()} and {@link #equals(Object)}.
 * <p>
 * The point is that these kind of things won't happen if you use lambdas.
 * <p>
 * So if they are important than you could use
 * <pre>
 * {@code
 *    Predicate<MyObject> predicate = Predicates.alwaysTrue();
 * }
 * </pre>
 * rather then:
 * <pre>
 *  {@code
 *     Predicate<MyObject> predicate = (o) -> true;
 *  }
 * </pre>
 *
 * @author Michiel Meeuwissen
 * @since 0.1
 */
public final class Predicates {

    private static final String FALSE = "FALSE";
    private static final String TRUE = "TRUE";

    private static final Predicate<Object> ALWAYS_FALSE =  always(false, FALSE);
    private static final Predicate<Object> ALWAYS_TRUE =  always(true, TRUE);


    private Predicates() {
    }

    /**
     * Returns a {@link Predicate} that always results in the same {@code boolean} value
     * @param value The boolean value to always return
     * @param toString The description for that, used in {@link #toString()}
     * @param <T> The type of the (ignored) argument of the predicate
     * @see #alwaysFalse()
     * @see #alwaysTrue()
     * @return A new Predicate, with described properties. It {@code equals} all such predicates wrapping the same {@code value}
     */
    public static <T> Predicate<T> always(boolean value, String toString) {
        return new Always<>(value, toString);
    }


    /**
     * Returns a {@link Predicate} that always results in the same {@code boolean} value
     * @param value The boolean value to always return*
     * @param <T> The type of the (ignored) argument of the predicate
     * @see #alwaysFalse()
     * @see #alwaysTrue()
     * @return A new Predicate, with described properties. It {@code equals} all such predicates wrapping the same {@code value}
     * @since 1.11
     */
    public static <T> Predicate<T> always(boolean value) {
        return value ? alwaysTrue() : alwaysFalse();
    }


    /**
     * Returns a {@link Predicate} that always results {@code false}, regardless of the argument. The toString value
     * is {@link #FALSE}
     * @param <T> The type of the (ignored) argument of the predicate
     * @see #alwaysTrue()
     */
    @SuppressWarnings("unchecked")
    public static <T> Predicate<T> alwaysFalse() {
        return (Predicate<T>) ALWAYS_FALSE;
    }

    /**
     * Returns a {@link Predicate} that always results {@code true}, regardless of the argument. The toString value
     * is {@link #TRUE}
     * @param <T> The type of the (ignored) argument of the predicate
     * @see #alwaysFalse()
     */
    @SuppressWarnings("unchecked")
    public static <T> Predicate<T> alwaysTrue() {
        return (Predicate<T>) ALWAYS_TRUE;
    }

    /**
     * Returns a {@link BiPredicate} that always results in the same {@code boolean} value
     * @param value The boolean value to always return
     * @param toString The description for that, used in {@link #toString()}. If {@code null} then {@link #biAlways(boolean)} is called.
     * @param <T> The type of the (ignored) first argument of the predicate
     * @param <U> The type of the (ignored) second argument of the predicate
     * @see #biAlwaysFalse()
     * @see #biAlwaysTrue()
     */
    public static <T, U> BiPredicate<T, U> biAlways(boolean value, String toString) {
        return toString == null ? biAlways(value) : new BiAlways<>(value, toString);
    }

    /**
     * Returns a {@link BiPredicate} that always results in the same {@code boolean} value. For both values the returned instance will always be the same.
     * @param value The boolean value to always return
     * @param <T> The type of the (ignored) first argument of the predicate
     * @param <U> The type of the (ignored) second argument of the predicate
     * @see #biAlwaysFalse()
     * @see #biAlwaysTrue()
     * @since 0.13
     */
    public static <T, U> BiPredicate<T, U> biAlways(boolean value) {
        return value ? biAlwaysTrue() : biAlwaysFalse();
    }


    /**
     * Returns a {@link BiPredicate} that always results {@code false}, regardless of the argument. The toString value
     * is {@link #FALSE}
     * @param <T> The type of the (ignored) first argument of the predicate
     * @param <U> The type of the (ignored) second argument of the predicate
     * @see #biAlwaysTrue() ()
     */
    public static <T, U> BiPredicate<T, U> biAlwaysFalse() {
        return biAlways(false, FALSE);
    }

     /**
     * Returns a {@link BiPredicate} that always results {@code true}, regardless of the argument. The toString value
     * is {@link #TRUE}
     * @param <T> The type of the (ignored) first argument of the predicate
     * @param <U> The type of the (ignored) second argument of the predicate
     * @see #biAlwaysFalse() ()
     */
    public static <T, U> BiPredicate<T, U> biAlwaysTrue() {
        return biAlways(true, TRUE);
    }

    /**
     * Returns a {@link TriPredicate} that always results in the same {@code boolean} value
     * @param value The boolean value to always return
     * @param toString The description for that, used in {@link #toString()}
     * @param <T> The type of the (ignored) first argument of the predicate
     * @param <U> The type of the (ignored) second argument of the predicate
     * @param <V> The type of the (ignored) third argument of the predicate
     * @see #triAlwaysFalse()
     * @see #triAlwaysTrue()
     */
    public static <T, U, V> TriPredicate<T, U, V> triAlways(boolean value, String toString) {
        return new TriAlways<>(value, toString);
    }

    /**
     * Returns a {@link TriPredicate} that always results in the same {@code boolean} value
     * @param value The boolean value to always return
     * @param <T> The type of the (ignored) first argument of the predicate
     * @param <U> The type of the (ignored) second argument of the predicate
     * @param <V> The type of the (ignored) third argument of the predicate
     * @see #triAlwaysFalse()
     * @see #triAlwaysTrue()
     */
    public static <T, U, V> TriPredicate<T, U, V> triAlways(boolean value) {
        return value ? triAlwaysTrue() : triAlwaysFalse();
    }

    /**
     * Returns a {@link TriPredicate} that always results {@code false}, regardless of the argument. The toString value
     * is {@link #FALSE}
     * @param <T> The type of the (ignored) first argument of the predicate
     * @param <U> The type of the (ignored) second argument of the predicate
     * @param <V> The type of the (ignored) third argument of the predicate
     * @see #triAlwaysTrue()
     */
    public static <T, U, V> TriPredicate<T, U, V> triAlwaysFalse() {
        return triAlways(false, FALSE);
    }

    /**
     * Returns a {@link TriPredicate} that always results {@code true}, regardless of the argument. The toString value
     * is {@link #TRUE}
     * @param <T> The type of the (ignored) first argument of the predicate
     * @param <U> The type of the (ignored) second argument of the predicate
     * @param <V> The type of the (ignored) third argument of the predicate
     * @return A new {@link TriPredicate} which is {@link #equals(Object)} other such {@code TriPredicated} which also always are {@code true}
     * @see #triAlwaysFalse() ()
     */
    public static <T, U, V> TriPredicate<T, U, V> triAlwaysTrue() {
        return triAlways(true, TRUE);
    }

    /**
     * Creates a new {@link TriPredicate} but implement it using a {@link BiPredicate}, simply completely ignoring the third argument
     * @param <T> the type of the first argument to the predicate
     * @param <U> the type of the second argument to the predicate
     * @param <V> the type of the third argument to the predicate (ignored)
     * @param biPredicate the {@code BiPredicate} used to implement the new {@code TriPredicate}
     * @return a new {@code TriPredicate} with the desired behaviour
     */
    public static <T, U, V> TriPredicate<T, U, V> ignoreArg3(BiPredicate<T, U> biPredicate) {
        return new TriWrapper<BiPredicate<T, U>, T, U, V>(biPredicate, null, "ignore arg3") {
            @Override
            public boolean test(T t, U u, V v) {
                return wrapped.test(t, u);

            }
        };
    }

    /**
     * Creates a new {@link TriPredicate} but implement it using a {@link BiPredicate}, simply completely ignoring the second argument
     * @param <T> the type of the first argument to the predicate
     * @param <U> the type of the second argument to the predicate (ignored)
     * @param <V> the type of the third argument to the predicate
     * @param biPredicate the {@code BiPredicate} used to implement the new {@code TriPredicate}
     * @return a new {@code TriPredicate} with the desired behaviour
     */
    public static <T, U, V> TriPredicate<T, U, V> ignoreArg2(BiPredicate<T, V> biPredicate) {
        return new TriWrapper<BiPredicate<T, V>, T, U, V>(biPredicate, null, "ignore arg2") {
            @Override
            public boolean test(T t, U u, V v) {
                return wrapped.test(t, v);

            }
        };
    }

    /**
     * Creates a new {@link TriPredicate} but implement it using a {@link BiPredicate}, simply completely ignoring the first argument
     * @param <T> the type of the first argument to the predicate (ignored)
     * @param <U> the type of the second argument to the predicate
     * @param <V> the type of the third argument to the predicate
     * @param biPredicate the {@code BiPredicate} used to implement the new {@code TriPredicate}
     * @return a new {@code TriPredicate} with the desired behaviour
     */
    public static <T, U, V> TriPredicate<T, U, V> ignoreArg1(BiPredicate<U, V> biPredicate) {
        return new TriWrapper<BiPredicate<U, V>, T, U, V>(biPredicate, null, "ignore arg1") {
            @Override
            public boolean test(T t, U u, V v) {
                return wrapped.test(u, v);

            }
        };
    }

    /**
     * Creates a new {@link BiPredicate} but implement it using a {@link Predicate}, simply completely ignoring the second argument
     * @param <T> the type of the first argument to the predicate
     * @param <U> the type of the second argument to the predicate (ignored)
     * @param predicate  the {@code Predicate} used to implement the new {@code BiPredicate}
     * @return a new {@code BiPredicate} with the desired behaviour
     */
    public static <T, U> BiPredicate<T, U> ignoreArg2(Predicate<T> predicate) {
        return new BiWrapper<Predicate<T> , T, U>(predicate, null, "ignore arg2") {
            @Override
            public boolean test(T t, U u) {
                return wrapped.test(t);

            }
        };
    }

    /**
     * Creates a new {@link BiPredicate} but implement it using a {@link Predicate}, simply completely ignoring the first argument
     * @param <T> the type of the first argument to the predicate (ignored)
     * @param <U> the type of the second argument to the predicate
     * @param predicate the {@code Predicate} used to implement the new {@code BiPredicate}
     * @return a new {@code BiPredicate} with the desired behaviour
     */
    public static <T, U> BiPredicate<T, U> ignoreArg1(Predicate<U> predicate) {
        return new BiWrapper<Predicate<U> , T, U>(predicate, null, "ignore arg1") {
            @Override
            public boolean test(T t, U u) {
                return wrapped.test(u);

            }
        };
    }

    /**
     * Morphs a given {@link BiPredicate} into a {@link Predicate}, which a certain given value for the first argument.
     * @param <U> the type of the first argument to the predicate (value specified)
     * @param <V> the type of the second argument to the predicate
     * @param biPredicate the {@code BiPredicate} used to implement the new {@code Predicate}
     * @param value the value needed as the first argument of the given {@code BiPredicate}
     * @return a new {@code BiPredicate} with the desired behaviour
     *
     * @see TriPredicate#withArg1(Object)
     */
    public static <U, V> Predicate<V> withArg1(BiPredicate<U, V> biPredicate, U value) {
        return new MonoWrapper<BiPredicate<U, V>, V>(biPredicate, value, "with arg1 " + value) {
            @Override
            public boolean test(V v) {
                return wrapped.test(value, v);
            }
        };
    }

    /**
     * Morphs a given {@link BiPredicate} into a {@link Predicate}, which a certain given value for the first argument.
     * @param <U> the type of the first argument to the predicate
     * @param <V> the type of the second argument to the predicate (value specified)
     * @param biPredicate the {@code BiPredicate} used to implement the new {@code Predicate}
     * @param value the value needed as the second argument of the given {@code BiPredicate}
     * @return a new {@code BiPredicate} with the desired behaviour
     *
     * @see TriPredicate#withArg1(Object)
     */
    public static <U, V> Predicate<U> withArg2(BiPredicate<U, V> biPredicate, V value) {
        return new MonoWrapper<BiPredicate<U, V>, U>(biPredicate, value, "with arg2 " + value) {
            @Override
            public boolean test(U u) {
                return  wrapped.test(u, value);
            }
        };
    }

    /**
     * The abstract base class for several fixed valued (tri|bi|)-predicates.
     */
    protected static abstract class AbstractAlways {
        protected final boolean val;
        private final String toString;


        /**
         * @param val The fixed {@code boolean} value to return
         * @param toString The String returned by {@link #toString()}
         */
        public AbstractAlways(boolean val, String toString) {
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
            AbstractAlways always = (AbstractAlways) o;
            return val == always.val;
        }

        @Override
        public int hashCode() {
            return (val ? 1 : 0);
        }
    }

    protected static final class Always<T> extends AbstractAlways implements Predicate<T> {
        public Always(boolean val, String s) {
            super(val, s);
        }

        @Override
        public boolean test(T t) {
            return val;

        }
    }

    protected static final class BiAlways<T, U> extends AbstractAlways implements BiPredicate<T, U> {
        public BiAlways(boolean val, String s) {
            super(val, s);
        }
        @Override
        public boolean test(T t, U u) {
            return val;
        }
    }

    protected static final class TriAlways<T, U, V> extends AbstractAlways implements TriPredicate<T, U, V> {

        public TriAlways(boolean val, String s) {
            super(val, s);
        }

        @Override
        public boolean test(T t, U u, V v) {
            return val;
        }
    }

    protected static abstract  class TriWrapper<W, X, Y, Z> extends ValueWrapper<W> implements TriPredicate<X, Y, Z> {

        public TriWrapper(W wrapped, Object value, String why) {
            super(wrapped, value, why);
        }
    }

    protected static abstract  class BiWrapper<W, X, Y>  extends ValueWrapper<W> implements BiPredicate<X, Y> {
        public BiWrapper(W wrapped, Object value, String why) {
            super(wrapped, value, why);
        }
    }

    protected static abstract  class MonoWrapper<W, X>  extends ValueWrapper<W> implements Predicate<X> {
        public MonoWrapper(W wrapped, Object value, String why) {
            super(wrapped, value, why);
        }
    }



}
