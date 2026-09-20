package org.meeuw.collections;

import java.util.Iterator;

import org.meeuw.functional.Unwrappable;


/**
 *
 * @author Michiel Meeuwissen
 * @since 1.18 (since 5.1 in vpro-shared-util)
 */
class CloseablePeekingIteratorImpl<T> implements CloseablePeekingIterator<T>, Unwrappable<CloseableIterator<? extends T>> {
    protected final CloseableIterator<? extends T> iterator;
    private boolean hasPeeked;
    private T peekedElement;

    public CloseablePeekingIteratorImpl(Iterator<? extends T> iterator) {
        this.iterator = CloseableIterator.of(iterator);
    }

    @Override
    public boolean hasNext() {
        return hasPeeked || iterator.hasNext();
    }

    @Override
    public T next() {
        if (!hasPeeked) {
            return iterator.next();
        }
        T result = peekedElement;
        hasPeeked = false;
        peekedElement = null;
        return result;
    }

    @Override
    public void remove() {
        if (hasPeeked) {
            throw new IllegalStateException("Can't remove after you've peeked at next");
        }
        iterator.remove();
    }

    @Override
    public T peek() {
        if (!hasPeeked) {
            peekedElement = iterator.next();
            hasPeeked = true;
        }
        return peekedElement;
    }

    @Override
    public void close() throws Exception {
        iterator.close();
    }

    @Override
    public CloseableIterator<? extends T> unwrap() {
        return iterator;
    }
}
