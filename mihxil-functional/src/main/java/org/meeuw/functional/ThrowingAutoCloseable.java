package org.meeuw.functional;

import static org.meeuw.functional.Sneaky.sneakyThrow;

/**
 * An extension of {@link AutoCloseable} that has a typed exception.
 * {@link #close()} will sneakily throw any exception thrown by {@link #closeThrows()}, which make it handy in try-with-resources.
 *
 * @since 1.8
 * @param <E> the type of the exception that can be thrown
 */
@FunctionalInterface
public interface ThrowingAutoCloseable<E extends Exception> extends AutoCloseable {

    @Override
    default void close() {
        try {
            closeThrows();
        } catch (final Exception e) {
            sneakyThrow(e);
        }
    }

    /**
     * Close, while allowing for an exception.
     * @throws E if the operation somehow fails, it throws {@link Exception exceptions} of this type
     */
    void closeThrows() throws E;

}
