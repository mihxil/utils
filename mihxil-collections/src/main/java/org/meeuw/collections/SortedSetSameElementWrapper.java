package org.meeuw.collections;

import java.util.SortedSet;

/**
 * @author Michiel Meeuwissen
 * @since 1.18 (since 2.3.1 in vpro-shared-util)
 */
public abstract class SortedSetSameElementWrapper<T> extends SortedSetElementWrapper<T, T> {
    public SortedSetSameElementWrapper(SortedSet<T> wrapped) {
        super(wrapped);
    }

    @Override
    protected T find(T element) {
        return element;
    }

    @Override
    public boolean add(T element) {
        return wrapped.add(element);
    }
}
