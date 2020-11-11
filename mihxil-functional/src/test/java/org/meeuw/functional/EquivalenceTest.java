package org.meeuw.functional;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michiel Meeuwissen
 * @since 1.0
 */
class EquivalenceTest {


    @Test
    public void and() {
        String a1 = "a";
        String a2 = "a";
        Equivalence<String> equivalence = Objects::equals;
        assertThat(equivalence.test(a1, a2)).isTrue();
        Predicate<String> predicate = equivalence.predicate(a1);
        assertThat(predicate.test(a2)).isTrue();
        assertThat(predicate).isEqualTo(predicate);
        assertThat(predicate).isNotEqualTo(equivalence.predicate("b"));
        assertThat(predicate.toString()).isEqualTo("equivalent to a");

        assertThat(Stream.of(a1, a2).allMatch(predicate)).isTrue();
        assertThat(Stream.of(a1, a2, "b").allMatch(predicate)).isFalse();
        assertThat(Stream.of(a1, a2, "b").anyMatch(predicate)).isTrue();
        assertThat(Stream.of("A", "b", "c").anyMatch(predicate)).isFalse();
    }

}
