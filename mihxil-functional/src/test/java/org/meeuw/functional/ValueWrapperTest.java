package org.meeuw.functional;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michiel Meeuwissen
 * @since 0.3
 */
@SuppressWarnings({"ConstantConditions", "EqualsWithItself", "EqualsBetweenInconvertibleTypes"})
class ValueWrapperTest {

    public static class W extends ValueWrapper<String> {

        public W(String wrapped) {
            super(wrapped, "value", "why");
        }
        public W(String wrapped, String value) {
            super(wrapped, value, "why");
        }
    }

    @Test
    void equalsHashCodeNull() {

        W w = new W(null);
        assertThat(w.equals(new W(null))).isTrue();
        assertThat(w.equals(new W(null, "abc"))).isFalse();
        assertThat(w.equals(new W(null, null))).isFalse();

        assertThat(w.hashCode()).isEqualTo(new W(null).hashCode());
        assertThat(w.hashCode()).isNotEqualTo(new W(null, "abc").hashCode());

        assertThat(w.equals(w)).isTrue();
        assertThat(w.equals(null)).isFalse();
        assertThat(w.equals("foobar")).isFalse();

        assertThat(w.toString()).isEqualTo("null(why)");
    }

    @Test
    void equalsHashCode() {

        W w = new W("foobar");
        assertThat(w.equals(new W("foobar"))).isTrue();
        assertThat(w.equals(new W(null))).isFalse();
        assertThat(w.equals(new W("foobar", "xyz"))).isFalse();
        assertThat(w.equals(new W("foobar", null))).isFalse();

        assertThat(w.hashCode()).isEqualTo(new W("foobar").hashCode());
        assertThat(w.hashCode()).isNotEqualTo(new W("foobar", "xyz").hashCode());
        assertThat(w.hashCode()).isNotEqualTo(new W("foobar", null).hashCode());

        assertThat(w.equals(w)).isTrue();
        assertThat(w.equals(null)).isFalse();
        assertThat(w.equals("foobar")).isFalse();

        assertThat(w.toString()).isEqualTo("foobar(why)");

    }

}
