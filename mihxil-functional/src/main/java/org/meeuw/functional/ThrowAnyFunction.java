package org.meeuw.functional;


/**
 * As {@link ThrowingFunction}, just no need to specify the type of the exception.
 *
 * @since 1.13
 */
public interface ThrowAnyFunction<A, R> extends ThrowingFunction<A, R, Exception> {

}
