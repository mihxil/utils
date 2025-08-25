package org.meeuw.functional;

import java.util.concurrent.Callable;


/**
 * An extension of {@link Callable} with a type of the exception
 * @since 1.15
 * @param <R> the return type of the callable
 * @param <E> the type of the exception that can be thrown
 */
@FunctionalInterface
public interface ThrowingCallable<R, E extends Exception> extends Callable<R> {

    @Override
    R call() throws E;

}
