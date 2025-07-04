package org.meeuw.functional;


/**
 * As {@link ThrowingBiFunction}, just no need to specify the type of the exception.
 *
 * @since 1.13
 * @param <A> the type of the first argument to the function
 * @param <B> the type of the second argument to the function
 * @param <R> the type of the result of the function
 */
@FunctionalInterface
public interface ThrowAnyBiFunction<A, B,  R> extends ThrowingBiFunction<A, B, R, Exception> {

}
