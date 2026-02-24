package org.meeuw.functional;

/**
 * As {@link ThrowingBiConsumer}, just no need to specify the type of the exception.
 *
 * @since 1.17
 * @param <T> the type of the input to the operation
 * @param <U> the type of the second input to the operation
 * @param <V> the type of the third input to the operation
 */
@FunctionalInterface
public interface ThrowAnyTriConsumer<T, U, V> extends ThrowingTriConsumer<T, U, V, Exception> {

}
