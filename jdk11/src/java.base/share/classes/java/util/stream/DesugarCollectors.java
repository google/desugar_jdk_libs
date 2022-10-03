/*
 * Copyright (c) 2012, 2017, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package java.util.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors.CollectorImpl;

/**
 * Implementations of {@link Collector} that implement various useful reduction
 * operations, such as accumulating elements into collections, summarizing
 * elements according to various criteria, etc.
 *
 * <p>The following are examples of using the predefined collectors to perform
 * common mutable reduction tasks:
 *
 * <pre>{@code
 * // Accumulate names into a List
 * List<String> list = people.stream()
 *   .map(Person::getName)
 *   .collect(Collectors.toList());
 *
 * // Accumulate names into a TreeSet
 * Set<String> set = people.stream()
 *   .map(Person::getName)
 *   .collect(Collectors.toCollection(TreeSet::new));
 *
 * // Convert elements to strings and concatenate them, separated by commas
 * String joined = things.stream()
 *   .map(Object::toString)
 *   .collect(Collectors.joining(", "));
 *
 * // Compute sum of salaries of employee
 * int total = employees.stream()
 *   .collect(Collectors.summingInt(Employee::getSalary));
 *
 * // Group employees by department
 * Map<Department, List<Employee>> byDept = employees.stream()
 *   .collect(Collectors.groupingBy(Employee::getDepartment));
 *
 * // Compute sum of salaries by department
 * Map<Department, Integer> totalByDept = employees.stream()
 *   .collect(Collectors.groupingBy(Employee::getDepartment,
 *                                  Collectors.summingInt(Employee::getSalary)));
 *
 * // Partition students into passing and failing
 * Map<Boolean, List<Student>> passingFailing = students.stream()
 *   .collect(Collectors.partitioningBy(s -> s.getGrade() >= PASS_THRESHOLD));
 *
 * }</pre>
 *
 * @since 1.8
 */
public final class DesugarCollectors {

    static final Set<Collector.Characteristics> CH_NOID = Collections.emptySet();
    static final Set<Collector.Characteristics> CH_UNORDERED_NOID
        = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED));

    private DesugarCollectors() {}

    /**
     * Adapts a {@code Collector} to one accepting elements of the same type
     * {@code T} by applying the predicate to each input element and only
     * accumulating if the predicate returns {@code true}.
     *
     * @apiNote
     * The {@code filtering()} collectors are most useful when used in a
     * multi-level reduction, such as downstream of a {@code groupingBy} or
     * {@code partitioningBy}.  For example, given a stream of
     * {@code Employee}, to accumulate the employees in each department that have a
     * salary above a certain threshold:
     * <pre>{@code
     * Map<Department, Set<Employee>> wellPaidEmployeesByDepartment
     *   = employees.stream().collect(
     *     groupingBy(Employee::getDepartment,
     *                filtering(e -> e.getSalary() > 2000,
     *                          toSet())));
     * }</pre>
     * A filtering collector differs from a stream's {@code filter()} operation.
     * In this example, suppose there are no employees whose salary is above the
     * threshold in some department.  Using a filtering collector as shown above
     * would result in a mapping from that department to an empty {@code Set}.
     * If a stream {@code filter()} operation were done instead, there would be
     * no mapping for that department at all.
     *
     * @param <T> the type of the input elements
     * @param <A> intermediate accumulation type of the downstream collector
     * @param <R> result type of collector
     * @param predicate a predicate to be applied to the input elements
     * @param downstream a collector which will accept values that match the
     * predicate
     * @return a collector which applies the predicate to the input elements
     * and provides matching elements to the downstream collector
     * @since 9
     */
    public static <T, A, R>
    Collector<T, ?, R> filtering(
        Predicate<? super T> predicate,
        Collector<? super T, A, R> downstream) {
        BiConsumer<A, ? super T> downstreamAccumulator = downstream.accumulator();
        return new CollectorImpl<>(downstream.supplier(),
            (r, t) -> {
                if (predicate.test(t)) {
                    downstreamAccumulator.accept(r, t);
                }
            },
            downstream.combiner(), downstream.finisher(),
            downstream.characteristics());
    }

    /**
     * Adapts a {@code Collector} accepting elements of type {@code U} to one
     * accepting elements of type {@code T} by applying a flat mapping function
     * to each input element before accumulation.  The flat mapping function
     * maps an input element to a {@link Stream stream} covering zero or more
     * output elements that are then accumulated downstream.  Each mapped stream
     * is {@link java.util.stream.BaseStream#close() closed} after its contents
     * have been placed downstream.  (If a mapped stream is {@code null}
     * an empty stream is used, instead.)
     *
     * @apiNote
     * The {@code flatMapping()} collectors are most useful when used in a
     * multi-level reduction, such as downstream of a {@code groupingBy} or
     * {@code partitioningBy}.  For example, given a stream of
     * {@code Order}, to accumulate the set of line items for each customer:
     * <pre>{@code
     * Map<String, Set<LineItem>> itemsByCustomerName
     *   = orders.stream().collect(
     *     groupingBy(Order::getCustomerName,
     *                flatMapping(order -> order.getLineItems().stream(),
     *                            toSet())));
     * }</pre>
     *
     * @param <T> the type of the input elements
     * @param <U> type of elements accepted by downstream collector
     * @param <A> intermediate accumulation type of the downstream collector
     * @param <R> result type of collector
     * @param mapper a function to be applied to the input elements, which
     * returns a stream of results
     * @param downstream a collector which will receive the elements of the
     * stream returned by mapper
     * @return a collector which applies the mapping function to the input
     * elements and provides the flat mapped results to the downstream collector
     * @since 9
     */
    public static <T, U, A, R>
    Collector<T, ?, R> flatMapping(Function<? super T, ? extends Stream<? extends U>> mapper,
        Collector<? super U, A, R> downstream) {
        BiConsumer<A, ? super U> downstreamAccumulator = downstream.accumulator();
        return new CollectorImpl<>(downstream.supplier(),
            (r, t) -> {
                try (Stream<? extends U> result = mapper.apply(t)) {
                    if (result != null)
                        result.sequential().forEach(u -> downstreamAccumulator.accept(r, u));
                }
            },
            downstream.combiner(), downstream.finisher(),
            downstream.characteristics());
    }

    /**
     * Returns a {@code Collector} that accumulates the input elements into an
     * <a href="../List.html#unmodifiable">unmodifiable List</a> in encounter
     * order. The returned Collector disallows null values and will throw
     * {@code NullPointerException} if it is presented with a null value.
     *
     * @param <T> the type of the input elements
     * @return a {@code Collector} that accumulates the input elements into an
     * <a href="../List.html#unmodifiable">unmodifiable List</a> in encounter order
     * @since 10
     */
    @SuppressWarnings("unchecked")
    public static <T>
    Collector<T, ?, List<T>> toUnmodifiableList() {
        return new CollectorImpl<>((Supplier<List<T>>) ArrayList::new, List::add,
            (left, right) -> { left.addAll(right); return left; },
            list -> (List<T>)List.of(list.toArray()),
            CH_NOID);
    }

    /**
     * Returns a {@code Collector} that accumulates the input elements into an
     * <a href="../Map.html#unmodifiable">unmodifiable Map</a>,
     * whose keys and values are the result of applying the provided
     * mapping functions to the input elements.
     *
     * <p>If the mapped keys contain duplicates (according to
     * {@link Object#equals(Object)}), an {@code IllegalStateException} is
     * thrown when the collection operation is performed.  If the mapped keys
     * might have duplicates, use {@link #toUnmodifiableMap(Function, Function, BinaryOperator)}
     * to handle merging of the values.
     *
     * <p>The returned Collector disallows null keys and values. If either mapping function
     * returns null, {@code NullPointerException} will be thrown.
     *
     * @param <T> the type of the input elements
     * @param <K> the output type of the key mapping function
     * @param <U> the output type of the value mapping function
     * @param keyMapper a mapping function to produce keys, must be non-null
     * @param valueMapper a mapping function to produce values, must be non-null
     * @return a {@code Collector} that accumulates the input elements into an
     * <a href="../Map.html#unmodifiable">unmodifiable Map</a>, whose keys and values
     * are the result of applying the provided mapping functions to the input elements
     * @throws NullPointerException if either keyMapper or valueMapper is null
     *
     * @see #toUnmodifiableMap(Function, Function, BinaryOperator)
     * @since 10
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T, K, U>
    Collector<T, ?, Map<K,U>> toUnmodifiableMap(Function<? super T, ? extends K> keyMapper,
        Function<? super T, ? extends U> valueMapper) {
        Objects.requireNonNull(keyMapper, "keyMapper");
        Objects.requireNonNull(valueMapper, "valueMapper");
        return Collectors.collectingAndThen(
            Collectors.toMap(keyMapper, valueMapper),
            map -> (Map<K,U>)Map.ofEntries(map.entrySet().toArray(new Map.Entry[0])));
    }

    /**
     * Returns a {@code Collector} that accumulates the input elements into an
     * <a href="../Map.html#unmodifiable">unmodifiable Map</a>,
     * whose keys and values are the result of applying the provided
     * mapping functions to the input elements.
     *
     * <p>If the mapped
     * keys contain duplicates (according to {@link Object#equals(Object)}),
     * the value mapping function is applied to each equal element, and the
     * results are merged using the provided merging function.
     *
     * <p>The returned Collector disallows null keys and values. If either mapping function
     * returns null, {@code NullPointerException} will be thrown.
     *
     * @param <T> the type of the input elements
     * @param <K> the output type of the key mapping function
     * @param <U> the output type of the value mapping function
     * @param keyMapper a mapping function to produce keys, must be non-null
     * @param valueMapper a mapping function to produce values, must be non-null
     * @param mergeFunction a merge function, used to resolve collisions between
     *                      values associated with the same key, as supplied
     *                      to {@link Map#merge(Object, Object, BiFunction)},
     *                      must be non-null
     * @return a {@code Collector} that accumulates the input elements into an
     * <a href="../Map.html#unmodifiable">unmodifiable Map</a>, whose keys and values
     * are the result of applying the provided mapping functions to the input elements
     * @throws NullPointerException if the keyMapper, valueMapper, or mergeFunction is null
     *
     * @see #toUnmodifiableMap(Function, Function)
     * @since 10
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T, K, U>
    Collector<T, ?, Map<K,U>> toUnmodifiableMap(
        Function<? super T, ? extends K> keyMapper,
        Function<? super T, ? extends U> valueMapper,
        BinaryOperator<U> mergeFunction) {
        Objects.requireNonNull(keyMapper, "keyMapper");
        Objects.requireNonNull(valueMapper, "valueMapper");
        Objects.requireNonNull(mergeFunction, "mergeFunction");
        return Collectors.collectingAndThen(
            Collectors.toMap(keyMapper, valueMapper, mergeFunction, HashMap::new),
            map -> (Map<K,U>)Map.ofEntries(map.entrySet().toArray(new Map.Entry[0])));
    }

    /**
     * Returns a {@code Collector} that accumulates the input elements into an
     * <a href="../Set.html#unmodifiable">unmodifiable Set</a>. The returned
     * Collector disallows null values and will throw {@code NullPointerException}
     * if it is presented with a null value. If the input contains duplicate elements,
     * an arbitrary element of the duplicates is preserved.
     *
     * <p>This is an {@link Collector.Characteristics#UNORDERED unordered}
     * Collector.
     *
     * @param <T> the type of the input elements
     * @return a {@code Collector} that accumulates the input elements into an
     * <a href="../Set.html#unmodifiable">unmodifiable Set</a>
     * @since 10
     */
    @SuppressWarnings("unchecked")
    public static <T>
    Collector<T, ?, Set<T>> toUnmodifiableSet() {
        return new CollectorImpl<>((Supplier<Set<T>>) HashSet::new, Set::add,
            (left, right) -> {
                if (left.size() < right.size()) {
                    right.addAll(left); return right;
                } else {
                    left.addAll(right); return left;
                }
            },
            set -> (Set<T>)Set.of(set.toArray()),
            CH_UNORDERED_NOID);
    }

    /**
     * Simple implementation class for {@code Collector}.
     *
     * @param <T> the type of elements to be collected
     * @param <R> the type of the result
     */
    static class CollectorImpl<T, A, R> implements Collector<T, A, R> {
        private final Supplier<A> supplier;
        private final BiConsumer<A, T> accumulator;
        private final BinaryOperator<A> combiner;
        private final Function<A, R> finisher;
        private final Set<Characteristics> characteristics;

        CollectorImpl(Supplier<A> supplier,
            BiConsumer<A, T> accumulator,
            BinaryOperator<A> combiner,
            Function<A,R> finisher,
            Set<Characteristics> characteristics) {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.combiner = combiner;
            this.finisher = finisher;
            this.characteristics = characteristics;
        }

        CollectorImpl(Supplier<A> supplier,
            BiConsumer<A, T> accumulator,
            BinaryOperator<A> combiner,
            Set<Characteristics> characteristics) {
            this(supplier, accumulator, combiner, castingIdentity(), characteristics);
        }

        @Override
        public BiConsumer<A, T> accumulator() {
            return accumulator;
        }

        @Override
        public Supplier<A> supplier() {
            return supplier;
        }

        @Override
        public BinaryOperator<A> combiner() {
            return combiner;
        }

        @Override
        public Function<A, R> finisher() {
            return finisher;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    }

    @SuppressWarnings("unchecked")
    private static <I, R> Function<I, R> castingIdentity() {
        return i -> (R) i;
    }
}
