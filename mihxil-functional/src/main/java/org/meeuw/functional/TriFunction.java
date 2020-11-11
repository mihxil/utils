package org.meeuw.functional;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The next in succession of {@link Function} and {@link BiFunction}.
 *
 * A function with three arguments
 *
 * @author Michiel Meeuwissen
 * @since 0.1
 */
@FunctionalInterface
public interface TriFunction <T,U,V,R> {

    R apply(T t, U u, V v);

    /**
     * @see Function#andThen(Function)
     */
    default <S> TriFunction<T, U, V, S> andThen(Function<? super R, ? extends S> after) {
        Objects.requireNonNull(after);
        return new Functions.TriWrapper<TriFunction<T, U, V, R>, T, U, V, S>(this,  after, " and then " + after) {

            @Override
            public S apply(T t, U u, V v) {
                R apply = wrapped.apply(t, u, v);
                return after.apply(apply);
            }
        };
    }

    /**
     * Morphs this {@link TriFunction} into a {@link BiFunction}, with a certain given value for the first argument.
     *
     * @see Functions#withArg1(BiFunction, Object)
     */
    default BiFunction<U, V, R> withArg1(T value) {
        return new Functions.BiWrapper<TriFunction<T, U, V, R>, U, V, R>(this, value, "with arg 1 " + value) {
            @Override
            public R apply(U u, V v) {
                return wrapped.apply(value, u, v);
            }
        };
    }

    /**
     * Morphs this {@link TriFunction} into a {@link BiFunction}, with a certain given value for the second argument.
     *
     * @see Functions#withArg2(BiFunction, Object)
     */
    default BiFunction<T, V, R> withArg2(U value) {
        return new Functions.BiWrapper<TriFunction<T, U, V, R>, T, V, R>(this, value, "with arg 2 " + value) {
            @Override
            public R apply(T t, V v) {
                return wrapped.apply(t, value, v);
            }
        };
    }

    /**
     * Morphs this {@link TriFunction} into a {@link BiFunction}, with a certain given value for the third argument.
     */
    default BiFunction<T, U, R> withArg3(V value) {
        return new Functions.BiWrapper<TriFunction<T, U, V, R>, T, U, R>(this, value, "with arg 3 " + value) {
            @Override
            public R apply(T t, U u) {
                return wrapped.apply(t, u, value);
            }
        };
    }

     /**
     * Creates a new {@link QuadriFunction} but implement it using a {@link TriFunction}, simply completely ignoring the fourth argument
     */
    default <X> QuadriFunction<T, U, V, X,  R> ignoreArg4() {
        return new Functions.QuadriWrapper<TriFunction<T, U, V, R>, T, U, V, X, R>(this, null, "ignore arg4") {
            @Override
            public R apply(T t, U u, V v, X x) {
                return wrapped.apply(t, u, v);
            }
        };
    }

    /**
     * Creates a new {@link QuadriFunction} but implement it using a {@link TriFunction}, simply completely ignoring the third argument
     */
    default <X> QuadriFunction<T, U, X, V,  R> ignoreArg3() {
        return new Functions.QuadriWrapper<TriFunction<T, U, V, R>, T, U, X, V, R>(this, null, "ignore arg3") {
            @Override
            public R apply(T t, U u, X x, V v) {
                return wrapped.apply(t, u, v);
            }
        };
    }

    /**
     * Creates a new {@link QuadriFunction} but implement it using a {@link TriFunction}, simply completely ignoring the second argument
     */
    default <X> QuadriFunction<T, X, U, V,  R> ignoreArg2() {
        return new Functions.QuadriWrapper<TriFunction<T, U, V, R>, T, X, U, V, R>(this, null, "ignore arg2") {
            @Override
            public R apply(T t, X x, U u, V v) {
                return wrapped.apply(t, u, v);
            }
        };
    }

    /**
     * Creates a new {@link QuadriFunction} but implement it using a {@link TriFunction}, simply completely ignoring the first argument
     */
    default <X>  QuadriFunction<X, T, U, V,  R> ignoreArg1() {
        return new Functions.QuadriWrapper<TriFunction<T, U, V, R>, X, T, U, V, R>(this, null, "ignore arg1") {
            @Override
            public R apply(X x, T t, U u, V v) {
                return wrapped.apply(t, u, v);
            }
        };
    }


}
