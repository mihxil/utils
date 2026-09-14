package org.meeuw.functional;

import java.util.function.*;

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

    /**
     * Returns a composed {@code ThrowingBiConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code BiConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */

    default ThrowingBiConsumer<T, U,  E> andThen(ThrowingBiConsumer<? super T, ? super U, ? extends E> after) {
        if (after == null) {
            throw new NullPointerException("after cannot be null");
        }
        return (T t, U u) -> {
            acceptThrows(t, u);
            after.acceptThrows(t, u);
        };
    }

    /**
     * Performs this operation on the given argument, while allowing for an exception.
     * @param t  the first input argument
     * @param u  the second input argument
     * @throws E if the operation somehow fails, it throws {@link Exception}s of this type
     */
    void acceptThrows(T t, U u) throws E;

    /**
     * Creates a new ThrowingConsumer implemented using this {@link ThrowingBiConsumer}, simply always consuming the same value for the second argument
     *
     * @param value the value to consume
     * @return A new ThrowingConsumer that will call the current {@link ThrowingBiConsumer} with the given value for the second argument every time it is run.
     * @since 1.17
     */
    default ThrowingConsumer<T, E> withArg2(U value) {
        return new Consumers.ThrowingMonoWrapper<ThrowingBiConsumer<T, U, E>, T, E>(this, value, "with arg2") {
            @Override
            public void acceptThrows(T t) throws E{
                wrapped.acceptThrows(t, value);
            }
        };
    }

    /**
     * Creates a new ThrowingConsumer implemented using this {@link ThrowingBiConsumer}, simply always consuming the same value for the second argument
     *
     * @param value the value to consume
     * @return A new ThrowingConsumer that will call the current {@link ThrowingBiConsumer} with the given value for the second argument every time it is run.
     * @since 1.17
     */
    default ThrowingConsumer<T, E> withArg2Supplier(Supplier<U> value) {
        return new Consumers.ThrowingMonoWrapper<ThrowingBiConsumer<T, U, E>, T, E>(this, value, "with arg2") {
            @Override
            public void acceptThrows(T t) throws E{
                wrapped.acceptThrows(t, value.get());
            }
        };
    }



    /**
     * Creates a new ThrowingConsumer implemented using this {@link ThrowingBiConsumer}, simply always consuming the same value for the fist argument
     *
     * @param value the value to consume
     * @return A new ThrowingConsumer that will call the current {@link ThrowingBiConsumer} with the given value for the first argument every time it is run.
     * @since 1.17
     */
    default ThrowingConsumer<U, E> withArg1(T value) {
        return new Consumers.ThrowingMonoWrapper<ThrowingBiConsumer<T, U, E>, U, E>(this, value, "with arg1") {
            @Override
            public void acceptThrows(U u) throws E{
                wrapped.acceptThrows(value, u);
            }
        };
    }

    /**
     * Creates a new ThrowingConsumer implemented using this {@link ThrowingBiConsumer}, simply always consuming the same value for the fist argument
     *
     * @param value the value to consume
     * @return A new ThrowingConsumer that will call the current {@link ThrowingBiConsumer} with the given value for the first argument every time it is run.
     * @since 1.17
     */
    default ThrowingConsumer<U, E> withArg1Supplier(Supplier<T> value) {
        return new Consumers.ThrowingMonoWrapper<ThrowingBiConsumer<T, U, E>, U, E>(this, value, "with arg1") {
            @Override
            public void acceptThrows(U u) throws E{
                wrapped.acceptThrows(value.get(), u);
            }
        };
    }

    /**
     * Creates a new ThrowingTriConsumer implemented using this {@link ThrowingBiConsumer}, simply ignoring the third argument
     * @param <X> Typed of ignored third argument
     * @return A new ThrowingTriConsumer that will call the current {@link ThrowingBiConsumer} with the given value for the third argument every time it is run.
     */
    default <X> ThrowingTriConsumer<T, U, X, E> ignoreArg3() {
        return new Consumers.ThrowingTriWrapper<ThrowingBiConsumer<T, U, E>, T, U, X,  E>(this, null, "ignore arg2") {
            @Override
            public void acceptThrows(T t, U u, X v) throws E {
                wrapped.acceptThrows(t, u);
            }
        };
    }
    /**
     * Creates a new ThrowingTriConsumer implemented using this {@link ThrowingBiConsumer}, simply ignoring the second argument
     * @since 1.17
     * @param <X> Typed of ignored second argument
     * @return A new ThrowingTriConsumer that will call the current {@link ThrowingBiConsumer} with the given value for the second argument every time it is run.
     */
    default <X> ThrowingTriConsumer<T, X, U, E> ignoreArg2() {
        return new Consumers.ThrowingTriWrapper<ThrowingBiConsumer<T, U, E>, T, X, U,  E>(this, null, "ignore arg2") {
            @Override
            public void acceptThrows(T t, X u, U v) throws E {
                wrapped.acceptThrows(t, v);
            }
        };
    }

    /**
     * Creates a new ThrowingTriConsumer implemented using this {@link ThrowingBiConsumer}, simply ignoring the first argument
     * @since 1.17
     * @param <X> Typed of ignored first argument
     * @return A new ThrowingTriConsumer that will call the current {@link ThrowingBiConsumer} with the given value for the first argument every time it is run.
     */
    default <X> ThrowingTriConsumer<X, T, U, E> ignoreArg1() {
        return new Consumers.ThrowingTriWrapper<ThrowingBiConsumer<T, U, E>, X, T,  U,  E>(this, null, "ignore arg1") {
            @Override
            public void acceptThrows(X t, T u, U v) throws E {
                wrapped.acceptThrows(u, v);
            }
        };
    }





}
