package org.meeuw.collections;

import lombok.Getter;
import lombok.Singular;
import lombok.extern.java.Log;

import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Level;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.meeuw.functional.Predicates;


/**
 * An iterator implementing offset and max, for another iterator.
 *
 * @author Michiel Meeuwissen
 * @since 1.18 (since 3.1 in vpro-shared-util)
 */
@SuppressWarnings("UnusedReturnValue")
@Log
public class MaxOffsetIterator<T> implements CloseablePeekingIterator<T>, Counted {

    protected final CloseableIterator<T> wrapped;

    protected PeekingIterator<T> peekingWrapped;

    /**
     * The maximal value of count. I.e. offset + max;
     */
    protected final long offsetmax;

    final long max;


    @Getter
    private final long offset;

    private final Predicate<T> countPredicate;

    /**
     * The count of the next element. First value will be the supplied value of offset.
     */

    protected long count = 0;

    private long returnedCount = 0;

    protected Boolean hasNext = null;

    protected T next;

    private RuntimeException exception;

    private Runnable callback;

    private final List<AutoCloseable> autoCloseables = new ArrayList<>();
    private boolean callbackCalled;
    private boolean autoCloseablesClosed;
    private boolean wrappedClosed;
    private boolean closeWrappedOnExhaustion;
    private boolean closed;

    public MaxOffsetIterator(Iterator<T> wrapped, Number max, boolean countNulls) {
        this(wrapped, max, 0L, countNulls);
    }

    public MaxOffsetIterator(Iterator<T> wrapped, Number max) {
        this(wrapped, max, 0L, true);
    }

    public MaxOffsetIterator(Iterator<T> wrapped, Number max, Number offset) {
        this(wrapped, max, offset, true);
    }

    public MaxOffsetIterator(Iterator<T> wrapped, Number max, Number offset, boolean countNulls) {
        this(wrapped, max, offset, null, countNulls, null, false);
    }

    @lombok.Builder(builderClassName = "Builder")
    protected MaxOffsetIterator(
        @NonNull Iterator<T> wrapped,
        @Nullable Number max,
        @Nullable Number offset,
        @Nullable Predicate<T> countPredicate,
        boolean countNulls,
        @Nullable @Singular  List<Runnable> callbacks,
        boolean autoClose) {
        //noinspection ConstantConditions
        if (wrapped == null) {
            throw new IllegalArgumentException("Cannot wrap null");
        }
        this.wrapped = CloseableIterator.of(wrapped);
        this.offset = offset == null ? 0L : offset.longValue();
        this.max = max == null ? Long.MAX_VALUE : max.longValue();
        this.offsetmax = max == null ? Long.MAX_VALUE : max.longValue() + this.offset;
        this.countPredicate = effectiveCountPredicate(countPredicate, countNulls);
        this.callback = () -> {
            if (callbacks != null) {
                for (Runnable r : callbacks) {
                    try {
                        r.run();
                    } catch (Exception e) {
                        log.log(Level.WARNING, e.getMessage(), e);
                    }
                }
            }
        };
        if (autoClose) {
            autoClose();
        }

    }

    @Override
    public Long getCount() {
        return returnedCount;
    }

    protected static <S>  Predicate<S> effectiveCountPredicate(Predicate<S> countPredicate, boolean countNulls) {
        Predicate<S> effective = countPredicate == null ? Predicates.alwaysTrue() : countPredicate;
        if (! countNulls) {
            effective = ((Predicate<S>) Objects::nonNull).and(effective);
        }
        return effective;
    }

    public MaxOffsetIterator<T> callBack(Runnable run) {
        callback = run;
        return this;
    }

    public MaxOffsetIterator<T> autoClose(AutoCloseable... closeables) {
        Collections.addAll(autoCloseables, closeables);
        return this;
    }

    public MaxOffsetIterator<T> autoClose() {
        closeWrappedOnExhaustion = true;
        return this;
    }

    @Override
    public boolean hasNext() {
        return findNext();
    }

    @Override
    public T peek() {
        if (!findNext()) {
            throw new NoSuchElementException();
        }
        if (exception != null) {
            throw exception;
        }
        returnedCount++;
        return next;
    }

    @Override
    public T next() {
        if (!findNext()) {
            throw new NoSuchElementException();
        }
        hasNext = null;
        if (exception != null) {
            throw exception;
        }
        return next;
    }

    protected boolean findNext() {
        if (hasNext == null) {
            hasNext = false;

            while(count < offset && wrapped.hasNext()) {
                T n;
                try {
                    n = wrapped.next();
                } catch(RuntimeException runtimeException) {
                    n = null;
                }
                if (countPredicate.test(n)) {
                    count++;
                }
            }

            if (count < offsetmax && wrapped.hasNext()) {
                try {
                    exception = null;
                    next = wrapped.next();
                } catch (RuntimeException e) {
                    exception = e;
                    next = null;

                }
                if (countPredicate.test(next)) {
                    count++;
                }
                hasNext = true;
            }

            if (!hasNext) {
                finishAfterExhaustion();
            }
        }
        return hasNext;
    }


    @Override
    public void remove() {
        wrapped.remove();
    }

    @Override
    public void close() throws Exception {
        if (!closed) {
            closed = true;
            Exception failure = null;
            try {
                runCallback();
            } catch (RuntimeException e) {
                failure = e;
            }
            Exception autoCloseFailure = closeAutoCloseables();
            if (autoCloseFailure != null) {
                if (failure == null) {
                    failure = autoCloseFailure;
                } else {
                    failure.addSuppressed(autoCloseFailure);
                }
            }
            try {
                closeWrapped();
            } catch (Exception e) {
                if (failure == null) {
                    failure = e;
                } else {
                    failure.addSuppressed(e);
                }
            }
            if (failure != null) {
                throw failure;
            }
        }
    }

    private void finishAfterExhaustion() {
        try {
            runCallback();
        } catch (RuntimeException e) {
            log.log(Level.WARNING, e.getMessage(), e);
        }
        Exception failure = closeAutoCloseables();
        if (closeWrappedOnExhaustion) {
            try {
                closeWrapped();
            } catch (Exception e) {
                log.log(Level.WARNING, e.getMessage(), e);
            }
        }
        if (failure != null) {
            log.log(Level.WARNING, failure.getMessage(), failure);
        }
    }

    private void runCallback() {
        if (!callbackCalled) {
            callbackCalled = true;
            if (callback != null) {
                callback.run();
            }
        }
    }

    private Exception closeAutoCloseables() {
        if (autoCloseablesClosed) {
            return null;
        }
        autoCloseablesClosed = true;
        Exception failure = null;
        for (AutoCloseable closeable : autoCloseables) {
            try {
                closeable.close();
            } catch (Exception e) {
                if (failure == null) {
                    failure = e;
                } else {
                    failure.addSuppressed(e);
                }
            }
        }
        return failure;
    }

    private void closeWrapped() throws Exception {
        if (!wrappedClosed) {
            wrappedClosed = true;
            wrapped.close();
        }
    }

    @Override
    public String toString() {
        return wrapped + "[" + offset + "," + (max < Long.MAX_VALUE ? max : "") + "]";
    }

    /**
     * Access to the (peeking) wrapped iterator.
     * This may be used to look 'beyond' max, to check what would have been the next one.
     */
    public PeekingIterator<T> peekingWrapped() {
        if (peekingWrapped == null) {
            peekingWrapped = wrapped.peeking();
        }
        return peekingWrapped;
    }

    public static <T> CountedMaxOffsetIterator.Builder<T> countedBuilder() {
        return CountedMaxOffsetIterator.<T>_countedBuilder();
    }
}
