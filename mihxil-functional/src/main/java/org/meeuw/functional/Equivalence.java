package org.meeuw.functional;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * A {@link BiPredicate} with tests with an object of the same type, can be considered an 'equivalance'.
 *
 * It is just less then a {@link java.util.Comparator} which for different objects also way which one is 'bigger'.
 * Equivalence is only about equality in some sense.
 *
 * @author Michiel Meeuwissen
 * @since 1.0
 */
@FunctionalInterface
public interface Equivalence<E> extends BiPredicate<E, E>  {

    @Override
    boolean test(E e1, E e2);

    /**
     * Converts this equivalence to {@link Predicate} which checks if objects are equivalent to one certain value.
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
