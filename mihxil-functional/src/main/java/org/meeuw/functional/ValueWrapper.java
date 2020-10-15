package org.meeuw.functional;

import java.util.Objects;

/**
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValueWrapper<?> wrapper = (ValueWrapper<?>) o;

        if (!wrapped.equals(wrapper.wrapped)) return false;
        return Objects.equals(value, wrapper.value);
    }

    @Override
    public int hashCode() {
        int result = wrapped.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
