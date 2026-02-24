package org.meeuw.functional;

import java.util.function.Consumer;

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
     * @since 1.17
     * @param value
     * @return
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
     * @since 1.17
     * @param value
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
     * @since 1.17
     * @param value
     */
    default ThrowingBiFunction<B, C, R, E> withArg1(A value) {
        return new Functions.ThrowingBiWrapper<ThrowingTriFunction<A, B, C, R, E>, B, C, R, E>(this, value, "with arg1 " + value) {

            @Override
            public R applyWithException(B b, C c) throws E {
                return wrapped.applyWithException(value, b,  c);
            }
        };
    }

}
