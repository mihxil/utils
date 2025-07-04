package org.meeuw.functional;

/**
 * A {@link CloseableSupplier} that is also a {@link Unwrappable}.
 *
 * @since 1.12
 * @param <T> Type of supplier
 * @param <W> Type of wrapped object
 */

public interface UnwrappableCloseableSupplier<T, W> extends CloseableSupplier<T>, Unwrappable<W> {

}
