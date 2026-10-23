package org.meeuw.collections;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import org.jspecify.annotations.Nullable;


/**
 * An iterator that is also aware of the current position {@link #getCount()}, and optionally of the size of the object that is iterated {@link #getSize()}, and also optionally of a 'total' size (in case this iterator presents some sub-collection) {@link #getTotalSize()}.
 *
 * @author Michiel Meeuwissen
 * @since 1.18 (since 0.31 in vpro-shared-util)
 */
public interface CountedIterator<T> extends Iterator<T>, CloseableIterator<T>, Counted {

    static <S> CountedIterator<S> of(Collection<S> wrapped) {
        return new BasicWrappedIterator<>(wrapped);
    }

    static <S> CountedPeekingIterator<S> peeking(CountedIterator<S> wrapped) {
        return wrapped == null ? null : wrapped.peeking();
    }

    static <S> CountedIterator<S> of(Stream<S> wrapped) {
        Spliterator<S> spliterator = wrapped.spliterator();
        return new BasicWrappedIterator<>(spliterator.getExactSizeIfKnown(), new CloseableIterator<S>() {
            private final Iterator<S> iterator = Spliterators.iterator(spliterator);

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public S next() {
                return iterator.next();
            }

            @Override
            public void close() {
                wrapped.close();
            }
        });
    }

    static <C> CountedIterator<C> of(Long size, Iterator<C> wrapped) {
        return new BasicWrappedIterator<>(size, wrapped);
    }

    static <C> CountedIterator<C> of(AtomicLong size, Iterator<C> wrapped) {
        return new BasicWrappedIterator<>(size, wrapped);
    }


    /**
     * @return The size, if known, of the collection this iterator is representing
     */
    OptionalLong getSize();

    /**
     * The current position. Will return {@code 1} after first successfull call to {@link #next()}, {@code 2} after the second one, and so on. It will return {@code 0} before the first call.
     */
    @Override
    Long getCount();

    /**
     * If the iterator is in some way restricted you may also want to report a total size, representing the unrestricted size.
     * <p>
     * The default implementation is {@link #getSize()}.
     * @return An optional long containing the 'total' size of something this iterator is a view on
     */
    default OptionalLong getTotalSize() {
        return getSize();
    }

    /**
     * Returns this iterator as {@link Spliterator}.
     */

    default CloseableSpliterator<T> spliterator() {
        OptionalLong size = getSize();
        final Spliterator<T> spliterator;
        if (size.isPresent()) {
             spliterator  = Spliterators.spliterator(this, size.getAsLong(), Spliterator.SIZED);
        } else {
            spliterator = Spliterators.spliteratorUnknownSize(this, 0);
        }
        return CloseableSpliterator.of(spliterator, this);
    }

    @Override
    default void close() throws Exception {

    }

    @Override
    default Stream<T> stream() {
        return CloseableIterator.super.stream();
    }

    /**
     * If you need a {@link PeekingIterator}, this will make you one. It remains also a {@link CountedIterator}
     */
    @Override
    default CountedPeekingIterator<T> peeking() {
        return new CountedPeekingIteratorImpl<>(this);
    }


    static OptionalLong ofNullable(@Nullable Long l) {
        return l == null ? OptionalLong.empty() : OptionalLong.of(l);
    }


}
