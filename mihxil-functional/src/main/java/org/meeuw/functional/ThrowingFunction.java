package org.meeuw.functional;

import java.util.function.Function;

import static org.meeuw.functional.Sneaky.sneakyThrow;


/**
 * An extension of {@link Function} that can throw exceptions too.
 * @since 1.13
 * @param <E> the type of exception that can be thrown
 */
@FunctionalInterface
public interface ThrowingFunction<A, R, E extends Exception> extends Function<A, R> {
    @Override
    default R apply(A a) {
        try {
            return applyWithException(a);
        } catch (final Exception e) {
            return sneakyThrow(e);
        }
    }

    R applyWithException(A a) throws E;

}
