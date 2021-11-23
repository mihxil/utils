package org.meeuw.functional;

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

/**
 * A 'Reasoned' predicate, is a predicate, but besides the normal {@link #test(Object)} method it
 * also supplies a {@link #testWithReason(Object)},which returns the {@link BooleanSupplier} {@link TestResult}, which besides
 * the test result boolean also can give a reason for <em>why</em> the predicate evaluated as it did.
 *
 * This is useful e.g. when the predicate itself is combined from multiple other ones with all kind of (boolean) logic, and sometimes
 * a user may find it hard to understand _why_ the predicate evaluates as it does.
 */
public interface ReasonedPredicate<T> extends Predicate<T> {

    /**
     * Returns a reason and result for the predicate evaluation.
     */
    default TestResult testWithReason(T input) {
        boolean applies = test(input);
        if (applies) {
            return new TestResult() {
                @Override
                public String toString() {
                    return ReasonedPredicate.this.toString() + "(" + input + ")";
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
                    return "!" + ReasonedPredicate.this.toString() + "(" + input + ")";
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
     * Basicly wraps e boolean with a reason
     */
    interface TestResult extends BooleanSupplier {

        default String getReason() {
            return toString();
        }

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

        static TestResult of(boolean applies, String reason) {
            return applies ? testsTrue(reason) : testsFalse(reason);
        }
    }
}
