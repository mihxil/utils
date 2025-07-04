package org.meeuw.functional;


/**
 * As {@link ThrowingFunction}, just no need to specify the type of the exception.
 *
 * @since 1.13
 * @param <A> the type of the first argument to the function
 * @param <R> the type of the result of the function
 */
@FunctionalInterface
public interface ThrowAnyFunction<A, R> extends ThrowingFunction<A, R, Exception> {

}
