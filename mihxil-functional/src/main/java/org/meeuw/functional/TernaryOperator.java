package org.meeuw.functional;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.BinaryOperator;

/**
 * Represents an operation upon three operands of the same type, producing a result
 * of the same type as the operands.  This is a specialization of
 * {@link TriFunction} for the case where the operands and the result are all of
 * the same type.
 *
 * @param <T> the type of the operands and result of the operator
 *
 * @see BinaryOperator
 * @author Michiel Meeuwissen
 * @since 0.1
 */
@FunctionalInterface
public interface TernaryOperator<T> extends TriFunction<T, T, T, T> {

     /**
     * Returns a {@link TernaryOperator} which returns the lesser of three elements
     * according to the specified {@code Comparator}.
     *
     * @param <T> the type of the input arguments of the comparator
     * @param comparator a {@code Comparator} for comparing the two values
     * @return a {@code TernaryOperator} which returns the lesser of its operands,
     *         according to the supplied {@code Comparator}
     * @throws NullPointerException if the argument is null
     */
    static <T> TernaryOperator<T> minBy(Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);
        return new  TernaryOperatorWrapper<Comparator<? super T>, T>(comparator, "min by " + comparator) {
            @Override
            public T apply(T a, T b, T c) {
                T smaller = comparator.compare(a, b) <= 0 ? a : b;
                return comparator.compare(smaller, c) <= 0 ? smaller : c;
            }
        };
    }

    /**
     * Returns a {@link TernaryOperator} which returns the greater of three elements
     * according to the specified {@code Comparator}.
     *
     * @param <T> the type of the input arguments of the comparator
     * @param comparator a {@code Comparator} for comparing the two values
     * @return a {@code TernaryOperator} which returns the greater of its operands,
     *         according to the supplied {@code Comparator}
     * @throws NullPointerException if the argument is null
     */
    static <T> TernaryOperator<T> maxBy(Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);
        return new  TernaryOperatorWrapper<Comparator<? super T>, T>(comparator, "max by " + comparator) {
            @Override
            public T apply(T a, T b, T c) {
                T bigger = comparator.compare(a, b) >= 0 ? a : b;
                return comparator.compare(bigger, c) >= 0 ? bigger : c;
            }
        };
    }

}
