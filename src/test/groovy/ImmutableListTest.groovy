import immutable.ImmutableList
import immutable.ImmutableListImpl
import javaslang.collection.Array
import spock.lang.Specification

class ImmutableListTest extends Specification {

    private def <T> ImmutableList<T> toImmutableList(Array<T> a) {
        if (a.empty) {
            return new ImmutableListImpl<T>()
        } else {
            return toImmutableList(a.tail()).prepend(a.head())
        }
    }


    def "Immutable list should execute adding, getting, head, tail and indicating if it is empty correctly"() {
        when: "new instance is created"
        def newList = new ImmutableListImpl<String>()
        then: "should be empty"
        newList.isEmpty() == true
        when: "new element is added"
        def list = new ImmutableListImpl<String>()
        def listWithElem = list.prepend("elem1")
        then: "new list with one element should be created"
        list.isEmpty() == true
        listWithElem.isEmpty() == false
        listWithElem.head() == "elem1"
        when: "list contains two elements"
        def listWithTwoElems = listWithElem.prepend("elem2")
        then: "last added element should be head"
        listWithTwoElems.head() == "elem2"
        listWithTwoElems.tail().head() == "elem1"
        listWithTwoElems.tail().tail().isEmpty() == true
    }

    def "Map should apply mapping function correctly"() {
        when: "integers are mapped to strings"
        def mapped = toImmutableList(Array.of(1, 2, 3)).map({ i -> String.valueOf(i) })
        then: "mapped list should contain string representation of ints"
        mapped == toImmutableList(Array.of("1", "2", "3"))
    }


    def "Get should return element at selected index"() {
        when: "element at some index is get"
        def l = toImmutableList(Array.of(1, 2, 3))
        then: "correct value should be returned"
        l.get(0) == 1
        l.get(1) == 2
        l.get(2) == 3
        when: "trying to get element at not existing index"
        l.get(3)
        then:
        thrown IndexOutOfBoundsException
    }

    def "remove should remove element at selected index"() {
        when:
        def l = toImmutableList(Array.of(1, 2, 3))
        def lWithout1 = l.remove(0)
        def lWithout2 = l.remove(1)
        def lWithout3 = l.remove(2)
        def lEmpty = l.remove(0).remove(0).remove(0)
        then:
        lWithout1 == toImmutableList(Array.of(2, 3))
        lWithout2 == toImmutableList(Array.of(1, 3))
        lWithout3 == toImmutableList(Array.of(1, 2))
        lEmpty.isEmpty() == true
    }

    def "append should appending element at the end of list"() {
        when:
        def l = new ImmutableListImpl<Integer>().append(1).append(2).append(3)
        then:
        l == toImmutableList(Array.of(1, 2, 3))
    }

    def "revert should revert list order"() {
        when:
        def l = toImmutableList(Array.of(1, 2, 3)).revert()
        then:
        l == toImmutableList(Array.of(3, 2, 1))
    }

    def "should fold left correctly"() {
        when:
        def folded = toImmutableList(Array.of("a", "b")).foldLeft("_init_", { a, b -> a + b })
        def emptyFolded = toImmutableList(Array.of()).foldLeft("_init_", { a, b -> a + b })
        then:
        folded == "_init_ab"
        emptyFolded == "_init_"
    }


    def "should flatMap correctly"() {
        when:
        def mapped = toImmutableList(Array.of(
                toImmutableList(Array.of("a", "b", "c")),
                toImmutableList(Array.of("d", "e")),
                toImmutableList(Array.of("f", "g", "k", "l")),
                toImmutableList(Array.of("m", "n"))))
                .flatMap({ l -> l.tail() })
        then:
        mapped == toImmutableList(Array.of("b", "c", "e", "g", "k", "l", "n"))
    }

    def "prependAll should prepend all list with correct order"() {
        when:
        def target = toImmutableList(Array.of(4, 5, 6))
        def source1 = toImmutableList(Array.of(1, 2, 3))
        then:
        def prepended = ImmutableListImpl.prependAll(target, source1)
        expect:
        prepended == toImmutableList(Array.of(1, 2, 3, 4, 5, 6))
    }

    def "sorting should sort correctly"() {
        when:
        def comparator = new Comparator<Integer>() {
            @Override
            int compare(Integer o1, Integer o2) {
                return o1 <=> o2
            }
        }
        def alreadySorted = toImmutableList(Array.of(1, 2, 3)).sort(comparator)
        def empty = toImmutableList(Array.of()).sort()
        def sorted = toImmutableList(Array.of(4, 2, 3, 2, 7, 1)).sort(comparator)
        then:
        alreadySorted == toImmutableList(Array.of(1, 2, 3))
        empty == toImmutableList(Array.of())
        sorted == toImmutableList(Array.of(1, 2, 2, 3, 4, 7))
    }
}
