package org.meeuw.functional;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ThrowingBiConsumerTest {
    AtomicLong counter = new AtomicLong(0);

    @BeforeEach
    public void before() {
        counter.set(0);
    }
    ThrowingBiConsumer<String, Integer, Exception> consumer = (s1, s2) -> {
        if (s1.equals("throw")) {
            throw new Exception("Test exception");
        }
        counter.getAndAdd(s2);
        System.out.println(s1 + s2);
    };

    @Test
    void accept() {
        consumer.accept("Hello", 1);
        assertThat(counter.get()).isEqualTo(1);
        assertThatThrownBy(() ->
            consumer.accept("throw", 2)
        ).isInstanceOf(Exception.class)
            .hasMessage("Test exception");
        assertThat(counter.get()).isEqualTo(1);
    }

    @Test
    void andThen() {
        ThrowingBiConsumer<String, Integer, Exception> andThen = consumer.andThen((s1, s2) ->  {
            if (s1.equals("throw2")) {
                throw new Exception("Test exception in andThen");
            }
            counter.getAndAdd(s2 * 10);
        });
        andThen.accept("Hello", 1);
        assertThat(counter.get()).isEqualTo(11);
        assertThatThrownBy(() -> andThen.accept("throw2", 2)).isInstanceOf(Exception.class)
            .hasMessage("Test exception in andThen");
        assertThat(counter.get()).isEqualTo(13);
    }

    @Test
    void withArg2() {
        consumer.withArg2(5).accept("Hello");
        assertThat(counter.get()).isEqualTo(5);
    }

    @Test
    void withArg1() {
        consumer.withArg1("hello").accept(3);
        assertThat(counter.get()).isEqualTo(3);
    }


    @Test
    void withArg2Supplier() {
        consumer.withArg2Supplier(() -> 5).accept("Hello");
        assertThat(counter.get()).isEqualTo(5);
    }

    @Test
    void withArg1Supplier() {
        consumer.withArg1Supplier(() -> "hello").accept(3);
        assertThat(counter.get()).isEqualTo(3);
    }

    @Test
    void ignoreArg3() {
        consumer.ignoreArg3().accept("Hello", 5, "foo");
        assertThat(counter.get()).isEqualTo(5);
    }

    @Test
    void ignoreArg2() {
        consumer.ignoreArg2().accept("Hello", "foo", 5);
        assertThat(counter.get()).isEqualTo(5);
    }

    @Test
    void ignoreArg1() {
        consumer.ignoreArg1().accept("Hello", "foo", 7);
        assertThat(counter.get()).isEqualTo(7);
    }
}
