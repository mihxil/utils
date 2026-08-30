package org.meeuw.collections;

import java.util.Iterator;

/**
 * An {@link Iterator} that supports a one-element lookahead while iterating.
 * <p>
 * Equivalent to the guava version, but without the pretty big dependency
 *
 * @author Michiel Meeuwissen
 * @since 1.19
 */
public interface PeekingIterator<E> extends Iterator<E> {

    /**
     * Returns the next element in the iteration without advancing it
     */
    E peek();

}
