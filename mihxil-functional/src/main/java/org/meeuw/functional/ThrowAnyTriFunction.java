package org.meeuw.functional;


/**
 * As {@link ThrowingSupplier}, just no need to specify the type of the exception.
 *
 * @since 1.13
 */
public interface ThrowAnyTriFunction<A, B, C, R> extends ThrowingTriFunction<A, B, C, R, Exception> {

}
