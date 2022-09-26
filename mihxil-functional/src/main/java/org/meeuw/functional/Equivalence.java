package org.meeuw.functional;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * A {@link BiPredicate} which tests two objects of the same type, can be considered an 'equivalence'.
 * <p>
 * It is just a bit less than a {@link java.util.Comparator} which for different objects also knows which one is 'bigger'.  Equivalence is only about equality in some sense (i.e. {@link #test(Object, Object)}), leaving size out of the consideration.
 *
 * @param <E> the type of the arguments to the equivalence
 * @author Michiel Meeuwissen
 * @since 1.0
 */
@FunctionalInterface
public interface Equivalence<E> extends BiPredicate<E, E>  {

    /**
     * Evaluates the equivalence test on the two input arguments.
     *
     * @return {@code true} if two input arguments are 'equivalent'
     */
    @Override
    boolean test(E e1, E e2);

    /**
     * Converts this equivalence to {@link Predicate} which checks if objects are equivalent to one certain value.
     * <p>
     * The resulting object implements {@link Object#equals(Object)} and {@link Object#hashCode()} based on the parameter.
     *
     * @param value the test object to compare to
     * @return a new {@code Predicate<E>} that just compares other objects to the given test object
     */
    default Predicate<E> predicate(E value) {
        return new Predicates.MonoWrapper<Equivalence<E>, E>(this, value, "equivalent to " + value) {
            @Override
            public boolean test(E  o) {
                return wrapped.test(value, o);
            }

            @Override
            public String toString() {
                return reason;
            }
        };
    }

}
