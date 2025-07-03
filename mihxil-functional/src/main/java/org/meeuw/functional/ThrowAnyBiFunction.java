package org.meeuw.functional;


/**
 * As {@link ThrowingBiFunction}, just no need to specify the type of the exception.
 *
 * @since 1.13
 */
@FunctionalInterface
public interface ThrowAnyBiFunction<A, B,  R> extends ThrowingBiFunction<A, B, R, Exception> {

}
