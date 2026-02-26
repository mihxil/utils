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
     *
     * @param t  the input argument
     * @throws E if the operation somehow fails, it throws {@link Exception exceptions} of this type
     */
    void acceptThrows(T t) throws E;

    /**
     * Creates a new Runnable implemented using a {@link Consumer}, simply always consuming that same value.
     * @since 1.17
     * @param value the value to consume
     * @return A new Runnable that will call the given {@link Consumer} with the given value every time it is run.
     */
    default ThrowingRunnable<E> withArg1(T value) {
        return new Consumers.ThrowingRunnableWrapper<ThrowingConsumer<T, E>, E>(this, value, "with arg1 " + value) {
            @Override
            public void runThrows() throws E {
                wrapped.acceptThrows(value);
            }
        };
    }
    /**
     * Morphs this {@link ThrowingConsumer} into a {@link ThrowingBiConsumer}.
     *
     * @param <X> the type of the second argument of the given {@code BiConsumer}, which is ignored
     * @return a new {@code ThrowingBiConsumer} that is calling the given {@code ThrowingConsumer}
     * @since 1.17
     */
    default <X> ThrowingBiConsumer<T, X, E> ignoreArg2() {
        return new Consumers.ThrowingBiWrapper<ThrowingConsumer<T, E>, T, X, E>(this, null, "ignore arg2") {
            @Override
            public void acceptThrows(T t, X u) throws E {
                wrapped.acceptThrows(t);
            }
        };
    }
    /**
     * Morphs this {@link ThrowingConsumer} into a {@link ThrowingBiConsumer}.
     *
     * @param <X> the type of the first argument of the given {@code BiConsumer}, which is ignored
     * @return a new {@code ThrowingBiConsumer} that is calling the given {@code ThrowingConsumer}
     * @since 1.17
     */
    default <X> ThrowingBiConsumer<X, T, E> ignoreArg1() {
        return new Consumers.ThrowingBiWrapper<ThrowingConsumer<T, E>, X, T,  E>(this, null, "ignore arg1") {
            @Override
            public void acceptThrows(X u, T t) throws E {
                wrapped.acceptThrows(t);
            }
        };
    }


}
