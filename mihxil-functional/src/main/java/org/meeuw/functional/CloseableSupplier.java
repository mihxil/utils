package org.meeuw.functional;

import java.util.function.Supplier;

/**
 * A thing that is both a {@link Supplier} and an {@link AutoCloseable}.
 * @since 1.12
 * @param <T> The type of the object supplied by this supplier
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
