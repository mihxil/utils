package org.meeuw.functional;


/**
 * As {@link ThrowingCallable}, just no need to specify the type of the exception.
 *
 * @since 1.14
 */
public interface ThrowAnyCallable<R> extends ThrowingCallable<R, Exception> {

}
