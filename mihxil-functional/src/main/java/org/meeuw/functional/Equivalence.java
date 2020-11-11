package org.meeuw.functional;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * A {@link BiPredicate} with tests to object with the same type, can be considered an 'equivalance'.
 *
 * It just less then a {@link java.util.Comparator} which for different objects also way which one is 'bigger'.
 * Equivalence is only about equality in some sense.
 *
 * @author Michiel Meeuwissen
 * @since 1.0
 */
@FunctionalInterface
public interface Equivalence<E> extends BiPredicate<E, E>  {

    @Override
    boolean test(E e1, E e2);

    default Predicate<E> predicate(E e) {
        return new Predicates.MonoWrapper<Equivalence<E>, E>(this, e, "equivalent to " + e) {
            @Override
            public boolean test(E  o) {
                return wrapped.test(e, o);
            }

            @Override
            public String toString() {
                return reason;
            }
        };

    }

}
