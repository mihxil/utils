package org.meeuw.functional;

import java.util.function.Function;
import java.util.function.Supplier;

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
     * @since 1.17
     * @param value
     * @return
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
