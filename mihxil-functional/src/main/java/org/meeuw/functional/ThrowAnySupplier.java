package org.meeuw.functional;


/**
 * As {@link ThrowingSupplier}, just no need to specify the type of the exception.
 *
 * @since 1.5
 */
public interface ThrowAnySupplier<T> extends ThrowingSupplier<T, Exception> {

}
