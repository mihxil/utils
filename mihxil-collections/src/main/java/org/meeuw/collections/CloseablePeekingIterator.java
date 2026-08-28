package org.meeuw.collections;

import com.google.common.collect.PeekingIterator;

/**
 * @author Michiel Meeuwissen
 * @since 2.21
 */
public interface CloseablePeekingIterator<E> extends PeekingIterator<E>, CloseableIterator<E> {
}
