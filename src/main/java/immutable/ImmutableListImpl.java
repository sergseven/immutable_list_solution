package immutable;


import static java.util.function.Function.identity;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.apache.commons.lang3.NotImplementedException;

public class ImmutableListImpl<T> implements ImmutableList<T> {

  private final BasicImmutableList<T> basicList;

  public ImmutableListImpl() {
    basicList = new BasicImmutableList<>();
  }

  private ImmutableListImpl(BasicImmutableList<T> basicList) {
    this.basicList = basicList;
  }

  @Override
  public T head() {
    return basicList.head();
  }

  @Override
  public boolean isEmpty() {
    return basicList.isEmpty();
  }

  @Override
  public ImmutableList<T> tail() {
    return new ImmutableListImpl<>(basicList.tail());
  }

  @Override
  public ImmutableList<T> prepend(T elem) {
    return new ImmutableListImpl<>(basicList.add(elem));
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof ImmutableListImpl) {
      return this.basicList.equals(((ImmutableListImpl) object).basicList);
    } else {
      return false;
    }
  }

  @Override
  public <R> ImmutableList<R> map(Function<T, R> f) {
    return mapWithRevert(f, this, new ImmutableListImpl<>()).revert();
  }

  @Override
  public T get(int index) {
    if (index == 0) {
      return head();
    }

    return tail().get(index - 1);
  }

  @Override
  public ImmutableList<T> remove(int index) {
    if (index == 0) {
      return tail();
    }

    return tail().remove(index - 1).prepend(head());
  }

  @Override
  public ImmutableList<T> append(T elem) {
    return revert().prepend(elem).revert();
  }

  @Override
  public ImmutableList<T> revert() {
    return mapWithRevert(identity(), this, new ImmutableListImpl<>());
  }

  @Override
  public <R> R foldLeft(R accumulator, BiFunction<R, T, R> f) {
    return isEmpty()
        ? accumulator
        : tail().isEmpty()
            ? f.apply(accumulator, head())
            : tail().foldLeft(f.apply(accumulator, head()), f);
  }

  @Override
  public <R> ImmutableList<R> flatMap(Function<T, ImmutableList<R>> f) {
    if(isEmpty()) {
      return new ImmutableListImpl<>();
    }

    ImmutableList<R> flattened = f.apply(head());
    return prependAll(tail().flatMap(f), flattened);
  }

  @Override
  public ImmutableList<T> sort(Comparator<T> c) {
    if(isEmpty() || tail().isEmpty()) {
      return this;
    }

    if(c.compare(head(), tail().head()) > 0) {
      //swap
      T elem1 = head();
      T elem2 = tail().head();

      ImmutableList<T> l = tail().tail().prepend(elem1).prepend(elem2);

//      sortExt(c, l);
    }
    throw new NotImplementedException("implement me!!!");
  }

  @Override
  public String toString() {
    return basicList.toString();
  }

  public static <R> ImmutableList<R> prependAll(ImmutableList<R> target, ImmutableList<R> source){
    if(source.isEmpty()) {
      return target;
    }

    ImmutableList<R> sourceReverted = source.revert();
    return prependAll(target.prepend(sourceReverted.head()), sourceReverted.tail().revert());
  }

  private static <T, R> ImmutableList<R> mapWithRevert(Function<T, R> f,
      ImmutableList<T> source,
      ImmutableList<R> mapped) {
    if (source.isEmpty()) {
      return mapped;
    }

    R result = f.apply(source.head());
    ImmutableList<R> prepended = mapped.prepend(result);
    return mapWithRevert(f, source.tail(), prepended);
  }
}
