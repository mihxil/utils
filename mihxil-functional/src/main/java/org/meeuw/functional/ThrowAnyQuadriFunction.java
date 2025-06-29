package org.meeuw.functional;


/**
 * As {@link ThrowingSupplier}, just no need to specify the type of the exception.
 *
 * @since 1.13
 */
public interface ThrowAnyQuadriFunction<A, B, C, D, R> extends ThrowingQuadriFunction<A, B, C, D, R, Exception> {

}
