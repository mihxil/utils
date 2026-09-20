package org.meeuw.collections;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A {@link Spliterator} that owns a closeable resource.
 *
 * @param <T> the type of elements supplied by this spliterator
 * @since 1.18
 */
public interface CloseableSpliterator<T> extends Spliterator<T>, AutoCloseable {

    /**
     * Wraps a spliterator and closes the supplied resource when this spliterator is closed.
     *
     * @param spliterator the spliterator to wrap
     * @param closeable   the resource to close
     * @param <T>         the type of elements supplied by the spliterator
     * @return a close-aware spliterator
     */
    static <T> CloseableSpliterator<T> of(Spliterator<T> spliterator, AutoCloseable closeable) {
        return new Wrapper<>(spliterator, closeable);
    }

    /**
     * Returns a sequential stream that closes this spliterator when the stream is closed.
     *
     * @return a stream over this spliterator
     */
    default Stream<T> stream() {
        return StreamSupport.stream(this, false).onClose(() -> {
            try {
                close();
            } catch (Exception e) {
                throw new IllegalStateException("Could not close spliterator", e);
            }
        });
    }

    class Wrapper<T> implements CloseableSpliterator<T> {
        private final Spliterator<T> wrapped;
        private final AutoCloseable closeable;
        private boolean closed;

        private Wrapper(Spliterator<T> wrapped, AutoCloseable closeable) {
            this.wrapped = Objects.requireNonNull(wrapped);
            this.closeable = Objects.requireNonNull(closeable);
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            return wrapped.tryAdvance(action);
        }

        @Override
        public Spliterator<T> trySplit() {
            return wrapped.trySplit();
        }

        @Override
        public long estimateSize() {
            return wrapped.estimateSize();
        }

        @Override
        public int characteristics() {
            return wrapped.characteristics();
        }

        @Override
        public void close() throws Exception {
            if (!closed) {
                closed = true;
                closeable.close();
            }
        }
    }
}
