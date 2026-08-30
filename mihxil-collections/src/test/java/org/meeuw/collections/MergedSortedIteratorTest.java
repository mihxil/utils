package org.meeuw.collections;

import java.util.*;

import org.junit.jupiter.api.Test;



import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michiel Meeuwissen
 * @since 0.32
 */
@SuppressWarnings("OptionalGetWithoutIsPresent")
public class MergedSortedIteratorTest {

    @Test
    public void test() {
        List<String> l1 = Arrays.asList("a", "d");
        List<String> l2 = Arrays.asList("b", "c", "e");

        CountedIterator<String> merged = MergedSortedIterator.merge(Comparator.naturalOrder(), CountedIterator.of(l1), CountedIterator.of(l2));

        assertThat(merged.stream().collect(java.util.stream.Collectors.toList())).isEqualTo(Arrays.asList("a", "b", "c", "d", "e"));
        assertThat(merged.getSize().get()).isEqualTo(5L);
        assertThat(merged.getTotalSize().get()).isEqualTo(5L);


    }

    @Test
    public void testInSameThread() {
        List<String> l1 = Arrays.asList("a", "d");
        List<String> l2 = Arrays.asList("b", "c", "e");

        CountedIterator<String> merged = MergedSortedIterator.mergeInSameThread(Comparator.naturalOrder(), CountedIterator.of(l1), CountedIterator.of(l2));

        assertThat(merged.stream().collect(java.util.stream.Collectors.toList())).isEqualTo(Arrays.asList("a", "b", "c", "d", "e"));
        assertThat(merged.getSize().get()).isEqualTo(5L);
        assertThat(merged.getTotalSize().get()).isEqualTo(5L);


    }

    @Test
    public void testInSameThread2() {
        List<String> l1 = Arrays.asList("a", "d");
        List<String> l2 = Arrays.asList("b", "c", "e");

        CountedIterator<String> merged = MergedSortedIterator.mergeInSameThread(Comparator.naturalOrder(), CountedIterator.of(l2), CountedIterator.of(l1));

        assertThat(merged.stream().collect(java.util.stream.Collectors.toList())).isEqualTo(Arrays.asList("a", "b", "c", "d", "e"));
        assertThat(merged.getSize().get()).isEqualTo(5L);
        assertThat(merged.getTotalSize().get()).isEqualTo(5L);

    }

}
