package org.meeuw.functional;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.meeuw.functional.Sneaky.sneakyThrow;


/**
 * An extension of {@link Consumer} that can throw exceptions too.
 * @since 1.16
 * @param <T> the type of the input to the operation
 * @param <U> the type of the input to the operation
 * @param <E> the type of the exception that can be thrown
 */
@FunctionalInterface
public interface ThrowingBiConsumer<T, U, E extends Exception> extends BiConsumer<T, U> {

    @Override
    default void accept(final T t, final U u) {
        try {
            acceptThrows(t, u);
        } catch (final Exception e) {
            sneakyThrow(e);
        }
    }

    default ThrowingBiConsumer<T, U,  E> andThen(ThrowingBiConsumer<? super T, ? super U, ? extends E> after) {

        return (T t, U u) -> {
            acceptThrows(t, u);
            after.acceptThrows(t, u);
        };
    }

    /**
     * Performs this operation on the given argument, while allowing for an exception.
     * @param t  the input argument
     * @throws E if the operation somehow fails, it throws exceptions of this type
     */
    void acceptThrows(T t, U u) throws E;

    /**
     * @since 1.17
     */
    default ThrowingConsumer<T, E> withArg2(U u) {
        return new Consumers.ThrowingMonoWrapper<ThrowingBiConsumer<T, U, E>, T, E>(this, u, "with arg2") {
            @Override
            public void acceptThrows(T t) throws E{
                wrapped.acceptThrows(t, u);
            }
        };
    }

    /**
     * @since 1.17
     */
    default ThrowingConsumer<U, E> withArg1(T t) {
        return new Consumers.ThrowingMonoWrapper<ThrowingBiConsumer<T, U, E>, U, E>(this, t, "with arg1") {
            @Override
            public void acceptThrows(U u) throws E{
                wrapped.acceptThrows(t, u);
            }
        };
    }




}
