package org.meeuw.functional;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.meeuw.functional.Sneaky.sneakyThrow;


/**
 * An extension of {@link Consumer} that can throw exceptions too.
 * @since 1.16
 * @param <T> the type of the input to the operation
 * @param <U> the type of the input to the operation
 * @param <V> the type of the input to the operation
 * @param <E> the type of the exception that can be thrown
 */
@FunctionalInterface
public interface ThrowingTriConsumer<T, U, V, E extends Exception> extends TriConsumer<T, U, V> {

    @Override
    default void accept(final T t, final U u, final V v) {
        try {
            acceptThrows(t, u, v);
        } catch (final Exception e) {
            sneakyThrow(e);
        }
    }

    default ThrowingTriConsumer<T, U, V,  E> andThen(ThrowingTriConsumer<? super T, ? super U, ? super V, ? extends E> after) {
        return (T t, U u, V v) -> {
            acceptThrows(t, u, v);
            after.acceptThrows(t, u,  v);
        };
    }

    /**
     * Performs this operation on the given argument, while allowing for an exception.
     * @param t  the input argument
     * @throws E if the operation somehow fails, it throws exceptions of this type
     */
    void acceptThrows(T t, U u, V v) throws E;

}
