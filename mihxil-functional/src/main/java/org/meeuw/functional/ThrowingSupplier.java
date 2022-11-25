package org.meeuw.functional;

import java.util.function.Supplier;

/**
 * An extension of {@link Supplier} that can throw exceptions too.
 * @param <T> the type of results supplied by this supplier
 * @since 1.5
 */
@FunctionalInterface
public interface ThrowingSupplier<T, E extends Exception> extends Supplier<T> {

    static <T extends Throwable> void sneakyThrow(Throwable e) throws T {
        throw (T) e;
    }

    @Override
    default T get() {
        try {
            return getThrows();
        } catch (final Throwable e) {
            sneakyThrow(e); return null;
        }
    }

    /**
     * Performs this operation on the given argument, while allowing for an exception.
     *
     * @return a result
     * @throws E if the operation somehow fails, it throws exceptions of this type
     */
    T getThrows() throws E;

}