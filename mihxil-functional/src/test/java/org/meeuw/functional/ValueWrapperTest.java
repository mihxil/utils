package org.meeuw.functional;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michiel Meeuwissen
 * @since 0.3
 */
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
    public void  equalsHashCodeNull() {

        W w = new W(null);
        assertThat(w).isEqualTo(new W(null));
        assertThat(w).isNotEqualTo(new W(null, "abc"));
        assertThat(w).isNotEqualTo(new W(null, null));

        assertThat(w.hashCode()).isEqualTo(new W(null).hashCode());
        assertThat(w.hashCode()).isNotEqualTo(new W(null, "abc").hashCode());

        assertThat(w).isEqualTo(w);
        assertThat(w).isNotEqualTo(null);
        assertThat(w).isNotEqualTo("foobar");

        assertThat(w.toString()).isEqualTo("null(why)");
    }
    @Test
    public void  equalsHashCode() {

        W w = new W("foobar");
        assertThat(w).isEqualTo(new W("foobar"));
        assertThat(w).isNotEqualTo(new W(null));
        assertThat(w).isNotEqualTo(new W("foobar", "xyz"));
        assertThat(w).isNotEqualTo(new W("foobar", null));

        assertThat(w.hashCode()).isEqualTo(new W("foobar").hashCode());
        assertThat(w.hashCode()).isNotEqualTo(new W("foobar", "xyz").hashCode());
        assertThat(w.hashCode()).isNotEqualTo(new W("foobar", null).hashCode());

        assertThat(w).isEqualTo(w);
        assertThat(w).isNotEqualTo(null);
        assertThat(w).isNotEqualTo("foobar");

        assertThat(w.toString()).isEqualTo("foobar(why)");

    }

}
