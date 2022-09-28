package org.meeuw.functional;

import java.util.function.Supplier;

/**
 * An extension of {@link Supplier} that can throw exceptions too.
 * @since 1.5
 */
@FunctionalInterface
public interface ThrowingSupplier<T, E extends Exception> extends Supplier<T> {

    static <T extends Throwable> T sneakyThrow(Throwable e) throws T {
        throw (T) e;
    }

    @Override
    default T get() {
        try {
            return getThrows();
        } catch (final Exception e) {
            throw sneakyThrow(e);
        }
    }
    /**
     * Performs this operation on the given argument, while allowing for an exception.
     *
     */
    T getThrows() throws E;

}
