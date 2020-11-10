package org.meeuw.functional;

import java.util.Objects;

/**
 * Wraps another object with a reason, and an extra value. This extra value just influences {@link #equals(Object)} and {@link #hashCode()}.
 *
 * @author Michiel Meeuwissen
 */
abstract class ValueWrapper<W> extends Wrapper<W> {
    private final Object value;

    /**
     * @param wrapped An object that this wrapper is wrapping, and can be used to implement it
     * @param value An extra value, which only function is to influence   {@link #equals(Object)} and {@link #hashCode()}
     * @param reason A description for why the wrapping happened
     */
    public ValueWrapper(W wrapped, Object value, String reason) {
        super(wrapped, reason);
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (! super.equals(o)) {
            return false;
        }
        ValueWrapper<?> wrapper = (ValueWrapper<?>) o;
        return Objects.equals(value, wrapper.value);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
