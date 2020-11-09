package org.meeuw.functional;

import java.util.Objects;

/**
 * Wraps another object with a reason, and an extra value. This extra value just influences {@link #equals(Object)} and {@link #hashCode()}.
 *
 * @author Michiel Meeuwissen
 */
abstract class ValueWrapper<W> extends Wrapper<W> {
    private final Object value;

    public ValueWrapper(W wrapped, Object value, String why) {
        super(wrapped, why);
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
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
