package org.meeuw.collections;

/**
 * @author Michiel Meeuwissen
 * @since 2.21
 */
public interface CloseablePeekingIterator<E> extends PeekingIterator<E>, CloseableIterator<E> {
}
