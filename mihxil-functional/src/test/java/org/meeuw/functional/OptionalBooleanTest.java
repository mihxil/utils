package org.meeuw.functional;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OptionalBooleanTest {

    @Test
    public void empty() {
        OptionalBoolean empty = OptionalBoolean.empty();
        assertThat(empty.isEmpty()).isTrue();
        assertThat(empty.isPresent()).isFalse();
        assertThatThrownBy(empty::getAsBoolean).isInstanceOf(NoSuchElementException.class);
        assertThat(empty.orElseGet(() -> true)).isTrue();
        assertThat(empty.orElseGet(() -> false)).isFalse();
        assertThat(empty.orElse(true)).isTrue();
        assertThat(empty.orElse(false)).isFalse();
        empty.ifPresent(b -> {
            throw new AssertionFailedError("Should not be called", null);
        });
        List<String> result = new ArrayList<>();
        empty.ifPresentOrElse(b -> {
            throw new AssertionFailedError("Should not be called", null);
        }, new Runnable() {
            @Override
            public void run() {
                result.add("orElse");
            }
        });
        assertThat(result).containsExactly("orElse");

        assertThatThrownBy(empty::orElseThrow).isInstanceOf(NoSuchElementException.class);

    }

}
