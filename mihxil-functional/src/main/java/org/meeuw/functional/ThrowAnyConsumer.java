package org.meeuw.functional;

/**
 * As {@link ThrowingConsumer}, just no need to specify the type of the exception.
 *
 * @since 1.8
 */
@FunctionalInterface
public interface ThrowAnyConsumer<T> extends ThrowingConsumer<T, Exception> {

}
