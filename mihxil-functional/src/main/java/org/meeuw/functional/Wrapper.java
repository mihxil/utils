package org.meeuw.functional;

/**
 * @author Michiel Meeuwissen
 */
abstract class Wrapper<W> {
    final W wrapped;
    private final String why;

    public Wrapper(W wrapped, String why) {
        this.wrapped = wrapped;
        this.why = why;
    }

    @Override
    public String toString() {
        return wrapped.toString() + "(" + why + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wrapper<?> wrapper = (Wrapper<?>) o;

        return wrapped.equals(wrapper.wrapped);
    }

    @Override
    public int hashCode() {
        int result = wrapped.hashCode();
        result = 31 * result;
        return result;
    }
}
