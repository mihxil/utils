package org.meeuw.functional;

import java.util.function.Supplier;


/**
 * A {@link Supplier} that can be unwrapped to a different type.
 * @since 0.12
 * @param <T> The type of the object supplied by this supplier
 * @param <W> The type of the object that can be unwrapped from this supplier
 */
public interface UnwrappableSupplier<T, W> extends Supplier<T>, Unwrappable<W> {




}
