package org.meeuw.functional;

import java.util.NoSuchElementException;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * A container object which may or may not contain a {@code boolean} value.
 * If a value is present, {@code isPresent()} returns {@code true}. If no
 * value is present, the object is considered <i>empty</i> and
 * {@code isPresent()} returns {@code false}.
 *
 * <p>Additional methods that depend on the presence or absence of a contained
 * value are provided, such as {@link #orElse(boolean) orElse()}
 * (returns a default value if no value is present) and
 * {@link #ifPresent(BooleanConsumer) ifPresent()} (performs an
 * action if a value is present).
 *
 * <p>This is a <a href="{@docRoot}/java.base/java/lang/doc-files/ValueBased.html">value-based</a>
 * class; programmers should treat instances that are
 * {@linkplain #equals(Object) equal} as interchangeable and should not
 * use instances for synchronization, or unpredictable behavior may
 * occur. For example, in a future release, synchronization may fail.
 *
 * @apiNote
 * {@code OptionBoolean} is primarily intended for use as a method return type where
 * there is a clear need to represent "no result." A variable whose type is
 * {@code OptionBoolean} should never itself be {@code null}; it should always point
 * to an {@code OptionBoolean} instance.
 *
 * @since 1.10
 */

public enum OptionalBoolean {

    /**
     * Common instance for {@code empty()}.
     */
    EMPTY,

    TRUE(true),

    FALSE(false);


    /**
     * If true then the value is present, otherwise indicates no value is present
     */
    private final boolean isPresent;
    private final boolean value;

    /**
     * Construct an empty instance.
     *
     * @implNote generally only one empty instance, {@link OptionalBoolean#EMPTY},
     * should exist per VM.
     */
    OptionalBoolean() {
        this.isPresent = false;
        this.value = false;
    }

    /**
     * Returns an empty {@code OptionalBoolean} instance.  No value is present for
     * this {@code OptionalBoolean}.
     *
     * @returns the  empty {@code OptionalBoolean}.
     */
    public static OptionalBoolean empty() {
        return EMPTY;
    }

    public static OptionalBoolean optionalTrue() {
        return TRUE;
    }
    public static OptionalBoolean optionalFalse() {
        return FALSE;
    }

    /**
     * Construct an instance with the described value.
     *
     * @param value the boolean value to describe
     */
    OptionalBoolean(boolean value) {
        this.isPresent = true;
        this.value = value;
    }



    /**
     * If a value is present, returns the value, otherwise throws
     * {@code NoSuchElementException}.
     *
     * @apiNote
     * The preferred alternative to this method is {@link #orElseThrow()}.
     *
     * @return the value described by this {@code OptionalBoolean}
     * @throws NoSuchElementException if no value is present
     */
    public boolean getAsBoolean() {
        if (!isPresent) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    /**
     * If a value is present, returns {@code true}, otherwise {@code false}.
     *
     * @return {@code true} if a value is present, otherwise {@code false}
     */
    public boolean isPresent() {
        return isPresent;
    }

    /**
     * If a value is not present, returns {@code true}, otherwise
     * {@code false}.
     *
     * @return  {@code true} if a value is not present, otherwise {@code false}
     * @since   11
     */
    public boolean isEmpty() {
        return !isPresent;
    }

    /**
     * If a value is present, performs the given action with the value,
     * otherwise does nothing.
     *
     * @param action the action to be performed, if a value is present
     * @throws NullPointerException if value is present and the given action is
     *         {@code null}
     */
    public void ifPresent(BooleanConsumer action) {
        if (isPresent) {
            action.accept(value);
        }
    }

    /**
     * If a value is present, performs the given action with the value,
     * otherwise performs the given empty-based action.
     *
     * @param action the action to be performed, if a value is present
     * @param emptyAction the empty-based action to be performed, if no value is
     *        present
     * @throws NullPointerException if a value is present and the given action
     *         is {@code null}, or no value is present and the given empty-based
     *         action is {@code null}.
     * @since 9
     */
    public void ifPresentOrElse(BooleanConsumer action, Runnable emptyAction) {
        if (isPresent) {
            action.accept(value);
        } else {
            emptyAction.run();
        }
    }


    /**
     * If a value is present, returns the value, otherwise returns
     * {@code other}.
     *
     * @param other the value to be returned, if no value is present
     * @return the value, if present, otherwise {@code other}
     */
    public boolean orElse(boolean other) {
        return isPresent ? value : other;
    }

    /**
     * If a value is present, returns the value, otherwise returns the result
     * produced by the supplying function.
     *
     * @param supplier the supplying function that produces a value to be returned
     * @return the value, if present, otherwise the result produced by the
     *         supplying function
     * @throws NullPointerException if no value is present and the supplying
     *         function is {@code null}
     */
    public boolean orElseGet(BooleanSupplier supplier) {
        return isPresent ? value : supplier.getAsBoolean();
    }

    /**
     * If a value is present, returns the value, otherwise throws
     * {@code NoSuchElementException}.
     *
     * @return the value described by this {@code OptionalBoolean}
     * @throws NoSuchElementException if no value is present
     * @since 10
     */
    public boolean orElseThrow() {
        if (!isPresent) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    /**
     * If a value is present, returns the value, otherwise throws an exception
     * produced by the exception supplying function.
     *
     * @apiNote
     * A method reference to the exception constructor with an empty argument
     * list can be used as the supplier. For example,
     * {@code IllegalStateException::new}
     *
     * @param <X> Type of the exception to be thrown
     * @param exceptionSupplier the supplying function that produces an
     *        exception to be thrown
     * @return the value, if present
     * @throws X if no value is present
     * @throws NullPointerException if no value is present and the exception
     *         supplying function is {@code null}
     */
    public<X extends Throwable> boolean orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (isPresent) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }



    /**
     * Returns a non-empty string representation of this {@code OptionalBoolean}
     * suitable for debugging.  The exact presentation format is unspecified and
     * may vary between implementations and versions.
     *
     * @implSpec
     * If a value is present the result must include its string representation
     * in the result.  Empty and present {@code OptionalBoolean}s must be
     * unambiguously differentiable.
     *
     * @return the string representation of this instance
     */
    @Override
    public String toString() {
        return isPresent
                ? ("OptionalBoolean[" + value + "]")
                : "OptionalBoolean.empty";
    }


}
