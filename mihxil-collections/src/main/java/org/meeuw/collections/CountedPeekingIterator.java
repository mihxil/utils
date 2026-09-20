package org.meeuw.collections;

/**
 * A {@link PeekingIterator} that is also {@link CountedIterator}.
 * @author Michiel Meeuwissen
 * @since 1.18 (since 5.1 in vpro-shared-util)
 */
public interface CountedPeekingIterator<T> extends CountedIterator<T>, CloseablePeekingIterator<T> {
}
