package org.meeuw.functional;

import java.util.Objects;

/**
 * Wraps some object with a reason.
 * @author Michiel Meeuwissen
 */
abstract class Wrapper<W> {
    final W wrapped;
    private final String reason;


    /**
     * @param wrapped An object that this wrapper is wrapping, and can be used to implement it
     * @param reason A description for why the wrapping happened.
     */
    public Wrapper(W wrapped, String reason) {
        this.wrapped = wrapped;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return wrapped + "(" + reason + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wrapper<?> wrapper = (Wrapper<?>) o;

        return Objects.equals(wrapped, wrapper.wrapped);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(wrapped);
        result = 31 * result;
        return result;
    }
}
