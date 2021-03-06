package generatedAssertions.customAssertions;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.util.Objects;
import org.springframework.samples.petclinic.model.Person;

/**
 * {@link Person} specific assertions - Generated by CustomAssertionGenerator.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class PersonAssert extends AbstractObjectAssert<PersonAssert, Person> {

  /**
   * Creates a new <code>{@link PersonAssert}</code> to make assertions on actual Person.
   * @param actual the Person we want to make assertions on.
   */
  public PersonAssert(Person actual) {
    super(actual, PersonAssert.class);
  }

  /**
   * An entry point for PersonAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myPerson)</code> and get specific assertion with code completion.
   * @param actual the Person we want to make assertions on.
   * @return a new <code>{@link PersonAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static PersonAssert assertThat(Person actual) {
    return new PersonAssert(actual);
  }

  /**
   * Verifies that the actual Person's firstName is equal to the given one.
   * @param firstName the given firstName to compare the actual Person's firstName to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Person's firstName is not equal to the given one.
   */
  public PersonAssert hasFirstName(String firstName) {
    // check that actual Person we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting firstName of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualFirstName = actual.getFirstName();
    if (!Objects.areEqual(actualFirstName, firstName)) {
      failWithMessage(assertjErrorMessage, actual, firstName, actualFirstName);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Person's id is equal to the given one.
   * @param id the given id to compare the actual Person's id to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Person's id is not equal to the given one.
   */
  public PersonAssert hasId(Integer id) {
    // check that actual Person we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting id of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Integer actualId = actual.getId();
    if (!Objects.areEqual(actualId, id)) {
      failWithMessage(assertjErrorMessage, actual, id, actualId);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Person's lastName is equal to the given one.
   * @param lastName the given lastName to compare the actual Person's lastName to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Person's lastName is not equal to the given one.
   */
  public PersonAssert hasLastName(String lastName) {
    // check that actual Person we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting lastName of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualLastName = actual.getLastName();
    if (!Objects.areEqual(actualLastName, lastName)) {
      failWithMessage(assertjErrorMessage, actual, lastName, actualLastName);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Person is new.
   * @return this assertion object.
   * @throws AssertionError - if the actual Person is not new.
   */
  public PersonAssert isNew() {
    // check that actual Person we want to make assertions on is not null.
    isNotNull();

    // check that property call/field access is true
    if (!actual.isNew()) {
      failWithMessage("\nExpecting that actual Person is new but is not.");
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Person is not new.
   * @return this assertion object.
   * @throws AssertionError - if the actual Person is new.
   */
  public PersonAssert isNotNew() {
    // check that actual Person we want to make assertions on is not null.
    isNotNull();

    // check that property call/field access is false
    if (actual.isNew()) {
      failWithMessage("\nExpecting that actual Person is not new but is.");
    }

    // return the current assertion for method chaining
    return this;
  }

}
