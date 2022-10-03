package org.meeuw.functional;

import static org.meeuw.functional.ThrowingSupplier.sneakyThrow;

/**
 * @since 1.6
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

    void runThrows() throws T;

}
