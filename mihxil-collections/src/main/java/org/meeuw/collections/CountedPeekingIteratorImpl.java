package org.meeuw.collections;

import java.util.Optional;



/**
 *
 * @author Michiel Meeuwissen
 * @since 1.18 (since 5.1 in vpro-shared-util)
 */
@SuppressWarnings("rawtypes")
class CountedPeekingIteratorImpl<T> extends CloseablePeekingIteratorImpl<T> implements  CountedPeekingIterator<T> {

    private final CountedIterator<? extends T> wrapped;

    public CountedPeekingIteratorImpl(CountedIterator<? extends T> iterator) {
        super(iterator);
        this.wrapped = iterator;
    }

    @Override
    public Optional<Long> getSize() {
        return wrapped.getSize();
    }

    @Override
    public Long getCount() {
        return ((CountedIterator) iterator).getCount();
    }

    @Override
    public Optional<Long> getTotalSize() {
        return wrapped.getTotalSize();
    }

    @Override
    public CountedPeekingIterator<T> peeking() {
        return this;
    }
}
