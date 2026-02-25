package org.meeuw.functional;

import java.util.Objects;
import java.util.function.Consumer;

import java.util.function.Function;

import static org.meeuw.functional.Sneaky.sneakyThrow;


/**
 * An extension of {@link Consumer} that can throw exceptions too.
 * @since 1.13
 * @param <A> the type of the first argument to the function
 * @param <B> the type of the second argument to the function
 * @param <C> the type of the third argument to the function
 * @param <R> the type of the result of the function
 * @param <E> the type of the exception that can be thrown
 */
@FunctionalInterface
public interface ThrowingTriFunction<A, B, C, R, E extends Exception> extends TriFunction<A, B, C, R> {
    @Override
    default R apply(A a, B b, C c) {
        try {
            return applyWithException(a, b, c);
        } catch (final Exception e) {
            return sneakyThrow(e);
        }
    }


    /**
     * Applies this function to the given arguments.
     * @param a  the first function argument
     * @param b  the second function argument
     * @param c  the third function argument
     * @return the function result
     * @throws E Checked exception that might occur.
     */

    R applyWithException(A a, B b, C c) throws E;


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
    default <S> ThrowingTriFunction<A, B, C, S, E> andThen(Function<? super R, ? extends S> after) {
        Objects.requireNonNull(after);
        return new Functions.ThrowingTriWrapper<ThrowingTriFunction<A, B, C, R, E>, A, B, C, S, E>(this, after, "and then " + after) {
            @Override
            public S applyWithException(A a, B b, C c) throws E {
                R result = wrapped.applyWithException(a, b, c);
                return after.apply(result);
            }
        };
    }


    /**
     * Morphs this {@link ThrowingTriFunction} into a {@link ThrowingBiFunction}, with a certain given value for the third argument.
     * @param value the argument to always supply to this function third argument
     * @return a new {@code ThrowingBiFunction}, which will use the given function and argument for its implementation
     * @since 1.17
     */
    default ThrowingBiFunction<A, B, R, E> withArg3(C value) {
        return new Functions.ThrowingBiWrapper<ThrowingTriFunction<A, B, C, R, E>, A, B, R, E>(this, value, "with arg3 " + value) {

            @Override
            public R applyWithException(A a, B b) throws E {
                return wrapped.applyWithException(a, b, value);
            }

        };
    }
    /**
     * Morphs this {@link ThrowingTriFunction} into a {@link ThrowingBiFunction}, with a certain given value for the second argument.
     * @param value the argument to always supply to this function third argument
     * @return a new {@code ThrowingBiFunction}, which will use the given function and argument for its implementation
     * @since 1.17
     */
    default ThrowingBiFunction<A, C, R, E> withArg2(B value) {
        return new Functions.ThrowingBiWrapper<ThrowingTriFunction<A, B, C, R, E>, A, C, R, E>(this, value, "with arg2 " + value) {

            @Override
            public R applyWithException(A a, C c) throws E {
                return wrapped.applyWithException(a, value, c);
            }
        };
    }

    /**
     * Morphs this {@link ThrowingTriFunction} into a {@link ThrowingBiFunction}, with a certain given value for the first argument.
     * @param value the argument to always supply to this function third argument
     * @return a new {@code ThrowingBiFunction}, which will use the given function and argument for its implementation
     * @since 1.17
     */
    default ThrowingBiFunction<B, C, R, E> withArg1(A value) {
        return new Functions.ThrowingBiWrapper<ThrowingTriFunction<A, B, C, R, E>, B, C, R, E>(this, value, "with arg1 " + value) {

            @Override
            public R applyWithException(B b, C c) throws E {
                return wrapped.applyWithException(value, b,  c);
            }
        };
    }

    /**
     * Creates a new {@link ThrowingQuadriFunction} using this {@link ThrowingTriFunction}, simply completely ignoring the fourth argument
     * @param <X> type of fourth argument to the resulting {@link ThrowingQuadriFunction}  (ignored)
     * @return the new {@code ThrowingQuadriFunction}
     * @since 1.17
     */
    default <X> ThrowingQuadriFunction<A, B, C, X, R, E> ignoreArg4() {
        return new Functions.ThrowingQuadriWrapper<ThrowingTriFunction<A, B, C, R, E>,  A, B, C, X, R , E>(this, null, "ignore arg4") {

            @Override
            public R applyWithException(A a, B b, C c, X x) throws E {
                return wrapped.applyWithException(a, b, c);
            }
        };
    }

    /**
     * Creates a new {@link ThrowingQuadriFunction} using this {@link ThrowingTriFunction}, simply completely ignoring the third argument
     * @param <X> type of third argument to the resulting {@link ThrowingQuadriFunction}  (ignored)
     * @return the new {@code ThrowingQuadriFunction}
     * @since 1.17
     */
    default <X> ThrowingQuadriFunction<A, B, X, C, R, E> ignoreArg3() {
        return new Functions.ThrowingQuadriWrapper<ThrowingTriFunction<A, B, C, R, E>,  A, B, X, C, R , E>(this, null, "ignore arg3") {

            @Override
            public R applyWithException(A a, B b, X x, C c) throws E {
                return wrapped.applyWithException(a, b, c);
            }
        };
    }

    /**
     * Creates a new {@link ThrowingQuadriFunction} using this {@link ThrowingTriFunction}, simply completely ignoring the fourth argument
     * @param <X> type of fourth argument to the resulting {@link ThrowingQuadriFunction}  (ignored)
     * @return the new {@code ThrowingQuadriFunction}
     * @since 1.17
     */
    default <X> ThrowingQuadriFunction<A, X, B, C, R, E> ignoreArg2() {
        return new Functions.ThrowingQuadriWrapper<ThrowingTriFunction<A, B, C, R, E>,  A, X, B, C, R , E>(this, null, "ignore arg2") {

            @Override
            public R applyWithException(A a, X x, B b, C c) throws E {
                return wrapped.applyWithException(a, b, c);
            }
        };
    }
    /**
     * Creates a new {@link ThrowingQuadriFunction} using this {@link ThrowingTriFunction}, simply completely ignoring the first argument
     * @param <X> type of first argument to the resulting {@link ThrowingQuadriFunction}  (ignored)
     * @return the new {@code ThrowingQuadriFunction}
     * @since 1.17
     */
    default <X> ThrowingQuadriFunction<X, A, B, C, R, E> ignoreArg1() {
        return new Functions.ThrowingQuadriWrapper<ThrowingTriFunction<A, B, C, R, E>,  X, A, B, C, R , E>(this, null, "ignore arg2") {

            @Override
            public R applyWithException(X x, A a, B b, C c) throws E {
                return wrapped.applyWithException(a, b, c);
            }
        };
    }


}
