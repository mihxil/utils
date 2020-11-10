package org.meeuw.functional;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * The next in succession of {@link Consumer} and {@link BiConsumer}.
 *
 * A function with three arguments
 *
 * @author Michiel Meeuwissen
 * @since 0.1
 */
@FunctionalInterface
public interface TriConsumer<T, U, V> {

    void accept(T t, U u, V v);

    /**
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

    default BiConsumer<U, V> withArg1(T t) {
        return new Consumers.BiWrapper<TriConsumer<T, U, V>, U, V>(this,  t, "with arg1 " + t) {
            @Override
            public void accept(U u, V v) {
                wrapped.accept(t, u, v);
            }
        };
    }

    default BiConsumer<T, V> withArg2(U u) {
        return new Consumers.BiWrapper<TriConsumer<T, U, V>, T, V>(this,  u, "with arg2 " + u) {
            @Override
            public void accept(T t, V v) {
                wrapped.accept(t, u, v);
            }
        };
    }

    default BiConsumer<T, U> withArg3(V v) {
        return new Consumers.BiWrapper<TriConsumer<T, U, V>, T, U>(this,  v, "with arg3 " + v) {
            @Override
            public void accept(T t, U u) {
                wrapped.accept(t, u, v);
            }
        };
    }
}
