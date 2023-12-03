package org.meeuw.functional;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.assertj.core.api.Assertions.*;

@SuppressWarnings({"ResultOfMethodCallIgnored", "EqualsWithItself"})
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
        }, () -> result.add("orElse"));
        assertThat(result).containsExactly("orElse");

        assertThatThrownBy(empty::orElseThrow).isInstanceOf(NoSuchElementException.class);
         assertThatThrownBy(() -> empty.orElseThrow(IllegalArgumentException::new)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void optionalTrue() {
        OptionalBoolean t = OptionalBoolean.optionalTrue();
        assertThat(t.isEmpty()).isFalse();
        assertThat(t.isPresent()).isTrue();
        assertThat(t.getAsBoolean()).isTrue();
        assertThat(t.orElseGet(() -> true)).isTrue();
        assertThat(t.orElseGet(() -> false)).isTrue();
        assertThat(t.orElse(true)).isTrue();
        assertThat(t.orElse(false)).isTrue();
        List<String> result = new ArrayList<>();
        t.ifPresent(b -> {
            result.add("if present " + b);
        });

        t.ifPresentOrElse(b -> {
            result.add("if present or else " + b);
        }, () -> {
            throw new AssertionFailedError("Should not be called", null);
        });
        assertThat(result).containsExactly("if present true", "if present or else true");

        assertThat(t.orElseThrow()).isTrue();

        assertThat(t.orElseThrow(IllegalArgumentException::new)).isTrue();
    }


    @Test
    public void optionalFalse() {
        OptionalBoolean f = OptionalBoolean.optionalFalse();
        assertThat(f.isEmpty()).isFalse();
        assertThat(f.isPresent()).isTrue();
        assertThat(f.getAsBoolean()).isFalse();
        assertThat(f.orElseGet(() -> true)).isFalse();
        assertThat(f.orElseGet(() -> false)).isFalse();
        assertThat(f.orElse(true)).isFalse();
        assertThat(f.orElse(false)).isFalse();
        List<String> result = new ArrayList<>();
        f.ifPresent(b -> {
            result.add("if present " + b);
        });

        f.ifPresentOrElse(b -> {
            result.add("if present or else " + b);
        }, new Runnable() {
            @Override
            public void run() {
                throw new AssertionFailedError("Should not be called", null);
            }
        });
        assertThat(result).containsExactly("if present false", "if present or else false");

        assertThat(f.orElseThrow()).isFalse();

        assertThat(f.orElseThrow(IllegalArgumentException::new)).isFalse();
    }

    @Test
    public void equals() {
        assertThat(OptionalBoolean.empty()).isEqualTo(OptionalBoolean.empty());
        assertThat(OptionalBoolean.empty()).isNotEqualTo(OptionalBoolean.optionalTrue());
        assertThat(OptionalBoolean.empty()).isNotEqualTo(OptionalBoolean.optionalFalse());
        assertThat(OptionalBoolean.optionalTrue()).isEqualTo(OptionalBoolean.optionalTrue());
        assertThat(OptionalBoolean.optionalTrue()).isNotEqualTo(OptionalBoolean.optionalFalse());
        assertThat(OptionalBoolean.optionalFalse()).isEqualTo(OptionalBoolean.optionalFalse());
        assertThat(OptionalBoolean.optionalFalse()).isNotEqualTo(OptionalBoolean.optionalTrue());

        assertThat(OptionalBoolean.empty()).isNotEqualTo(null);
        assertThat(OptionalBoolean.empty()).isNotEqualTo("");
    }

    @Test
    public void hashcode() {
        assertThat(OptionalBoolean.empty().hashCode()).isEqualTo(OptionalBoolean.empty().hashCode());
        assertThat(OptionalBoolean.empty().hashCode()).isNotEqualTo(OptionalBoolean.optionalTrue().hashCode());
        assertThat(OptionalBoolean.empty().hashCode()).isNotEqualTo(OptionalBoolean.optionalFalse().hashCode());
        assertThat(OptionalBoolean.optionalTrue().hashCode()).isEqualTo(OptionalBoolean.optionalTrue().hashCode());
        assertThat(OptionalBoolean.optionalTrue().hashCode()).isNotEqualTo(OptionalBoolean.optionalFalse().hashCode());
        assertThat(OptionalBoolean.optionalFalse().hashCode()).isEqualTo(OptionalBoolean.optionalFalse().hashCode());
        assertThat(OptionalBoolean.optionalFalse().hashCode()).isNotEqualTo(OptionalBoolean.optionalTrue().hashCode());
    }

    @Test
    public void toStringTest() {
        assertThat(OptionalBoolean.empty().toString()).isEqualTo("OptionalBoolean.empty");
        assertThat(OptionalBoolean.optionalTrue().toString()).isEqualTo("OptionalBoolean[true]");
        assertThat(OptionalBoolean.optionalFalse().toString()).isEqualTo("OptionalBoolean[false]");

    }

}
