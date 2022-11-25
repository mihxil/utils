package org.meeuw.functional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.meeuw.functional.Consumers.*;

/**
 * @author Michiel Meeuwissen
 * @since 2.16
 */
class ConsumersTest {

    static class Abstract {
        final List<String> consumed = new ArrayList<>();

        @Override
        public String toString() {
            return getClass().getSimpleName();
        }
    }

    static class Mono extends Abstract implements Consumer<String> {
        @Override
        public void accept(String s) {
            consumed.add(s);
        }
    }

    static class Bi extends Abstract  implements  BiConsumer<String, Integer> {
        @Override
        public void accept(String s, Integer integer) {
            consumed.add(s + ":" + integer);
        }
    }

    static class BiInteger extends Abstract implements BiConsumer<Integer, Integer> {
        @Override
        public void accept(Integer s, Integer integer) {
            consumed.add(s + ":" + integer);
        }
    }

    @Test
    void biIgnoreArg3() {
        Bi bi = new Bi();
        Consumers.ignoreArg3(bi).accept("string", 1, 2.0f);
        assertThat(bi.consumed).containsExactly("string:1");
    }

    @Test
    void biIgnoreArg2() {
        Bi bi = new Bi();
        ignoreArg2(bi).accept("string", new Object(), 2);
        assertThat(bi.consumed).containsExactly("string:2");
    }

    @Test
    void biIgnoreArg1() {
        Bi bi = new Bi();
        ignoreArg1(bi).accept("ignored", "string", 3);
        assertThat(bi.consumed).containsExactly("string:3");

        assertThat(bi).isEqualTo(bi);
        assertThat(ignoreArg1(bi).equals(ignoreArg1(bi))).isTrue();
        assertThat(ignoreArg1(bi).equals(ignoreArg2(bi))).isFalse();
    }

    @Test
    void monoIgnoreArg2() {
        Mono mono = new Mono();
        ignoreArg2(mono).accept("string", 1);
        assertThat(mono.consumed).containsExactly("string");
    }

    @Test
    void monoIgnoreArg1() {
        Mono mono = new Mono();
        ignoreArg1(mono).accept("string", "foobar");
        assertThat(mono.consumed).containsExactly("foobar");
    }


    @Test
    void biWithArg2() {
        Bi bi = new Bi();
        withArg2(bi, 1).accept("a");
        withArg2(bi, 2).accept("b");
        assertThat(bi.consumed).containsExactly("a:1", "b:2");

        assertThat(withArg2(bi, 1).toString()).isEqualTo("Bi(with arg2 1)");
        assertThat(withArg2(bi, 1).equals(withArg2(bi, 1))).isTrue();
        assertThat(withArg2(bi, 1).equals(withArg2(bi, 2))).isFalse();
    }

    @Test
    void biiEquals12() {
        BiInteger bii = new BiInteger();
        assertThat(withArg2(bii, 1).equals(withArg1(bii, 1))).isFalse();
        assertThat(withArg1(bii, 1).equals(withArg1(bii, 1))).isTrue();
    }

    @Test
    void biWithArg1() {
        Bi bi = new Bi();
        Consumers.withArg1(bi, "pref").accept(1);
        Consumers.withArg1(bi, "pref").accept(2);
        assertThat(bi.consumed).containsExactly("pref:1", "pref:2");
    }

    @Test
    void biWithArg2Supplier() {
        Bi bi = new Bi();
        Consumers.withArg2Supplier(bi, () -> 1).accept("a");
        Consumers.withArg2Supplier(bi, () -> 2).accept("b");
        assertThat(bi.consumed).containsExactly("a:1", "b:2");
    }

    @Test
    void biWithArg1Supplier() {
        Bi bi = new Bi();
        Consumers.withArg1Supplier(bi, () -> "pref").accept(1);
        Consumers.withArg1Supplier(bi, () -> "pref").accept(2);
        assertThat(bi.consumed).containsExactly("pref:1", "pref:2");
    }

    @Test
    void nop() {
        Consumer<String> nop1 = Consumers.nop(String.class);
        Consumer<String> nop2 = Consumers.nop(String.class);
        Consumer<Integer> nop3 = Consumers.nop(Integer.class);
        assertThat(nop1).isEqualTo(nop2);
        assertThat(nop1.hashCode()).isEqualTo(nop2.hashCode());
        assertThat(nop1).isNotEqualTo(nop3);
        assertThat(nop1).isNotEqualTo("foobar");
        assertThat(nop1).isNotEqualTo(Consumers.nop());

        assertThat(nop1.toString()).isEqualTo("NOP");

        nop1.accept("fla");

        Consumers.nop().accept("bla");
    }

    @Test
    void biNop() {
        BiConsumer<String, Integer> nop1 = Consumers.biNop(String.class, Integer.class);
        BiConsumer<String, Integer> nop2 = Consumers.biNop(String.class, Integer.class);
        BiConsumer<Integer, String> nop3 = Consumers.biNop(Integer.class, String.class);
        assertThat(nop1).isEqualTo(nop2);
        assertThat(nop1.hashCode()).isEqualTo(nop2.hashCode());
        assertThat(nop1).isNotEqualTo(nop3);
        assertThat(nop1).isNotEqualTo("foobar");
        assertThat(nop1).isNotEqualTo(Consumers.nop());

        assertThat(nop1.toString()).isEqualTo("NOP");

        nop1.accept("fla", 1);

        Consumers.biNop().accept("bla", 1);
    }


    @Test
    void triNop() {
        TriConsumer<String, Integer, Float> nop1 = Consumers.triNop(String.class, Integer.class, Float.class);
        TriConsumer<String, Integer, Float> nop2 = Consumers.triNop(String.class, Integer.class, Float.class);
        TriConsumer<Integer, String, Float> nop3 = Consumers.triNop(Integer.class, String.class, Float.class);
        assertThat(nop1).isEqualTo(nop2);
        assertThat(nop1.hashCode()).isEqualTo(nop2.hashCode());
        assertThat(nop1).isNotEqualTo(nop3);
        assertThat(nop1).isNotEqualTo("foobar");
        assertThat(nop1).isNotEqualTo(Consumers.nop());

        assertThat(nop1.toString()).isEqualTo("NOP");

        nop1.accept("fla", 1, 0f);

        Consumers.triNop().accept("bla", 1, 0f);
    }

}
