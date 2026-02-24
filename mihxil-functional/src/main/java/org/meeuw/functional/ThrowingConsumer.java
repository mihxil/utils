package org.meeuw.functional;

import java.util.function.Consumer;

import static org.meeuw.functional.Sneaky.sneakyThrow;


/**
 * An extension of {@link Consumer} that can throw exceptions too.
 * @since 1.4
 * @param <T> the type of the input to the operation
 * @param <E> the type of the exception that can be thrown
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
     * A version of {@link #andThen(Consumer)} then accepts a {@link ThrowingConsumer} instead of a {@link Consumer}, and also produces one.
     * @since 1.16
     * @param after The consumer to perform after this operation is performed
     * @return a composed Consumer that performs in sequence this operation followed by the after operation
     * @see #andThen(Consumer)
     */
    default ThrowingConsumer<T, E> andThen(ThrowingConsumer<? super T, ? extends E> after) {
        return (T t) -> {
            acceptThrows(t);
            after.acceptThrows(t);
        };
    }

    /**
     * Performs this operation on the given argument, while allowing for an exception.
     * @param t  the input argument
     * @throws E if the operation somehow fails, it throws {@link Exception exceptions} of this type
     */
    void acceptThrows(T t) throws E;

}
