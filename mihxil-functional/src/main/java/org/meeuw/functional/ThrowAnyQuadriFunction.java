package org.meeuw.functional;


/**
 * As {@link ThrowingQuadriFunction}, just no need to specify the type of the exception.
 *
 * @since 1.13
 * @param <A> the type of the first argument to the function
 * @param <B> the type of the second argument to the function
 * @param <C> the type of the third argument to the function
 * @param <D> the type of the fourth argument to the function
 * @param <R> the type of the result of the function
 */
@FunctionalInterface
public interface ThrowAnyQuadriFunction<A, B, C, D, R> extends ThrowingQuadriFunction<A, B, C, D, R, Exception> {

}
