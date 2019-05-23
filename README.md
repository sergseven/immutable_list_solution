# instanceOf replacement


#### The challange

Given immutable list `immutable.ImmutableListImpl` with already implemented methods:
* `T head()` - returns first element of the list
* `boolean isEmpty()`- checks if list is empty
* `ImmutableList<T> tail()` - returns new instance of list containing sublist containing all elements except first
* `ImmutableList<T> prepend(T elem)` - adds element to the beginning of the list, returns new instance of list

*Implement missing methods of this class.*

#### Requirements

* Do not use loops
* Do not use other collections and arrays
* Do not change the `ImmutableList<T>` interface
* Reuse existing methods to implement new ones

## Environment setup

Basic template with attached test libraries and java execution task.

To compile code:
```
./mvnw compile
```
to run sample code from main class:
```
./mvnw compile exec:java
```
to run tests:
```
./mvnw test
```
to zip task sources with master pom.xml (from `maven_project_template` dir):
```
./mvnw assembly:single
```

## Libraries attached to project

Some potentially useful libraries are attached to project. You can (but you don't have to) use them:
* Javaslang
* RxJava
* Guava
* Lombok
* Apache Commons Lang
* JUnit
* Spock Framework
* TestNg

to switch between `JUnit` and `TestNG` uncomment proper section in `pom.xml` file (by default`JUnit`is enabled).
