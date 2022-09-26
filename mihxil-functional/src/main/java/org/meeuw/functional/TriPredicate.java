package org.meeuw.functional;

import java.util.Objects;
import java.util.function.BiPredicate;

/**
 * The next in succession of {@link java.util.function.Predicate} and {@link BiPredicate}.
 * <p>
 * A predicate with three arguments
 *
 * @param <T> the type of the first argument to the predicate
 * @param <U> the type of the second argument to the predicate
 * @param <V> the type of the third argument to the predicate
 * @author Michiel Meeuwissen
 * @since 0.1
 */
@FunctionalInterface
public interface TriPredicate<T, U, V> {

    /**
     * Evaluates this predicate on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     * @param v the third input argument
     * @return {@code true} if the input arguments match the predicate,
     * otherwise {@code false}
     */
    boolean test(T t, U u, V v);


    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * AND of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code false}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ANDed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * AND of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default TriPredicate<T, U, V> and(TriPredicate<? super T, ? super U,? super V> other) {
        Objects.requireNonNull(other);
        return new Predicates.TriWrapper<TriPredicate<? super T, ? super U, ? super V>, T, U, V>(this, other, "and " + other) {
            @Override
            public boolean test(T t, U u, V v) {
                return wrapped.test(t, u, v) && other.test(t, u, v);
            }
        };
    }

    /**
     * Returns a predicate that represents the logical negation of this
     * predicate.
     *
     * @return a predicate that represents the logical negation of this
     * predicate
     */
    default TriPredicate<T, U, V> negate() {
        return new Predicates.TriWrapper<TriPredicate<T, U, V>, T, U, V>(this, null, " negated") {
            @Override
            public boolean test(T t, U u, V v) {
                return ! wrapped.test(t, u, v);
            }
        };
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * OR of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code true}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ORed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * OR of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default TriPredicate<T, U, V> or(TriPredicate<? super T, ? super U, ? super V> other) {
        Objects.requireNonNull(other);
        return new Predicates.TriWrapper<TriPredicate<? super T, ? super U, ? super V>, T, U, V>(this, other, "and " + other) {
            @Override
            public boolean test(T t, U u, V v) {
                return wrapped.test(t, u, v) || other.test(t, u, v);
            }
        };
    }

    /**
     * Morphs this {@link TriPredicate} into a {@link BiPredicate}, by filling in the third argument
     * @param value the value needed as the third argument of this {@code TriPredicate}
     * @return a new {@code BiPredicate} with the desired behaviour
     */
    default BiPredicate<T, U> withArg3(V value) {
        return new Predicates.BiWrapper<TriPredicate<T, U, V>, T, U>(this, value, "with arg3 " + value) {
            @Override
            public boolean test(T t, U u) {
                return wrapped.test(t, u, value);
            }
        };
    }

    /**
     * Morphs this {@link TriPredicate} into a {@link BiPredicate}, by filling in the second argument
     * @param value the value needed as the second argument of this {@code TriPredicate}
     * @return a new {@code BiPredicate} with the desired behaviour
     */
    default BiPredicate<T, V> withArg2(U value) {
        return new Predicates.BiWrapper<TriPredicate<T, U, V>, T, V>(this, value, "with arg2 " + value) {
            @Override
            public boolean test(T t, V v) {
                return wrapped.test(t, value, v);
            }
        };
    }

    /**
     * Morphs this {@link TriPredicate} into a {@link BiPredicate}, by filling in the first argument
     * @param value the value needed as the first argument of this {@code TriPredicate}
     * @return a new {@code BiPredicate} with the desired behaviour
     */
    default BiPredicate<U, V> withArg1(T value) {
        return new Predicates.BiWrapper<TriPredicate<T, U, V>, U, V>(this, value, "with arg1 " + value) {
            @Override
            public boolean test(U u, V v) {
                return wrapped.test(value, u, v);
            }
        };
    }


}
