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

}
