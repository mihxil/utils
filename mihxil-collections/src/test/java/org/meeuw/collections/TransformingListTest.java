package org.meeuw.collections;

import java.util.*;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michiel Meeuwissen
 * @since 1.63
 */
class TransformingListTest {


    @Test
    void adds() {
        List<Integer> set = new ArrayList<>();
        set.add(1);
        set.add(2);

        TransformingList<StringBuilder, Integer> transforming =
            new TransformingList<>(set, i -> new StringBuilder(String.valueOf(i)), stringBuilder -> Integer.parseInt(stringBuilder.toString()));
        transforming.add(new StringBuilder("3"));

        assertThat(set).containsExactly(1, 2, 3);

    }


    @Test
    void removes() {
        List<Integer> set = new ArrayList<>();
        set.add(1);
        set.add(2);

        TransformingList<StringBuilder, Integer> transforming =
            new TransformingList<>(set, i -> new StringBuilder(String.valueOf(i)), stringBuilder -> Integer.parseInt(stringBuilder.toString()));
        transforming.remove(new StringBuilder("2"));

        assertThat(set).containsExactly(1);

    }

    @Test
    void changesFirst() {
        List<Integer> set = new ArrayList<>();
        set.add(1);
        set.add(2);

        TransformingList<StringBuilder, Integer> transforming =
            new TransformingList<>(set, i -> new StringBuilder(String.valueOf(i)), stringBuilder -> Integer.parseInt(stringBuilder.toString()));
        transforming.get(0).append("0");
        assertThat(transforming.get(0).toString()).isEqualTo("10");
        set = transforming.produce();

        assertThat(set).containsExactly(10, 2);

    }

    @Test
    void setReplacesCachedValue() {
        List<Integer> set = new ArrayList<>(Arrays.asList(1, 2));
        TransformingList<String, Integer> transforming =
            new TransformingList<>(set, String::valueOf, Integer::parseInt);

        transforming.get(0);
        transforming.get(1);

        assertThat(transforming.set(0, "3")).isEqualTo("1");
        assertThat(transforming.get(0)).isEqualTo("3");
        assertThat(transforming.get(1)).isEqualTo("2");
        assertThat(set).containsExactly(3, 2);
    }

}
