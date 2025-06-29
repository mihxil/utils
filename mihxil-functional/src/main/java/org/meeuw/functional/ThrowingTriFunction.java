package org.meeuw.functional;

import java.util.function.Consumer;

import static org.meeuw.functional.Sneaky.sneakyThrow;


/**
 * An extension of {@link Consumer} that can throw exceptions too.
 * @since 1.13
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

    R applyWithException(A a, B b, C c) throws E;

}
