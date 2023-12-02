package org.meeuw.functional;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BooleanConsumerTest {

    @Test
    public void test() {
        final List<String> result = new ArrayList<>();
        BooleanConsumer c = new BooleanConsumer() {
            @Override
            public void accept(boolean value) {
                result.add(String.valueOf(value));
            }
        };
        c.andThen(b -> result.add("and then " + b)).accept(true);
        c.accept(false);

        assertThat(result).containsExactly("true", "and then true", "false");
    }

}
