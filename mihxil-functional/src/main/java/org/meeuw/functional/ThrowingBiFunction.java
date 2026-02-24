package org.meeuw.functional;

import java.util.function.*;

import static org.meeuw.functional.Sneaky.sneakyThrow;


/**
 * An extension of {@link BiFunction} that can throw exceptions too.
 * @since 1.13
 * @param <A> the type of the first argument to the function
 * @param <B> the type of the second argument to the function
 * @param <R> the type of the result of the function
 * @param <E> the type of the exception that can be thrown by this function
 */
@FunctionalInterface
public interface ThrowingBiFunction<A, B, R, E extends Exception> extends BiFunction<A, B, R> {
    @Override
    default R apply(A a, B b) {
        try {
            return applyWithException(a, b);
        } catch (final Exception e) {
            return sneakyThrow(e);
        }
    }

    /**
     * Applies this function to the given arguments.
     * @param a  the first function argument
     * @param b  the second function argument
     * @return the function result
     * @throws E Checked exception that might occur.
     */
    R applyWithException(A a, B b) throws E;



    /**
     * @since 1.17
     * @param value
     * @return
     */
    default ThrowingFunction<A, R, E> withArg2(B value) {
         return new Functions.ThrowingMonoWrapper<ThrowingBiFunction<A, B, R, E>, A, R,  E>(this, value, "with arg2 " + value) {
             @Override
             public R applyWithException(A a) throws E {
                 return wrapped.applyWithException(a, value);
             }
         };

    }

    /**
     * @since 1.17
     * @param value
     * @return
     */
    default ThrowingFunction<B, R, E> withArg1(A value) {
        return new Functions.ThrowingMonoWrapper<ThrowingBiFunction<A, B, R, E>, B, R,  E>(this, value, "with arg1 " + value) {
            @Override
            public R applyWithException(B b) throws E {
                return wrapped.applyWithException(value, b);
            }
        };
    }



}
