package immutable;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface ImmutableList<T> {
    T head();
    boolean isEmpty();
    ImmutableList<T> tail();
    ImmutableList<T> prepend(T elem);

    <R> ImmutableList<R> map(Function<T, R> f);
    T get(int index);
    ImmutableList<T> remove(int index);
    ImmutableList<T> append(T elem);
    ImmutableList<T> revert();
    <R> R foldLeft(R accumulator, BiFunction<R, T, R> f);
    <R> ImmutableList<R> flatMap(Function<T, ImmutableList<R>> f);
    ImmutableList<T> sort(Comparator<T> comparator);
}
