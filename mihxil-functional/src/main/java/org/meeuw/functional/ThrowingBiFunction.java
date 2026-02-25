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
     * Morphs this {@link ThrowingBiFunction} into a {@link ThrowingFunction}, with a certain given value for the second argument.
     * @param value the argument to always supply to this function second argument
     * @return a new {@code ThrowingFunction}, which will use the given function and argument for its implementation
     * @since 1.17
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
     * Morphs this {@link ThrowingBiFunction} into a {@link ThrowingFunction}, with a certain given value for the first argument.
     * @param value the argument to always supply to this function first argument
     * @return a new {@code ThrowingFunction}, which will use the given function and argument for its implementation
     * @since 1.17
     */
    default ThrowingFunction<B, R, E> withArg1(A value) {
        return new Functions.ThrowingMonoWrapper<ThrowingBiFunction<A, B, R, E>, B, R,  E>(this, value, "with arg1 " + value) {
            @Override
            public R applyWithException(B b) throws E {
                return wrapped.applyWithException(value, b);
            }
        };
    }


    /**
     * Creates a new {@link ThrowingTriFunction} using this {@link ThrowingBiFunction}, simply completely ignoring the third argument
     * @param <X> type of third argument to the resulting {@link ThrowingTriFunction}  (ignored)
     * @return the new {@code ThrowingBiFunction}
     * @since 1.17
     */
    default <X> ThrowingTriFunction<A, B, X,  R, E> ignoreArg3() {
        return new Functions.ThrowingTriWrapper<ThrowingBiFunction<A, B, R, E>,  A, B, X,  R , E>(this, null, "ignore arg3") {
            @Override
            public R applyWithException(A a, B b, X x) throws E {
                return wrapped.applyWithException(a, b);
            }
        };
    }


    /**
     * Creates a new {@link ThrowingTriFunction} using this {@link ThrowingBiFunction}, simply completely ignoring the second argument
     * @param <X> type of second argument to the resulting {@link ThrowingTriFunction}  (ignored)
     * @return the new {@code ThrowingBiFunction}
     * @since 1.17
     */
    default <X> ThrowingTriFunction<A, X, B, R, E> ignoreArg2() {
        return new Functions.ThrowingTriWrapper<ThrowingBiFunction<A, B, R, E>,  A, X, B, R , E>(this, null, "ignore arg2") {
            @Override
            public R applyWithException(A a, X x, B b) throws E {
                return wrapped.applyWithException(a, b);
            }
        };
    }


    /**
     * Creates a new {@link ThrowingTriFunction} using this {@link ThrowingBiFunction}, simply completely ignoring the third argument
     * @param <X> type of third argument to the resulting {@link ThrowingTriFunction}  (ignored)
     * @return the new {@code ThrowingBiFunction}
     * @since 1.17
     */
    default <X> ThrowingTriFunction<X, A, B,  R, E> ignoreArg1() {
        return new Functions.ThrowingTriWrapper<ThrowingBiFunction<A, B, R, E>,  X, A, B,  R , E>(this, null, "ignore arg1") {
            @Override
            public R applyWithException(X x, A a, B b) throws E {
                return wrapped.applyWithException(a, b);
            }
        };
    }


}
