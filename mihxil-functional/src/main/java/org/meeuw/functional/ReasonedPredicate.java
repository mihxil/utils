package org.meeuw.functional;

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

/**
 * A 'Reasoned' predicate, is a predicate, but besides the normal {@link #test(Object)} method it
 * also supplies a {@link #testWithReason(Object)},which returns the {@link BooleanSupplier} {@link TestResult}, which besides
 * the test result boolean also can give a reason for <em>why</em> the predicate evaluated as it did.
 * <p>
 * This is useful e.g. when the predicate itself is combined from multiple other ones with all kind of (boolean) logic, and sometimes
 * a user may find it hard to understand <em>why</em> the predicate evaluates as it does.
 *
 * @param <T> the type of the input to the predicate
 */
public interface ReasonedPredicate<T> extends Predicate<T> {

    /**
     * Returns a reason and result for the predicate evaluation.
     * @param input The object to test with this predicate
     * @return A {@link TestResult}, describing whether and why the test succeeded
     */
    default TestResult testWithReason(T input) {
        boolean applies = test(input);
        if (applies) {
            return new TestResult() {
                @Override
                public String toString() {
                    return ReasonedPredicate.this + "(" + input + ")";
                }

                @Override
                public boolean getAsBoolean() {
                    return true;
                }
            };
        } else {
            return new TestResult() {
                @Override
                public String toString() {
                    return "!" + ReasonedPredicate.this + "(" + input + ")";
                }

                @Override
                public boolean getAsBoolean() {
                    return false;
                }
            };
        }
    }

    @Override
    default boolean test(T input) {
        return testWithReason(input).getAsBoolean();
    }

    /**
     * Basically wraps a boolean with a reason
     */
    interface TestResult extends BooleanSupplier {

        /**
         * The reason of why a {code boolean} test result is what it is.
         * @return A string describing the reason why the test result is {@code true} or {@code false}.
         */
        default String getReason() {
            return toString();
        }

        /**
         * Creates a {@link TestResult}, wrapping {@code false}, with given {@link #getReason()}.
         * @param reason Specified the reason why the test result is {@code false}
         */
        static TestResult testsFalse(String reason) {
            return new TestResult() {
                @Override
                public boolean getAsBoolean() {
                    return false;
                }
                @Override
                public String toString() {
                    return reason;
                }
            };
        }

        /**
         * Creates a {@link TestResult}, wrapping {@code true}, with given {@link #getReason()}.
         * @param reason Specified the reason why the test result is {@code true}
         */
        static TestResult testsTrue(String reason) {
            return new TestResult() {
                @Override
                public boolean getAsBoolean() {
                    return true;
                }
                @Override
                public String toString() {
                    return reason == null ? "tests true" : reason;
                }
                @Override
                public String getReason() {
                    return reason;
                }
            };
        }

        /**
         * Creates a {@link TestResult}, wrapping given {@code boolean}, with given {@link #getReason()}.
         * @param applies The boolean wrapped by the resulting {@code TestResult}
         * @param reason Specified the reason why the test result is {@code applies}
         */
        static TestResult of(boolean applies, String reason) {
            return applies ? testsTrue(reason) : testsFalse(reason);
        }
    }
}
