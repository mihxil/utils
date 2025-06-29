package org.meeuw.functional;

import java.util.concurrent.Callable;

import static org.meeuw.functional.Sneaky.sneakyThrow;


/**
 * An extension of {@link Callable} that can throw exceptions too.
 * @since 1.13
 */
@FunctionalInterface
public interface ThrowingCallable<R, E extends Exception> extends Callable<R> {
    @Override
    default R call() {
        try {
            return callWithException();
        } catch (final Exception e) {
            return sneakyThrow(e);
        }
    }

    R callWithException() throws E;

}
