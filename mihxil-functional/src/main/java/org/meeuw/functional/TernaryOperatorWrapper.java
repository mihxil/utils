package org.meeuw.functional;

/**
 * @author Michiel Meeuwissen
 * @since 1.2
 */
abstract class TernaryOperatorWrapper<W, T> extends Wrapper<W> implements TernaryOperator<T> {
    /**
     * @param wrapped An object that this wrapper is wrapping, and can be used to implement it
     * @param reason  A description for why the wrapping happened
     */
    public TernaryOperatorWrapper(W wrapped, String reason) {
        super(wrapped, reason);
    }
}
