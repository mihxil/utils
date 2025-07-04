package org.meeuw.functional;


/**
 * As {@link ThrowingSupplier}, just no need to specify the type of the exception.
 *
 * @since 1.5
 * @param <T> the type of results supplied by this supplier
 */
@FunctionalInterface
public interface ThrowAnySupplier<T> extends ThrowingSupplier<T, Exception> {

}
