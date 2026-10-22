package org.meeuw.collections;

import java.util.*;

import org.meeuw.functional.Unwrappable;

/**
 * @author Michiel Meeuwissen
 * @since 1.18 (since 1.3 in vpro-shared-util)
 */
public  abstract class WrappedIterator<T, S> implements CountedIterator<S>, Unwrappable<CloseableIterator<T>> {

    protected final CloseableIterator<T> wrapped;

    public WrappedIterator(Iterator<T> wrapped) {
        this.wrapped = CloseableIterator.of(wrapped);
    }

    @Override
    public boolean hasNext() {
        return iterator().hasNext();
    }

    @Override
    public abstract S next();

    @Override
    public void remove() {
        iterator().remove();
    }

    protected Iterator<T> iterator() {
        return wrapped;
    }


    @SuppressWarnings({"rawtypes"})
    @Override
    public OptionalLong getSize() {
        if (wrapped instanceof CountedIterator) {
            return ((CountedIterator) wrapped).getSize();
        }
        return OptionalLong.empty();

    }

    @SuppressWarnings("rawtypes")
    @Override
    public Long getCount() {
        if (wrapped instanceof CountedIterator) {
            return ((CountedIterator) wrapped).getCount();
        }
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public OptionalLong getTotalSize() {
        if (wrapped instanceof CountedIterator) {
            return ((CountedIterator) wrapped).getTotalSize();
        }
        return OptionalLong.empty();

    }

    @Override
    public void close() throws Exception {
        wrapped.close();
    }

    @Override
    public CloseableIterator<T> unwrap() {
        return wrapped;
    }


    @Override
    public String toString() {
        return "Counted[" + wrapped  + "]";
    }
}
