package org.meeuw.functional;

/**
 * As {@link ThrowingAutoCloseable}, just no need to specify the type of the exception.
 *
 * @since 1.8
 */
@FunctionalInterface
public interface ThrowAnyAutoCloseable extends ThrowingAutoCloseable<Exception> {
}
