package org.meeuw.functional;


/**
 * As {@link ThrowingQuadriFunction}, just no need to specify the type of the exception.
 *
 * @since 1.13
 */
@FunctionalInterface
public interface ThrowAnyQuadriFunction<A, B, C, D, R> extends ThrowingQuadriFunction<A, B, C, D, R, Exception> {

}
