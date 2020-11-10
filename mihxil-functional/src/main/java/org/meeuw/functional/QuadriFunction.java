package org.meeuw.functional;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The next in succession of {@link Function}, {@link BiFunction} and {@link TriFunction}
 *
 * A function with four arguments
 *
 * @author Michiel Meeuwissen
 * @since 0.1
 */
@FunctionalInterface
public interface QuadriFunction<T, U, V, W, R> {

    R apply(T t, U u, V v, W w);

    /**
     * Morphs this {@link QuadriFunction} into a {@link TriFunction}, with a certain given value for the first argument.
     *
     */
    default TriFunction<U, V, W, R> withArg1(T t) {
        return new Functions.TriWrapper<QuadriFunction<T, U, V, W, R>, U, V, W, R>(this, t, "with arg 1 " + t) {
            @Override
            public R apply(U u, V v, W w) {
                return wrapped.apply(t, u, v, w);
            }
        };
    }

    /**
     * Morphs this {@link QuadriFunction} into a {@link TriFunction}, with a certain given value for the second argument.
     *
     * See {@link Functions#withArg2(BiFunction, Object)}
     */
    default TriFunction<T, V, W, R> withArg2(U u) {
        return new Functions.TriWrapper<QuadriFunction<T, U, V, W, R>, T, V, W, R>(this, u, "with arg 2 " + u) {
            @Override
            public R apply(T t, V v, W w) {
                return wrapped.apply(t, u, v, w);
            }
        };
    }

    /**
     * Morphs this {@link QuadriFunction} into a {@link TriFunction}, with a certain given value for the third argument.
     */
    default TriFunction<T, U, W, R> withArg3(V v) {
        return new Functions.TriWrapper<QuadriFunction<T, U, V, W, R>, T, U, W, R>(this, v, "with arg 3 " + v) {
            @Override
            public R apply(T t, U u, W w) {
                return wrapped.apply(t, u, v, w);
            }
        };
    }

     /**
     * Morphs this {@link QuadriFunction} into a {@link TriFunction}, with a certain given value for the fourth argument.
     */
    default TriFunction<T, U, V, R> withArg4(W w) {
        return new Functions.TriWrapper<QuadriFunction<T, U, V, W, R>, T, U, V, R>(this, w, "with arg 4 " + w) {
            @Override
            public R apply(T t, U u, V v) {
                return wrapped.apply(t, u, v, w);
            }
        };
    }

    /**
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
}
