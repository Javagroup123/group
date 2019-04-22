//This code shows how to prevent the fragile base class problem (FBCP).

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * This is basically just an array list, but it also has a thingy. So it's a
 * pair of an array and something else.
 *
 * @param <E>
 *          The type of the array elements.
 * @param <T>
 *          The type of the thingy.
 */
public abstract class AbstractClass<E, T> implements SomeInterface<E> {
  /**
   * So this abstract implementation is backed by an array. Implementations using
   * a linked list would have to use another base class.
   * 
   * You might think it's better to have this field protected. But having all
   * fields private is always better for stable code.
   */
  private final E[] data;

  /**
   * This text here would explain what the thingy is.
   */
  private final T thingy;

  /**
   * Constructors don't contribute to the FBCP, but consider using factories.
   */
  public AbstractClass(E[] data, T thingy) {
    // create a defensive copy
    this.data = Arrays.copyOf(data, data.length);
    this.thingy = thingy;
  }

  /**
   * Making this final makes sure no subclass returns anything other than what was
   * passed to the constructor.
   */
  public final T getThingy() {
    return thingy;
  }

  /**
   * This allows to access the array. This is final because access to the array is
   * something this class does, even when it's abstract. This implementation is
   * better than the one give in the interface.
   */
  @Override
  public final Stream<E> stream() {
    return Arrays.stream(data);
  }

  /**
   * This is fine, because we also override #stream(). But this design as fragile.
   * Remove #stream() and you get a StackOverflowError.
   */
  @Override
  public void forEach(Consumer<? super E> action) {
    this.stream().forEach(action);
  }

  /** This is final because there is no way this could be optimised. */
  public final int getLength() {
    return this.data.length;
  }

  /**
   * Even if there is a faster way, it's not the job of the implementation to
   * override this. So it's final.
   */
  @Override
  public final Iterator<E> iterator() {
    return Arrays.asList(data).iterator();
  }

  /**
   * So that thingy seems to be able to do something. Probably with the array. But
   * there could be an optimised implementation for whatever type the thingy has.
   * So we let that class implement this important method, as we do not know
   * anything about the type of it.
   */
  public abstract long doSomething();

  /**
   * This is trivial, so but it's final because there isn't anything that could be
   * optimised.
   */
  public final boolean containsThingy() {
    return this.contains(thingy);
  }

  /**
   * This SHOULD be final. It may seem trivial. A programmer could be tempted to
   * make it non-final so that the implementation could optimise it. To
   * demonstrate the FBCP it is not final.
   */
  public boolean contains(Object o) {
    return Arrays.asList(data).contains(o);
  }

  /**
   * This is an empty method because not all objects have to be closed. Especially
   * when we use an array. Only when the data comes from a closeable resource it
   * must be overridden. In this case we leave it non-final so that the
   * implementation can clean up when it's closed.
   */
  @Override
  public void close() {

  }

}