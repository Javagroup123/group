//This code shows how to prevent the fragile base class problem (FBCP).

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;;

/**
 * Interfaces are here to describe abstract ideas. This is something that can be
 * iterated and closed.
 */
public interface SomeInterface<E> extends Iterable<E>, AutoCloseable {
  /**
   * This is a default method in an interface. It's trivial and you will often see
   * forEach() implemented as a default method. Documentation describes this
   * method and how to override it. It's important to tell programmers that they
   * must override both stream() and forEach() or leave both naive implementations
   * from this interface. It must also describe whether the order of the elements
   * is relevant. Whether action must accept null. And so on.
   * 
   * Default methods can't be final, so they must always be trivial or even empty.
   * To prevent the problem you would leave both abstract and let an abstract
   * class implement both.
   */
  default void forEach(Consumer<? super E> action) {
    Objects.requireNonNull(action);
    for (E e : this)
      action.accept(e);
  }

  /**
   * Such methods only exist to make the implementation easy. It should always be
   * overridden to improve performance. It's important to tell programmers that
   * they must override both stream() and forEach() or leave both naive
   * implementations from this interface. It's ok to implement it, but there's a
   * high risk that someone will override forEach() based on this stream() method.
   */
  default Stream<E> stream() {
    final ArrayList<E> list = new ArrayList<>();
    forEach(list::add);
    return list.stream();
  }
}