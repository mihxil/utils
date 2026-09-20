package org.meeuw.collections;

import lombok.extern.java.Log;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Stream;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.meeuw.functional.Functions;


/**
 * Adapts an existing iterator, to add elements at the end, perhaps based on the last element.
 *
 * @author Michiel Meeuwissen
 * @since 1.17
 */
@Log
public class TailAdder<T> implements CountedIterator<T> {

    private final boolean onlyIfEmpty;

    private final boolean onlyIfNotEmpty;

    private final Function<T, T>[] adder;

    private final CloseableIterator<T> wrapped;

    int wrapcount = 0;
    int addercount = 0;
    int tailcount = 0;
    T nextFromAdder;
    Boolean adderHasNext = null;
    T last = null;
    private boolean closed;

    @SafeVarargs
    public static <T> TailAdder<T> withFunctions(Iterator<T> wrapped, Function<T, T>... adder) {
        return new TailAdder<>(wrapped, false, false, adder);
    }

    @SafeVarargs
    protected TailAdder(Iterator<T> wrapped, boolean onlyIfEmpty, boolean onlyIfNotEmpty, Function<T, T>... adder) {
        this.wrapped = CloseableIterator.of(wrapped);
        this.onlyIfEmpty = onlyIfEmpty;
        this.onlyIfNotEmpty = onlyIfNotEmpty;
        if (onlyIfEmpty && onlyIfNotEmpty) {
            throw new IllegalArgumentException("Cant specify both onlyIfEmpty and onlyIfNotEmpty");
        }
        this.adder = adder;
    }

    @SuppressWarnings("unchecked")
    @lombok.Builder(builderClassName = "Builder")
    private TailAdder(Iterator<T> wrapped,
                      boolean onlyIfEmpty,
                      boolean onlyIfNotEmpty,
                      @lombok.Singular  List<Function<T, T>> adders,
                      @lombok.Singular List<Callable<T>> callableAdders) {
        this(wrapped, onlyIfEmpty, onlyIfNotEmpty,
            Stream.<Function<T, T>>concat(
                adders.stream(),
                callableAdders.stream().map(Functions::ignoreArg1)
            ).toArray(Function[]::new)
        );
    }


    @Override
    public boolean hasNext() {
        if (wrapped.hasNext()) {
            return true;
        }
        findNext();
        if (! adderHasNext) {
            closeAfterExhaustion();
        }
        return adderHasNext;
    }

    @Override
    public T next() {
        if (wrapped.hasNext()) {
            wrapcount++;
            T result = wrapped.next();
            last = result;
            return result;
        }
        findNext();
        if (! adderHasNext) {
            closeAfterExhaustion();
            throw new NoSuchElementException();
        }
        adderHasNext = null;
        tailcount++;
        return nextFromAdder;
    }

    protected T getLast() {
        return last;
    }

    private void findNext() {
        if (adderHasNext == null) {
            adderHasNext = false;
            boolean addTail;
            if (onlyIfNotEmpty) {
                addTail = wrapcount > 0;
            } else if (onlyIfEmpty) {
                addTail = wrapcount == 0;
            } else {
                addTail = true;
            }

            if (addTail) {
                while (addercount < adder.length) {
                    try {
                        nextFromAdder = adder[addercount++].apply(getLast());
                        adderHasNext = true;
                        break;
                    } catch (NoSuchElementException nse) {
                        // ignore
                    } catch (Exception e) {
                        log.log(Level.WARNING, e.getClass().getName() + ": " + e.getMessage());

                    }

                }
            }
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull Optional<Long> getSize() {
        if (wrapped instanceof CountedIterator) {
            Optional<Long> wrappedSize = ((CountedIterator) wrapped).getSize();
            if (wrappedSize.isPresent()) {
                long l = wrappedSize.get();
                if ((!onlyIfEmpty || l == 0L) && (!onlyIfNotEmpty || l > 0L)) {
                    l += adder.length;
                }
                return Optional.of(l);
            }

        }
        return Optional.empty();
    }

    @Override
    public Long getCount() {
        return (long) wrapcount + tailcount;
    }


    @Override
    public void close() throws Exception {
        if (! closed) {
            closed = true;
            wrapped.close();
        }
    }

    private void closeAfterExhaustion() {
        try {
            close();
        } catch (Exception e) {
            throw new IllegalStateException("Could not close tail iterator", e);
        }
    }

    @Override
    public String toString() {
        return wrapped + " + TAIL";
    }
}
