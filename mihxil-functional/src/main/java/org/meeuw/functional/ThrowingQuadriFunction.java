package org.meeuw.functional;

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

    R applyWithException(A a, B b, C c, D d) throws E;

}
