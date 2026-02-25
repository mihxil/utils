package org.meeuw.functional;

import java.util.Objects;
import java.util.function.Function;

import static org.meeuw.functional.Sneaky.sneakyThrow;


/**
 * An extension of {@link QuadriFunction} that can throw exceptions too.
 * @since 1.13
 * @param <A> the type of the first argument to the function
 * @param <B> the type of the second argument to the function
 * @param <C> the type of the third argument to the function
 * @param <D> the type of the fourth argument to the function
 * @param <R> the type of the result of the function
 * @param <E> the type of the exception that can be thrown
 */
@FunctionalInterface
public interface ThrowingQuadriFunction<A, B, C, D, R, E extends Exception> extends QuadriFunction<A, B, C, D, R> {
    @Override
    default R apply(A a, B b, C c, D d) {
        try {
            return applyWithException(a, b, c, d);
        } catch (final Exception e) {
            return sneakyThrow(e);
        }
    }
    /**
     * Applies this function to the given arguments.
     * @param a  the first function argument
     * @param b  the second function argument
     * @param c  the third function argument
     * @param d  the fourth function argument
     * @return the function result
     * @throws E Checked exception that might occur.
     */

    R applyWithException(A a, B b, C c, D d) throws E;

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
     * @since 1.17
     */
    default <S> ThrowingQuadriFunction<A, B, C, D, S, E> andThen(Function<? super R, ? extends S> after) {
        Objects.requireNonNull(after);
        return new Functions.ThrowingQuadriWrapper<ThrowingQuadriFunction<A, B, C, D, R, E>, A, B, C, D, S, E>(this, after, "and then " + after) {
            @Override
            public S applyWithException(A a, B b, C c, D d) throws E {
                R result = wrapped.applyWithException(a, b, c, d);
                return after.apply(result);
            }
        };
    }

    /**
     * Morphs this {@link ThrowingQuadriFunction} into a {@link ThrowingTriFunction}, with a certain given value for the first argument.
     *
     * @param value the argument to always supply to this function first argument
     * @return a new {@code ThrowingTriFunction}, which will use the given function and argument for its implementation
     * @since 1.17
     */
    default ThrowingTriFunction<B, C, D,  R, E> withArg1(A value) {
        return new Functions.ThrowingTriWrapper<ThrowingQuadriFunction<A, B, C, D, R, E>, B, C, D, R, E>(this, value, "with arg 1 " + value) {

            @Override
            public R applyWithException(B b, C c, D d) throws E {
                return wrapped.applyWithException(value, b, c, d);
            }
        };
    }

    /**
     * Morphs this {@link ThrowingQuadriFunction} into a {@link ThrowingTriFunction}, with a certain given value for the second argument.
     * @param value the argument to always supply to this function second argument
     * @return a new {@code ThrowingTriFunction}, which will use the given function and argument for its implementation
     * @see ThrowingBiFunction#withArg2(Object)
     * @since 1.17
     */
    default ThrowingTriFunction<A, C, D, R, E> withArg2(B value) {
        return new Functions.ThrowingTriWrapper<ThrowingQuadriFunction<A, B, C, D,  R, E>, A, C, D, R, E>(this, value, "with arg 2 " + value) {

            @Override
            public R applyWithException(A a, C c, D d) throws E {
                return wrapped.applyWithException(a, value, c, d);
            }
        };
    }

    /**
     * Morphs this {@link ThrowingQuadriFunction} into a {@link ThrowingTriFunction}, with a certain given value for the third argument.
     * @param value the argument to always supply to this function third argument
     * @return a new {@code ThrowingTriFunction}, which will use the given function and argument for its implementation
     * @since 1.17
     */
    default ThrowingTriFunction<A, B, D, R, E> withArg3(C value) {
        return new Functions.ThrowingTriWrapper<ThrowingQuadriFunction<A, B, C, D,  R, E>, A, B, D, R, E>(this, value, "with arg 3 " + value) {
            @Override
            public R applyWithException(A a, B b, D d) throws E {
                return wrapped.applyWithException(a, b, value, d);
            }
        };
    }

    /**
     * Morphs this {@link ThrowingQuadriFunction} into a {@link ThrowingTriFunction}, with a certain given value for the fourth argument.
     * @param value the argument to always supply to this function fourth argument
     * @return a new {@code ThrowingTriFunction}, which will use the given function and argument for its implementation
     * @since 1.17
     */
    default ThrowingTriFunction<A, B, C, R, E> withArg4(D value) {
        return new Functions.ThrowingTriWrapper<ThrowingQuadriFunction<A, B, C, D,  R, E>, A, B, C, R, E>(this, value, "with arg 4 " + value) {

            @Override
            public R applyWithException(A a, B b, C c) throws E {
                return wrapped.applyWithException(a, b, c, value);
            }
        };
    }

}
