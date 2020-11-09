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
    }

    @Test
    public void  equalsHashCodeNull() {

        W w = new W(null);
        assertThat(w).isEqualTo(new W(null));
        assertThat(w).isEqualTo(w);
        assertThat(w).isNotEqualTo(null);
        assertThat(w).isNotEqualTo("foobar");
    }
    @Test
    public void  equalsHashCode() {

        W w = new W("foobar");
        assertThat(w).isEqualTo(new W("foobar"));
        assertThat(w).isEqualTo(w);
        assertThat(w).isNotEqualTo(null);
        assertThat(w).isNotEqualTo("foobar");
    }

}
