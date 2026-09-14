package org.meeuw.collections;

import lombok.ToString;

import java.util.*;
import java.util.function.BiFunction;

/**
 * A wrapping iterator with the option to skip certain entries (based on comparing with the previous entry)
 * @author Michiel Meeuwissen
 * @since 1.68
 */
@ToString
public class SkippingIterator<T> implements Iterator<T> {

    private final Iterator<T> wrapped;

    private final BiFunction<T, T, Boolean> comparator;

    private Boolean hasNext = null;

    private T next;

    private boolean foundFirst = false;

    @lombok.Builder(builderClassName = "Builder")
    public SkippingIterator(
        Iterator<T> wrapped,
        BiFunction<T, T, Boolean> comparator) {
        this.wrapped = wrapped;
        this.comparator = comparator == null ? Objects::equals : comparator;
    }

    /**
     * Default comparator is {@link Objects#equals(Object, Object)}
     * @param wrapped
     */
    public SkippingIterator(
        Iterator<T> wrapped) {
        this(wrapped, null);
    }


    @Override
    public boolean hasNext() {
        findNext();
        return hasNext;
    }

    @Override
    public T next() {
        findNext();
        if (hasNext) {
            hasNext = null;
            return next;
        } else {
            throw new NoSuchElementException();
        }
    }

    protected void findNext() {

        if (hasNext == null) {

            hasNext = false;

            while (wrapped.hasNext()) {
                boolean hadFirst = this.foundFirst;
                this.foundFirst = true;
                T n = wrapped.next();
                T previous = next;
                if (hadFirst && comparator.apply(previous, n)) {
                    continue;
                } else {
                    hasNext = true;
                    next = n;
                    break;
                }
            }
        }
    }


}
