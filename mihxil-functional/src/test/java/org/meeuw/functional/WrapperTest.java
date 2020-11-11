package org.meeuw.functional;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Michiel Meeuwissen
 * @since 1.0
 */
class WrapperTest {

    public static class W extends Wrapper<String> {

        public W(String wrapped) {
            super(wrapped, "why");
        }

    }

    @Test
    public void  equalsHashCodeNull() {

        W w = new W(null);
        assertThat(w.equals(new W(null))).isTrue();

        assertThat(w.hashCode()).isEqualTo(new W(null).hashCode());
        assertThat(w.hashCode()).isNotEqualTo(new W("abc").hashCode());

        assertThat(w.equals(w)).isTrue();
        assertThat(w.equals(null)).isFalse();
        assertThat(w.equals("foobar")).isFalse();

        assertThat(w.toString()).isEqualTo("null(why)");
    }

    @Test
    public void  equalsHashCode() {

        W w = new W("foobar");
        assertThat(w.equals(new W("foobar"))).isTrue();
        assertThat(w.equals(new W(null))).isFalse();

        assertThat(w.hashCode()).isEqualTo(new W("foobar").hashCode());
        assertThat(w.hashCode()).isNotEqualTo(new W("xxyy").hashCode());

        assertThat(w.equals(w)).isTrue();
        assertThat(w.equals(null)).isFalse();
        assertThat(w.equals("foobar")).isFalse();

        assertThat(w.toString()).isEqualTo("foobar(why)");

    }

}
