package org.meeuw.functional;

/**
 * Interface for wrappers that provide access to an underlying wrapped object.
 * @param <W> The type of the wrapped object.
 * @since 1.12
 */
public interface Unwrappable<W> {

    /**
     * Returns the underlying wrapped object.
     * @return The wrapped object.
     */
    W unwrap();
}
