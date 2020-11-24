package org.meeuw.functional;

/**
 * @author Michiel Meeuwissen
 * @since 1.2
 */
abstract class QuaternaryOperatorWrapper<W, T> extends Wrapper<W> implements QuaternaryOperator<T> {
    /**
     * @param wrapped An object that this wrapper is wrapping, and can be used to implement it
     * @param reason  A description for why the wrapping happened
     */
    public QuaternaryOperatorWrapper(W wrapped, String reason) {
        super(wrapped, reason);
    }
}
