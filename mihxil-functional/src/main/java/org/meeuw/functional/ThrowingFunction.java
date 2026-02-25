package org.meeuw.functional;

import java.util.Objects;
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

    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <S> the type of output of the {@code after} function, and of the composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then applies the {@code after} function
     * @throws NullPointerException if after is null
     * @see Function#andThen(Function)
     * @since 1.17
     */
    default <S> ThrowingFunction<A,S, E> andThen(ThrowingFunction<? super R, ? extends S, ? extends E> after) {
        Objects.requireNonNull(after);
        return new Functions.ThrowingMonoWrapper<ThrowingFunction<A, R, E>, A, S, E>(this, after, "and then " + after) {
            @Override
            public S applyWithException(A a) throws E {
                R result = wrapped.applyWithException(a);
                return after.apply(result);
            }
        };
    }


    /**
     * Morphs this {@link ThrowingFunction} into a {@link ThrowingSupplier}, with a certain given value for the argument
     * @param value the argument to always supply to this function's first argument
     * @return a new {@code ThrowingSupplier}, which will use the given function and argument for its implementation
     * @since 1.17
     */
     default ThrowingSupplier<R, E> withArg1(A value) {
        return new Suppliers.ThrowingSupplierWrapper<R, ThrowingFunction<A, R, E>, E>(this, "with arg1 " + value) {
            @Override
            public R getThrows() throws E {
                return wrapped.applyWithException(value);
            }
        };
    }

    /**
     * Creates a new {@link ThrowingBiFunction} using this {@link ThrowingFunction}, simply completely ignoring the second argument
     * @param <X> type of second argument to the resulting {@link ThrowingBiFunction}  (ignored)
     * @return the new {@code ThrowingBiFunction}
     * @since 1.17
     */
    default <X> ThrowingBiFunction<A, X, R, E> ignoreArg2() {
        return new Functions.ThrowingBiWrapper<ThrowingFunction<A, R, E>,  A, X, R , E>(this, null, "ignore arg2") {

            @Override
            public R applyWithException(A a, X x) throws E {
                return wrapped.applyWithException(a);
            }
        };
    }

    /**
     * Creates a new {@link ThrowingBiFunction} using this {@link ThrowingFunction}, simply completely ignoring the first argument
     * @param <X> type of first argument to the resulting {@link ThrowingBiFunction}  (ignored)
     * @return the new {@code ThrowingBiFunction}
     * @since 1.17
     */
    default <X> ThrowingBiFunction<X, A, R, E> ignoreArg1() {
        return new Functions.ThrowingBiWrapper<ThrowingFunction<A, R, E>,  X, A,  R , E>(this, null, "ignore arg1") {
            @Override
            public R applyWithException(X x, A a) throws E {
                return wrapped.applyWithException(a);
            }
        };
    }

}
