package org.meeuw.functional;

import java.util.function.Supplier;

/**
 * @since 1.12
 * @param <T>
 */
@FunctionalInterface
public interface CloseableSupplier<T> extends Supplier<T>, AutoCloseable {

    /**
     * Closes the supplier, releasing any resources it holds.
     * This method should be called when the supplier is no longer needed.
     */
    @Override
    default void close() throws Exception {

    }


}
