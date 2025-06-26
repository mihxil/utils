package org.meeuw.functional;

/**
 * @since 1.12
 * @param <T> Type of supplier
 * @param <W> Type of wrapped object
 */
public interface UnwrappableCloseableSupplier<T, W> extends CloseableSupplier<T>, Unwrappable<W> {

}
