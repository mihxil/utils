package org.meeuw.functional;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ThrowingTriConsumerTest {
    AtomicLong counter = new AtomicLong(0);

    @BeforeEach
    public void before() {
        counter.set(0);
    }

    ThrowingTriConsumer<String, Integer, String, Exception> consumer = (s1, s2, s3) -> {
        if (s1.equals("throw")) {
            throw new Exception("Test exception");
        }
        counter.getAndAdd(s2);
        System.out.println(s1 + s2 + s3);
    };

    @Test
    void accept() {
        consumer.accept("Hello", 1, "!");
        assertThat(counter.get()).isEqualTo(1);
        assertThatThrownBy(() -> {
            consumer.accept("throw", 2, "!");
        }).isInstanceOf(Exception.class)
            .hasMessage("Test exception");
        assertThat(counter.get()).isEqualTo(1);
    }

    @Test
    void andThen() {
        ThrowingTriConsumer<String, Integer, String, Exception> andThen = consumer.andThen((s1, s2, s3) ->  {
            if (s1.equals("throw2")) {
                throw new Exception("Test exception in andThen");
            }
            counter.getAndAdd(s2 * 10);
        });
        andThen.accept("Hello", 1, "!");
        assertThat(counter.get()).isEqualTo(11);
        assertThatThrownBy(() -> {
            andThen.accept("throw2", 2, "!");
        }).isInstanceOf(Exception.class)
            .hasMessage("Test exception in andThen");
        assertThat(counter.get()).isEqualTo(13);
    }


    @Test
    void withArg3() {
        consumer.withArg3("!").accept("Hello", 1);
        assertThat(counter.get()).isEqualTo(1);
    }

    @Test
    void withArg2() {
        consumer.withArg2(5).accept("Hello", "foo");
        assertThat(counter.get()).isEqualTo(5);
    }

    @Test
    void withArg1() {
        consumer.withArg1("hello").accept(3, "foo");
        assertThat(counter.get()).isEqualTo(3);
    }
}
