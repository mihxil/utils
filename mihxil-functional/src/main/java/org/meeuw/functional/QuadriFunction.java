package org.meeuw.functional;

import java.util.Objects;
import java.util.function.*;

/**
 * The next in succession of {@link Function}, {@link BiFunction} and {@link TriFunction}
 *
 * A function with four arguments
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <V> the type of the third argument to the function
 * @param <W> the type of the fourth argument to the function
 * @param <R> the type of the result of the function
 *
 * @author Michiel Meeuwissen
 * @since 0.1
 */
@FunctionalInterface
public interface QuadriFunction<T, U, V, W, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @param v the third function argument
     * @param w the fourth function argument
     * @return the function result
     */
    R apply(T t, U u, V v, W w);

    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <S> the type of output of the {@code after} function, and of the composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then applies the {@code after} function
     * @throws NullPointerException if after is null
     * @see Function#andThen(Function)
     */
    default <S> QuadriFunction<T, U, V, W, S> andThen(Function<? super R, ? extends S> after) {
        Objects.requireNonNull(after);
        return new Functions.QuadriWrapper<QuadriFunction<T, U, V, W, R>, T, U, V, W, S>(this, after, "and then " + after) {
            @Override
            public S apply(T t, U u, V v, W w) {
                return after.apply(wrapped.apply(t, u, v, w));
            }
        };

    }

    /**
     * Morphs this {@link QuadriFunction} into a {@link TriFunction}, with a certain given value for the first argument.
     *
     * @param value the argument to always supply to this function first argument
     * @return a new {@code TriFunction}, which will use the given function and argument for its implementation
     */
    default TriFunction<U, V, W, R> withArg1(T value) {
        return new Functions.TriWrapper<QuadriFunction<T, U, V, W, R>, U, V, W, R>(this, value, "with arg 1 " + value) {
            @Override
            public R apply(U u, V v, W w) {
                return wrapped.apply(value, u, v, w);
            }
        };
    }

    /**
     * Morphs this {@link QuadriFunction} into a {@link TriFunction}, with a certain given value for the second argument.
     * @param value the argument to always supply to this function second argument
     * @return a new {@code TriFunction}, which will use the given function and argument for its implementation
     * @see Functions#withArg2(BiFunction, Object)
     */
    default TriFunction<T, V, W, R> withArg2(U value) {
        return new Functions.TriWrapper<QuadriFunction<T, U, V, W, R>, T, V, W, R>(this, value, "with arg 2 " + value) {
            @Override
            public R apply(T t, V v, W w) {
                return wrapped.apply(t, value, v, w);
            }
        };
    }

    /**
     * Morphs this {@link QuadriFunction} into a {@link TriFunction}, with a certain given value for the third argument.
     * @param value the argument to always supply to this function third argument
     * @return a new {@code TriFunction}, which will use the given function and argument for its implementation
     */
    default TriFunction<T, U, W, R> withArg3(V value) {
        return new Functions.TriWrapper<QuadriFunction<T, U, V, W, R>, T, U, W, R>(this, value, "with arg 3 " + value) {
            @Override
            public R apply(T t, U u, W w) {
                return wrapped.apply(t, u, value, w);
            }
        };
    }

    /**
     * Morphs this {@link QuadriFunction} into a {@link TriFunction}, with a certain given value for the fourth argument.
     * @param value the argument to always supply to this function fourth argument
     * @return a new {@code TriFunction}, which will use the given function and argument for its implementation
     */
    default TriFunction<T, U, V, R> withArg4(W value) {
        return new Functions.TriWrapper<QuadriFunction<T, U, V, W, R>, T, U, V, R>(this, value, "with arg 4 " + value) {
            @Override
            public R apply(T t, U u, V v) {
                return wrapped.apply(t, u, v, value);
            }
        };
    }

}
