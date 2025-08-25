package org.meeuw.functional;

import java.util.function.Function;

import static org.meeuw.functional.Sneaky.sneakyThrow;


/**
 * An extension of {@link Function} that can throw exceptions too.
 * @since 1.13
 * @param <A> the type of the input to the function
 * @param <R> the type of the result of the function
 * @param <E> the type of exception that can be thrown
 */
@FunctionalInterface
public interface ThrowingFunction<A, R, E extends Exception> extends Function<A, R> {
    @Override
    default R apply(A a) {
        try {
            return applyWithException(a);
        } catch (final Exception e) {
            return sneakyThrow(e);
        }
    }

     /**
     * Applies this function to the given arguments.
     * @param a  the first function argument
     * @return the function result
     * @throws E Checked exception that might occur.
     */
    R applyWithException(A a) throws E;

}
