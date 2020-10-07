package org.meeuw.util;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The next in succession of {@link Function} and {@link BiFunction}.
 *
 * A function with three arguments
 *
 * @author Michiel Meeuwissen
 * @since 1.72
 */
@FunctionalInterface
public interface TriFunction <T,U,V,R> {

    R apply(T t, U u, V v);

    /**
     * Morphs this {@link TriFunction} into a {@link BiFunction}, which a certain given value for the first argument.
     *
     * See {@link Functions#withArg1(BiFunction, Object)}
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
     * Morphs this {@link TriFunction} into a {@link BiFunction}, which a certain given value for the second argument.
     *
     * See {@link Functions#withArg2(BiFunction, Object)}
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
     * Morphs this {@link TriFunction} into a {@link BiFunction}, which a certain given value for the third argument.
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
     * @see Function#andThen(Function)
     */
    default <S> TriFunction<T, U, V, S> andThen(Function<? super R, ? extends S> after) {
        Objects.requireNonNull(after);
        return (T t, U u, V v) -> after.apply(apply(t, u, v));
    }
}
