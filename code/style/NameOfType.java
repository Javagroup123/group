// Each file starts with a package declaration.
// It defines the namespace and must correspond to the folter structure.
// For very simple projects you can use no subfolders and leave out the package declaration.
package com.github.claudemartin.javagroup123;

// After that you have the list imports:
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Javadoc is used to describe all elements of the source code. Just start a
 * block with "/**" and describe what the element does.
 *
 * The name of a type (class/interface) must start with capital case and it must
 * be the same as the name of the file. It can't start with a digit. And there
 * can't be any whitespace in the name. That's why we use CamelCase.
 *
 * Source Code is written for humans. The CPU only knows machine code. Good
 * source code usualy has more documentation (English) than code (Java). So
 * write a lot, but not too much.
 *
 * @author Claude Martin
 *
 */
public class NameOfType {

  /**
   * Fields that are static and final sould be written in ALL_CAPS.
   *
   * Note that the Java language doesn't know about constants. The value could
   * be different for each invocation or version of your software.
   * Often an Enumeration (enum) should be used instead.
   */
  public static int          NAME_OF_CONSTANT = 123;

  /**
   * The name of a member variable should start in lower case. Usually there
   * should be a getter and setter. But this is final, so no setter.
   */
  private final List<String> nameOfMember     = new ArrayList<String>();

  /**
   * This one isn't final so there is a setter. Note that this would be
   * initialized as null, which is risky. But in this case the constructor will
   * initialize it.
   */
  private LocalTime          anotherMember;

  /** This is a constructor. It has the same name as the class. */
  public NameOfType() {
    // This makes sure there is a valid value and not null:
    this.setAnotherMember(LocalTime.now());
  }

  /**
   * A main method is a special method, that defines where the execution of a
   * program begins. It must be {@code public static void}.
   */
  public static void main(final String[] args) {
    // This is what a hello world looks like in Java:
    System.out.println("Hello world!");
  }

  /**
   * The first sentence should explain what the method does. Further sentences
   * just give more detail.
   *
   * @param someParameter
   *          Parameters are documented like this.
   * @return Here you should explain what this returns.
   */
  public long someMethod(final int someParameter) {
    // Local variables do not have javadoc. But you can always add comments.
    final long someLocalVariable = 12345;
    // Here would be some more code...
    return someLocalVariable;
  }

  /** Even getters should have some javadoc. */
  public List<String> getNameOfMember() {
    // This returns an unmodifiable view, to ensure encapsulation:
    return Collections.unmodifiableList(this.nameOfMember);
  }

  /** Even getters should have some javadoc. */
  public LocalTime getAnotherMember() {
    return this.anotherMember;
  }

  /**
   * And setters too. You should mention if there are any preconditions. In this
   * case null is not allowed.
   *
   * @throws NullPointerException
   *           if a null is passed.
   */
  public void setAnotherMember(final LocalTime anotherMember) {
    Objects.requireNonNull(anotherMember, "anotherMember must not be null");
    this.anotherMember = anotherMember;
  }

}
