package org.meeuw.functional;

class Sneaky {

    private Sneaky() {
        // no instances
    }
    /**
     * Just throws its argument. This is actually just a trick to fool the compiler.
     * This way we can throw checked exceptions from a method without a throw clause, without having to wrap them in
     * {@link RuntimeException}. Similar to <a href="https://projectlombok.org/features/SneakyThrows">lombok's {@code @SneakyThrows}</a> does, but without the need for the dependency.
     * @return Never returns anything, always throws the exception
     */
    @SuppressWarnings("unchecked")
    public static <T extends Throwable, R> R  sneakyThrow(Throwable e) throws T {
        throw (T) sneaky(e);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Throwable> T sneaky(Throwable e) {
        return (T) e;
    }
}
