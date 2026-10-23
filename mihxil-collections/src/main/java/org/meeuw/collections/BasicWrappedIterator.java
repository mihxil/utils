package org.meeuw.collections;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

import org.jspecify.annotations.Nullable;

import static org.meeuw.functional.Optionals.ofNullable;


/**
 * @author Michiel Meeuwissen
 * @since 1.18 (since 0.31 in vpro-shared-util)
 */
public class BasicWrappedIterator<T> extends WrappedIterator<T, T> {

    private final @Nullable Supplier<@Nullable Long> size;
    private final @Nullable Supplier<@Nullable Long> totalSize;

    private long count = 0;

    public BasicWrappedIterator(Iterator<T> wrapped) {
        super(wrapped);
        size = null;
        totalSize = null;
    }

    @lombok.Builder(builderClassName = "Builder")
    protected BasicWrappedIterator(
        @Nullable Supplier<@Nullable Long> sizeSupplier,
        @Nullable Supplier<@Nullable Long> totalSizeSupplier,
        @Nullable Long size,
        @Nullable Long totalSize,
        Iterator<T> wrapped) {
        super(wrapped);
        this.size = sizeSupplier != null ? sizeSupplier : () -> size;
        this.totalSize = totalSizeSupplier != null ? totalSizeSupplier :  () -> totalSize;
    }


    public BasicWrappedIterator(Long size, Long totalSize, Iterator<T> wrapped) {
        this(null, null, size, totalSize, wrapped);
    }

    public BasicWrappedIterator(AtomicLong size, AtomicLong totalSize, Iterator<T> wrapped) {
        this(size::get, totalSize::get, null, null, wrapped);
    }

    public BasicWrappedIterator(Long totalSize, Iterator<T> wrapped) {
        this(totalSize, totalSize, wrapped);
    }

    public BasicWrappedIterator(AtomicLong totalSize, Iterator<T> wrapped) {
        this(totalSize, totalSize, wrapped);
    }

    public BasicWrappedIterator(Collection<T> wrapped) {
        super(wrapped.iterator());
        this.size = () -> (long) wrapped.size();
        this.totalSize = () -> (long) wrapped.size();
    }

    @Override
    public OptionalLong getSize() {
        if (size != null) {
            Long l = size.get();
            return ofNullable(l);
        } else {
            return super.getSize();
        }
    }

    @Override
    public OptionalLong getTotalSize() {
        if (totalSize != null) {
            Long l = totalSize.get();
            return  ofNullable(l);
        } else {
            return super.getTotalSize();
        }
    }

    @Override
    public T next() {
        T n = wrapped.next();
        count++;
        return n;
    }

    @Override
    public Long getCount() {
        return count;
    }

}
