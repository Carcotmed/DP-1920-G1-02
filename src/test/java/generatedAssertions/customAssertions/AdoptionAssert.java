package generatedAssertions.customAssertions;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.util.Objects;
import org.springframework.samples.petclinic.model.Adoption;

/**
 * {@link Adoption} specific assertions - Generated by CustomAssertionGenerator.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class AdoptionAssert extends AbstractObjectAssert<AdoptionAssert, Adoption> {

  /**
   * Creates a new <code>{@link AdoptionAssert}</code> to make assertions on actual Adoption.
   * @param actual the Adoption we want to make assertions on.
   */
  public AdoptionAssert(Adoption actual) {
    super(actual, AdoptionAssert.class);
  }

  /**
   * An entry point for AdoptionAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myAdoption)</code> and get specific assertion with code completion.
   * @param actual the Adoption we want to make assertions on.
   * @return a new <code>{@link AdoptionAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static AdoptionAssert assertThat(Adoption actual) {
    return new AdoptionAssert(actual);
  }

  /**
   * Verifies that the actual Adoption's date is equal to the given one.
   * @param date the given date to compare the actual Adoption's date to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Adoption's date is not equal to the given one.
   */
  public AdoptionAssert hasDate(java.time.LocalDate date) {
    // check that actual Adoption we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting date of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    java.time.LocalDate actualDate = actual.getDate();
    if (!Objects.areEqual(actualDate, date)) {
      failWithMessage(assertjErrorMessage, actual, date, actualDate);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Adoption's end is equal to the given one.
   * @param end the given end to compare the actual Adoption's end to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Adoption's end is not equal to the given one.
   */
  public AdoptionAssert hasEnd(java.time.LocalDate end) {
    // check that actual Adoption we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting end of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    java.time.LocalDate actualEnd = actual.getEnd();
    if (!Objects.areEqual(actualEnd, end)) {
      failWithMessage(assertjErrorMessage, actual, end, actualEnd);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Adoption's id is equal to the given one.
   * @param id the given id to compare the actual Adoption's id to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Adoption's id is not equal to the given one.
   */
  public AdoptionAssert hasId(Integer id) {
    // check that actual Adoption we want to make assertions on is not null.
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
   * Verifies that the actual Adoption is new.
   * @return this assertion object.
   * @throws AssertionError - if the actual Adoption is not new.
   */
  public AdoptionAssert isNew() {
    // check that actual Adoption we want to make assertions on is not null.
    isNotNull();

    // check that property call/field access is true
    if (!actual.isNew()) {
      failWithMessage("\nExpecting that actual Adoption is new but is not.");
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Adoption is not new.
   * @return this assertion object.
   * @throws AssertionError - if the actual Adoption is new.
   */
  public AdoptionAssert isNotNew() {
    // check that actual Adoption we want to make assertions on is not null.
    isNotNull();

    // check that property call/field access is false
    if (actual.isNew()) {
      failWithMessage("\nExpecting that actual Adoption is not new but is.");
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Adoption's owner is equal to the given one.
   * @param owner the given owner to compare the actual Adoption's owner to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Adoption's owner is not equal to the given one.
   */
  public AdoptionAssert hasOwner(org.springframework.samples.petclinic.model.Owner owner) {
    // check that actual Adoption we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting owner of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.springframework.samples.petclinic.model.Owner actualOwner = actual.getOwner();
    if (!Objects.areEqual(actualOwner, owner)) {
      failWithMessage(assertjErrorMessage, actual, owner, actualOwner);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Adoption's pet is equal to the given one.
   * @param pet the given pet to compare the actual Adoption's pet to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Adoption's pet is not equal to the given one.
   */
  public AdoptionAssert hasPet(org.springframework.samples.petclinic.model.Pet pet) {
    // check that actual Adoption we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting pet of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.springframework.samples.petclinic.model.Pet actualPet = actual.getPet();
    if (!Objects.areEqual(actualPet, pet)) {
      failWithMessage(assertjErrorMessage, actual, pet, actualPet);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Adoption is valid date.
   * @return this assertion object.
   * @throws AssertionError - if the actual Adoption is not valid date.
   */
  public AdoptionAssert isValidDate() {
    // check that actual Adoption we want to make assertions on is not null.
    isNotNull();

    // check that property call/field access is true
    if (!actual.isValidDate()) {
      failWithMessage("\nExpecting that actual Adoption is valid date but is not.");
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Adoption is not valid date.
   * @return this assertion object.
   * @throws AssertionError - if the actual Adoption is valid date.
   */
  public AdoptionAssert isNotValidDate() {
    // check that actual Adoption we want to make assertions on is not null.
    isNotNull();

    // check that property call/field access is false
    if (actual.isValidDate()) {
      failWithMessage("\nExpecting that actual Adoption is not valid date but is.");
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Adoption is valid end.
   * @return this assertion object.
   * @throws AssertionError - if the actual Adoption is not valid end.
   */
  public AdoptionAssert isValidEnd() {
    // check that actual Adoption we want to make assertions on is not null.
    isNotNull();

    // check that property call/field access is true
    if (!actual.isValidEnd()) {
      failWithMessage("\nExpecting that actual Adoption is valid end but is not.");
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Adoption is not valid end.
   * @return this assertion object.
   * @throws AssertionError - if the actual Adoption is valid end.
   */
  public AdoptionAssert isNotValidEnd() {
    // check that actual Adoption we want to make assertions on is not null.
    isNotNull();

    // check that property call/field access is false
    if (actual.isValidEnd()) {
      failWithMessage("\nExpecting that actual Adoption is not valid end but is.");
    }

    // return the current assertion for method chaining
    return this;
  }

}
