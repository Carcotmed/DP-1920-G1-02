package generatedAssertions.customAssertions;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.util.Objects;
import org.springframework.samples.petclinic.model.PetType;

/**
 * {@link PetType} specific assertions - Generated by CustomAssertionGenerator.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class PetTypeAssert extends AbstractObjectAssert<PetTypeAssert, PetType> {

  /**
   * Creates a new <code>{@link PetTypeAssert}</code> to make assertions on actual PetType.
   * @param actual the PetType we want to make assertions on.
   */
  public PetTypeAssert(PetType actual) {
    super(actual, PetTypeAssert.class);
  }

  /**
   * An entry point for PetTypeAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myPetType)</code> and get specific assertion with code completion.
   * @param actual the PetType we want to make assertions on.
   * @return a new <code>{@link PetTypeAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static PetTypeAssert assertThat(PetType actual) {
    return new PetTypeAssert(actual);
  }

  /**
   * Verifies that the actual PetType's id is equal to the given one.
   * @param id the given id to compare the actual PetType's id to.
   * @return this assertion object.
   * @throws AssertionError - if the actual PetType's id is not equal to the given one.
   */
  public PetTypeAssert hasId(Integer id) {
    // check that actual PetType we want to make assertions on is not null.
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
   * Verifies that the actual PetType's name is equal to the given one.
   * @param name the given name to compare the actual PetType's name to.
   * @return this assertion object.
   * @throws AssertionError - if the actual PetType's name is not equal to the given one.
   */
  public PetTypeAssert hasName(String name) {
    // check that actual PetType we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting name of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualName = actual.getName();
    if (!Objects.areEqual(actualName, name)) {
      failWithMessage(assertjErrorMessage, actual, name, actualName);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual PetType is new.
   * @return this assertion object.
   * @throws AssertionError - if the actual PetType is not new.
   */
  public PetTypeAssert isNew() {
    // check that actual PetType we want to make assertions on is not null.
    isNotNull();

    // check that property call/field access is true
    if (!actual.isNew()) {
      failWithMessage("\nExpecting that actual PetType is new but is not.");
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual PetType is not new.
   * @return this assertion object.
   * @throws AssertionError - if the actual PetType is new.
   */
  public PetTypeAssert isNotNew() {
    // check that actual PetType we want to make assertions on is not null.
    isNotNull();

    // check that property call/field access is false
    if (actual.isNew()) {
      failWithMessage("\nExpecting that actual PetType is not new but is.");
    }

    // return the current assertion for method chaining
    return this;
  }

}