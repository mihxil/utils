package org.meeuw.functional;

/**
 * As {@link ThrowingRunnable}, just no need to specify the type of the exception.
 *
 * @since 1.8
 */
@FunctionalInterface
public interface ThrowAnyRunnable extends ThrowingRunnable<Exception> {
}
