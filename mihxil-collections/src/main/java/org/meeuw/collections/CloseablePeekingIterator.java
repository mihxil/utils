package org.meeuw.collections;

/**
 * @author Michiel Meeuwissen
 * @since 1.18 (since 2.21 in vpro-shared-util)
 */
public interface CloseablePeekingIterator<E> extends PeekingIterator<E>, CloseableIterator<E> {
}
