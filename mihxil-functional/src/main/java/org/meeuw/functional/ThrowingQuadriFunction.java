package org.meeuw.functional;

import static org.meeuw.functional.Sneaky.sneakyThrow;


/**
 * An extension of {@link QuadriFunction} that can throw exceptions too.
 * @since 1.13
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
