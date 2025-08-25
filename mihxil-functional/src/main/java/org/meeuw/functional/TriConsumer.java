package org.meeuw.functional;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * The next in succession of {@link Consumer} and {@link BiConsumer}.
 * <p>
 * A void function with three arguments. Expected to have side effects.
 *
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the operation
 * @param <V> the type of the third argument to the operation
 *
 * @see Consumer
 * @see BiConsumer
 * @author Michiel Meeuwissen
 * @since 0.1
 */
@FunctionalInterface
public interface TriConsumer<T, U, V> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     * @param v the third input argument
     */
    void accept(T t, U u, V v);

    /**
     * Returns a composed TriConsumer that performs, in sequence, this operation followed by the after operation. If performing either operation throws an exception, it is relayed to the caller of the composed operation. If performing this operation throws an exception, the after operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code TriConsumer} that performs in sequence this operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     * @see Consumer#andThen(Consumer)
     */
    default TriConsumer<T, U, V> andThen(TriConsumer<? super T, ? super U, ? super V> after) {
        Objects.requireNonNull(after);
        return new Consumers.TriWrapper<TriConsumer<T, U, V>, T, U, V>(this, after, "and then " + after) {
            @Override
            public void accept(T t, U u, V v) {
                wrapped.accept(t, u, v);
                after.accept(t, u, v);
            }
        };
    }



    /**
     * Morphs this {@link TriConsumer} into a {@link BiConsumer} where the value for the third argument will be fixed.
     * @param value The fixed value for the third argument of this {@code TriConsumer}
     * @return a new {@code BiConsumer} that is calling this {@code TriConsumer}, and uses the given value for the third argument
     */
    default BiConsumer<T, U> withArg3(V value) {
        return new Consumers.BiWrapper<TriConsumer<T, U, V>, T, U>(this,  value, "with arg3 " + value) {
            @Override
            public void accept(T t, U u) {
                wrapped.accept(t, u, value);
            }
        };
    }

    /**
     * Morphs this {@link TriConsumer} into a {@link BiConsumer} where the value for the second argument will be fixed.
     * @param value The fixed value for the second argument of this {@code TriConsumer}
     * @return a new {@code BiConsumer} that is calling this {@code TriConsumer}, and uses the given value for the second argument
     */
    default BiConsumer<T, V> withArg2(U value) {
        return new Consumers.BiWrapper<TriConsumer<T, U, V>, T, V>(this,  value, "with arg2 " + value) {
            @Override
            public void accept(T t, V v) {
                wrapped.accept(t, value, v);
            }
        };
    }

    /**
     * Morphs this {@link TriConsumer} into a {@link BiConsumer} where the value for the first argument will be fixed.
     * @param value The fixed value for the first argument of this {@code TriConsumer}
     * @return a new {@code BiConsumer} that is calling this {@code TriConsumer}, and uses the given value for the first argument
     */
    default BiConsumer<U, V> withArg1(T value) {
        return new Consumers.BiWrapper<TriConsumer<T, U, V>, U, V>(this,  value, "with arg1 " + value) {
            @Override
            public void accept(U u, V v) {
                wrapped.accept(value, u, v);
            }
        };
    }
}
