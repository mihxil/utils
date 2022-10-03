package org.meeuw.functional;

import java.util.function.Consumer;

import static org.meeuw.functional.ThrowingSupplier.sneakyThrow;

/**
 * An extension of {@link Consumer} that can throw exceptions too.
 * @since 1.4
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> extends Consumer<T> {

    @Override
    default void accept(final T t) {
        try {
            acceptThrows(t);
        } catch (final Exception e) {
            sneakyThrow(e);
        }
    }

    /**
     * Performs this operation on the given argument, while allowing for an exception.
     * @param t  the input argument
     */
    void acceptThrows(T t) throws E;

}
