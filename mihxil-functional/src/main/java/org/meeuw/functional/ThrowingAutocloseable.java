package org.meeuw.functional;

import static org.meeuw.functional.ThrowingSupplier.sneakyThrow;

/**
 * @since 1.8
 */
@FunctionalInterface
public interface ThrowingAutocloseable<E extends Exception> extends AutoCloseable {

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
     * @throws E if the operation somehow fails, it throws exceptions of this type
     */
    void closeThrows() throws E;

}
