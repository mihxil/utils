package org.meeuw.collections;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * @author Michiel Meeuwissen
 * @since 1.18 (since 4.3 in vpro-shared-util)
 */
public class TransformingSortedSet<T, S> extends AbstractSet<T> implements SortedSet<T>, TransformingCollection<T, S, SortedSet<T>, SortedSet<S>> {
    private final SortedSet<S> wrapped;
    private final Map<S, Optional<T>> transformed;
    private final Function<S, T> transformer;
    private final Function<T, S> producer;

    private final Comparator<T> comparator = new Comparator<T>() {
        @Override
        public int compare (T o1, T o2){
            Comparator<? super S> wrappedComparator = wrapped.comparator();
            if (wrappedComparator == null) {
                wrappedComparator = (Comparator <? super S>) Comparator.naturalOrder();
            }
            return wrappedComparator.compare(producer.apply(o1), producer.apply(o2));
        }
    };

    /**
     * Creates a sorted view that transforms elements from the wrapped set.
     *
     * @param wrapped the backing set
     * @param transformer converts backing elements to view elements
     * @param producer converts view elements to backing elements
     */
    public TransformingSortedSet(SortedSet<S> wrapped, Function<S, T> transformer, Function<T, S> producer) {
        this.wrapped = wrapped;
        transformed = new HashMap<>(wrapped.size());
        this.transformer = transformer;
        this.producer = producer;
    }
    /**
     * Returns an iterator over the transformed elements in backing-set order.
     *
     * @return an iterator over transformed elements
     */
    @NonNull
    @Override
    public Iterator<T> iterator() {
        return TransformingCollection.super.iterator();
    }

    /**
     * Returns the number of elements in the backing set.
     *
     * @return the set size
     */
    @Override
    public int size() {
        return TransformingCollection.super.size();
    }

    /**
     * Adds an element after converting it to its backing representation.
     *
     * @param toAdd the element to add
     * @return whether the backing set changed
     */
    @Override
    public boolean add(T toAdd) {
        return TransformingCollection.super.add(toAdd);
    }

    /**
     * Removes an element after converting it to its backing representation.
     *
     * @param o the element to remove
     * @return whether the backing set changed
     */
    @Override
    public boolean remove(Object o) {
        return TransformingCollection.super.remove(o);
    }

    /**
     * Returns a comparator that orders transformed elements according to their backing values.
     *
     * @return the transformed-element comparator
     */
    @Override
    public Comparator<? super T> comparator() {
        return comparator;
    }

    /**
     * Returns a transformed view of the specified backing-set range.
     *
     * @param fromElement the inclusive lower bound
     * @param toElement the exclusive upper bound
     * @return a transformed range view
     */
    @NonNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return new TransformingSortedSet<>(wrapped.subSet(produce(fromElement), produce(toElement)), transformer, producer);

    }

    /**
     * Returns a transformed view of the backing-set head range.
     *
     * @param toElement the exclusive upper bound
     * @return a transformed head-range view
     */
    @NonNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        return new TransformingSortedSet<>(wrapped.headSet(produce(toElement)), transformer, producer);
    }

    /**
     * Returns a transformed view of the backing-set tail range.
     *
     * @param fromElement the inclusive lower bound
     * @return a transformed tail-range view
     */
    @NonNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return new TransformingSortedSet<>(wrapped.tailSet(produce(fromElement)), transformer, producer);
    }

    /**
     * Returns the first transformed element.
     *
     * @return the first element
     */
    @Override
    public T first() {
        return transform(wrapped.first());
    }

    /**
     * Returns the last transformed element.
     *
     * @return the last element
     */
    @Override
    public T last() {
        return transform(wrapped.last());
    }

    /**
     * Returns the backing sorted set.
     *
     * @return the backing set
     */
    @Override
    public SortedSet<S> unwrap() {
        return wrapped;
    }
    /**
     * Transforms a backing-set element into a view element.
     *
     * @param entry the backing element
     * @return the transformed element
     */
    @Override
    public T transform(S entry) {
        return transformed.computeIfAbsent(entry, e -> Optional.ofNullable(transformer.apply(e))).orElse(null);
    }

    /**
     * Produces a backing-set element from a view element.
     *
     * @param entry the view element
     * @return the backing element
     */
    @Override
    public S produce(T entry) {
        return producer.apply(entry);

    }

    /**
     * Creates an empty backing-element set with the backing set's ordering.
     *
     * @return an empty backing-element set
     */
    @Override
    public SortedSet<S> newWrap() {
        return new TreeSet<>(wrapped.comparator());

    }

    /**
     * Creates an empty transformed-element set with this set's ordering.
     *
     * @return an empty transformed-element set
     */
    @Override
    public SortedSet<T> newFiltered() {
        return new TreeSet<>(comparator);

    }

    /**
     * Returns a sorted set containing transformed elements whose backing elements match the predicate.
     *
     * @param s the predicate applied to backing elements
     * @return a sorted set of matching transformed elements
     */
    public SortedSet<T> filter(Predicate<S> s) {
        SortedSet<T> result = new TreeSet<>(comparator);
        for (S in : unwrap()) {
            if (s.test(in)) {
                result.add(transform(in));
            }
        }
        return result;
    }


}
