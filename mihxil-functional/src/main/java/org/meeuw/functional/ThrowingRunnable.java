package org.meeuw.functional;

import static org.meeuw.functional.Sneaky.sneakyThrow;

/**
 * An extension of {@link Runnable} that can throw exceptions too.
 * @since 1.6
 * @param <T> The type of exceptions thrown by {@link #runThrows()}
 */
@FunctionalInterface
public interface ThrowingRunnable<T extends Throwable> extends Runnable {

    default void run() {
        try {
            runThrows();
        } catch (final Throwable e) {
            sneakyThrow(e);
        }
    }

    /**
     * See {@link #run()}, but it may throw an exception.
     * @throws T if an exception occurs during execution
     */
    void runThrows() throws T;

}
