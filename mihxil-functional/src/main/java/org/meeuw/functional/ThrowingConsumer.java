package org.meeuw.functional;

import java.util.function.Consumer;

/**
 * An extension of {@link Consumer} that can throw exceptions too.
 * @since 1.4
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> extends Consumer<T> {

    static <T extends Throwable> void sneakyThrow(Throwable e) throws T {
        throw (T) e;
    }
    @Override
    default void accept(final T elem) {
        try {
            acceptThrows(elem);
        } catch (final Exception e) {
            sneakyThrow(e);
        }
    }

    void acceptThrows(T elem) throws E;

}
