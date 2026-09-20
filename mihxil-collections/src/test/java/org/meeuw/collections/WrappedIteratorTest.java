package org.meeuw.collections;

import java.util.*;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michiel Meeuwissen
 * @since 2.0
 */
class WrappedIteratorTest {

    @Test
    void iteratesAndRemoves() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", null, "d"));
        WrappedIterator<String, String> wrapped = new WrappedIterator<String, String>(list.iterator()) {


            @Override
            public String next() {
                return "{" + wrapped.next() + "}";
            }
        };
        StringBuilder build = new StringBuilder();
        while (wrapped.hasNext()) {
            String s = wrapped.next();
            build.append(s);
            if ("{b}".equals(s)) {
                wrapped.remove();
            }
        }
        assertThat(build).hasToString("{a}{b}{c}{null}{d}");
        assertThat(list).containsExactly("a", "c", null, "d");
    }
}
