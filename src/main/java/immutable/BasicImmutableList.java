package immutable;


import java.util.LinkedList;
import java.util.List;

public class BasicImmutableList<T> {
    private final List<T> internalList;

    public BasicImmutableList() {
        this.internalList = new LinkedList<>();
    }

    private BasicImmutableList(List<T> internalList) {
        this.internalList = new LinkedList<>(internalList);
    }

    private List<T> copyList() {
        return new LinkedList<>(this.internalList);
    }

    public final T head() {
        return internalList.get(0);
    }

    public final boolean isEmpty() {
        return internalList.isEmpty();
    }

    public final BasicImmutableList<T> tail() {
        if(isEmpty()) {
            return new BasicImmutableList<>();
        }
        return new BasicImmutableList<>(copyList().subList(1, this.internalList.size()));
    }

    public BasicImmutableList<T> add(T elem) {
        List<T> internalList = copyList();
        internalList.add(0, elem);
        return new BasicImmutableList<>(internalList);
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof BasicImmutableList) {
            BasicImmutableList l = (BasicImmutableList)object;
            return isEmpty() && l.isEmpty() ||
                    !isEmpty() && !l.isEmpty() && head().equals(l.head()) && tail().equals(l.tail());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return internalList.toString();
    }
}
