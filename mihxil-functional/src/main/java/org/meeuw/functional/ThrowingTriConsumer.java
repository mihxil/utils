package org.meeuw.functional;

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

    /**
     * Returns a composed {@code ThrowingTriConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code BiConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default ThrowingTriConsumer<T, U, V,  E> andThen(ThrowingTriConsumer<? super T, ? super U, ? super V, ? extends E> after) {
        if (after == null) {
            throw new NullPointerException("after cannot be null");
        }
        return (T t, U u, V v) -> {
            acceptThrows(t, u, v);
            after.acceptThrows(t, u,  v);
        };
    }

    /**
     * Performs this operation on the given argument, while allowing for an exception.
     * @param t  the first input argument
     * @param u  the second input argument
     * @param v  the third input argument
     *
     * @throws E if the operation somehow fails, it throws exceptions with this type
     */
    void acceptThrows(T t, U u, V v) throws E;

    /**
     * @since 1.17
     */
    default ThrowingBiConsumer<T, U, E> withArg3(V v) {
        return new Consumers.ThrowingBiWrapper<ThrowingTriConsumer<T, U, V, E>, T, U, E>(this, v, "with arg3 " + v) {
            @Override
            public void acceptThrows(T t, U u) throws E{
                wrapped.acceptThrows(t, u, v);
            }
        };
    }

    /**
     * @since 1.17
     */
    default ThrowingBiConsumer<T, V, E> withArg2(U u) {
        return new Consumers.ThrowingBiWrapper<ThrowingTriConsumer<T, U, V, E>, T, V, E>(this, u, "with arg2 " + u) {
            @Override
            public void acceptThrows(T t, V v) throws E{
                wrapped.acceptThrows(t, u, v);
            }
        };
    }

    /**
     * @since 1.17
     */
    default ThrowingBiConsumer<U, V, E> withArg1(T t) {
        return new Consumers.ThrowingBiWrapper<ThrowingTriConsumer<T, U, V, E>, U, V, E>(this, t, "with arg1 " + t) {
            @Override
            public void acceptThrows(U u, V v) throws E{
                wrapped.acceptThrows(t, u, v);
            }
        };
    }




}
