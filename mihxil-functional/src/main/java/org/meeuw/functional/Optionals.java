package org.meeuw.functional;

import java.util.Optional;
import java.util.OptionalLong;

import org.jspecify.annotations.Nullable;


public class Optionals {

    private Optionals() {}

    public static OptionalLong ofNullable(@Nullable Long l) {
        return l == null ? OptionalLong.empty() : OptionalLong.of(l);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Optional<Long> boxed(OptionalLong l) {
        return l.isPresent() ? Optional.of(l.getAsLong()) : Optional.empty();
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static @Nullable Long orElse(OptionalLong l, @Nullable Long e) {
        return l.isPresent() ? l.getAsLong() : e;
    }

}
