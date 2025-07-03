package org.meeuw.functional;

import java.util.function.BiFunction;

import static org.meeuw.functional.Sneaky.sneakyThrow;


/**
 * An extension of {@link BiFunction} that can throw exceptions too.
 * @since 1.13
 * @param <A> the type of the first argument to the function
 * @param <B> the type of the second argument to the function
 * @param <R> the type of the result of the function
 * @param <E> the type of the exception that can be thrown by this function
 */
@FunctionalInterface
public interface ThrowingBiFunction<A, B, R, E extends Exception> extends BiFunction<A, B, R> {
    @Override
    default R apply(A a, B b) {
        try {
            return applyWithException(a, b);
        } catch (final Exception e) {
            return sneakyThrow(e);
        }
    }

    R applyWithException(A a, B b) throws E;

}
