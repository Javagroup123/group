//This code shows how to prevent the fragile base class problem (FBCP).

import java.util.function.Predicate;

/** Special implementation for when the thingy is a Predicate. This is final because other 
  * implementations should just extend AbstractClass. */
public final class PredicateImplementation<E> extends AbstractClass<E, Predicate<E>> {
  /** We just use the same constructor from the base class. */
  public PredicateImplementation(E[] data, Predicate<E> thingy) {
    super(data, thingy);
  }

  @Override
  public long doSomething() {
    return stream().filter(this.getThingy()).count();
  }

  @Override
  public boolean contains(Object o) {
    // Oh, boy. Someone tried to optimise this code.
    if (o == getThingy())
      return super.containsThingy();
    else
      return super.contains(o);
  }

  /** Static methods should be final, so no other class can hide it. */
  public final static <E> AbstractClass<E, Predicate<E>> getInstance(E[] data, Predicate<E> thingy) {
    return new PredicateImplementation<E>(data, thingy);
  }

  public static void main(String[] args) {
    final String[] strings = { "foo", "bar", "baz" };

    try (PredicateImplementation<String> instance = new PredicateImplementation<>(strings, s -> s.length() == 3)) {
      // This works because the abstract class correctly overrides both stream() and forEach():
      instance.forEach(System.out::println);
      // All words are 3 letters long, so this will print 3:
      System.out.println(instance.doSomething());
      // This will cause a StackOverflowError:
      System.out.println(instance.containsThingy());
    }
  }
}
