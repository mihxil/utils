package org.meeuw.collections;

/**
 * A {@link PeekingIterator} that is also {@link CountedIterator}.
 * @author Michiel Meeuwissen
 * @since 5.1
 */
public interface CountedPeekingIterator<T> extends CountedIterator<T>, CloseablePeekingIterator<T> {
}
