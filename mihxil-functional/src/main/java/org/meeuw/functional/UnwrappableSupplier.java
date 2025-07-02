package org.meeuw.functional;

import java.util.function.Supplier;


/**
 * @since 0.12
 * @param <T>
 * @param <W>
 */
public interface UnwrappableSupplier<T, W> extends Supplier<T>, Unwrappable<W> {




}
