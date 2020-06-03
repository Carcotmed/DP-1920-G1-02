package generatedAssertions.customAssertions;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.internal.Iterables;
import org.assertj.core.util.Objects;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Visit;

/**
 * {@link Pet} specific assertions - Generated by CustomAssertionGenerator.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class PetAssert extends AbstractObjectAssert<PetAssert, Pet> {

  /**
   * Creates a new <code>{@link PetAssert}</code> to make assertions on actual Pet.
   * @param actual the Pet we want to make assertions on.
   */
  public PetAssert(Pet actual) {
    super(actual, PetAssert.class);
  }

  /**
   * An entry point for PetAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myPet)</code> and get specific assertion with code completion.
   * @param actual the Pet we want to make assertions on.
   * @return a new <code>{@link PetAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static PetAssert assertThat(Pet actual) {
    return new PetAssert(actual);
  }

  /**
   * Verifies that the actual Pet's birthDate is equal to the given one.
   * @param birthDate the given birthDate to compare the actual Pet's birthDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Pet's birthDate is not equal to the given one.
   */
  public PetAssert hasBirthDate(java.time.LocalDate birthDate) {
    // check that actual Pet we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting birthDate of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    java.time.LocalDate actualBirthDate = actual.getBirthDate();
    if (!Objects.areEqual(actualBirthDate, birthDate)) {
      failWithMessage(assertjErrorMessage, actual, birthDate, actualBirthDate);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Pet's id is equal to the given one.
   * @param id the given id to compare the actual Pet's id to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Pet's id is not equal to the given one.
   */
  public PetAssert hasId(Integer id) {
    // check that actual Pet we want to make assertions on is not null.
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
   * Verifies that the actual Pet's imageDeleteHash is equal to the given one.
   * @param imageDeleteHash the given imageDeleteHash to compare the actual Pet's imageDeleteHash to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Pet's imageDeleteHash is not equal to the given one.
   */
  public PetAssert hasImageDeleteHash(String imageDeleteHash) {
    // check that actual Pet we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting imageDeleteHash of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualImageDeleteHash = actual.getImageDeleteHash();
    if (!Objects.areEqual(actualImageDeleteHash, imageDeleteHash)) {
      failWithMessage(assertjErrorMessage, actual, imageDeleteHash, actualImageDeleteHash);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Pet's imageURL is equal to the given one.
   * @param imageURL the given imageURL to compare the actual Pet's imageURL to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Pet's imageURL is not equal to the given one.
   */
  public PetAssert hasImageURL(String imageURL) {
    // check that actual Pet we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting imageURL of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualImageURL = actual.getImageURL();
    if (!Objects.areEqual(actualImageURL, imageURL)) {
      failWithMessage(assertjErrorMessage, actual, imageURL, actualImageURL);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Pet's name is equal to the given one.
   * @param name the given name to compare the actual Pet's name to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Pet's name is not equal to the given one.
   */
  public PetAssert hasName(String name) {
    // check that actual Pet we want to make assertions on is not null.
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
   * Verifies that the actual Pet is new.
   * @return this assertion object.
   * @throws AssertionError - if the actual Pet is not new.
   */
  public PetAssert isNew() {
    // check that actual Pet we want to make assertions on is not null.
    isNotNull();

    // check that property call/field access is true
    if (!actual.isNew()) {
      failWithMessage("\nExpecting that actual Pet is new but is not.");
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Pet is not new.
   * @return this assertion object.
   * @throws AssertionError - if the actual Pet is new.
   */
  public PetAssert isNotNew() {
    // check that actual Pet we want to make assertions on is not null.
    isNotNull();

    // check that property call/field access is false
    if (actual.isNew()) {
      failWithMessage("\nExpecting that actual Pet is not new but is.");
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Pet's owner is equal to the given one.
   * @param owner the given owner to compare the actual Pet's owner to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Pet's owner is not equal to the given one.
   */
  public PetAssert hasOwner(org.springframework.samples.petclinic.model.Owner owner) {
    // check that actual Pet we want to make assertions on is not null.
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
   * Verifies that the actual Pet's type is equal to the given one.
   * @param type the given type to compare the actual Pet's type to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Pet's type is not equal to the given one.
   */
  public PetAssert hasType(org.springframework.samples.petclinic.model.PetType type) {
    // check that actual Pet we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting type of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.springframework.samples.petclinic.model.PetType actualType = actual.getType();
    if (!Objects.areEqual(actualType, type)) {
      failWithMessage(assertjErrorMessage, actual, type, actualType);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Pet's visits contains the given Visit elements.
   * @param visits the given elements that should be contained in actual Pet's visits.
   * @return this assertion object.
   * @throws AssertionError if the actual Pet's visits does not contain all given Visit elements.
   */
  public PetAssert hasVisits(Visit... visits) {
    // check that actual Pet we want to make assertions on is not null.
    isNotNull();

    // check that given Visit varargs is not null.
    if (visits == null) failWithMessage("Expecting visits parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getVisits(), visits);

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Pet's visits contains the given Visit elements in Collection.
   * @param visits the given elements that should be contained in actual Pet's visits.
   * @return this assertion object.
   * @throws AssertionError if the actual Pet's visits does not contain all given Visit elements.
   */
  public PetAssert hasVisits(java.util.Collection<? extends Visit> visits) {
    // check that actual Pet we want to make assertions on is not null.
    isNotNull();

    // check that given Visit collection is not null.
    if (visits == null) {
      failWithMessage("Expecting visits parameter not to be null.");
      return this; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getVisits(), visits.toArray());

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Pet's visits contains <b>only</b> the given Visit elements and nothing else in whatever order.
   * @param visits the given elements that should be contained in actual Pet's visits.
   * @return this assertion object.
   * @throws AssertionError if the actual Pet's visits does not contain all given Visit elements.
   */
  public PetAssert hasOnlyVisits(Visit... visits) {
    // check that actual Pet we want to make assertions on is not null.
    isNotNull();

    // check that given Visit varargs is not null.
    if (visits == null) failWithMessage("Expecting visits parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getVisits(), visits);

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Pet's visits contains <b>only</b> the given Visit elements in Collection and nothing else in whatever order.
   * @param visits the given elements that should be contained in actual Pet's visits.
   * @return this assertion object.
   * @throws AssertionError if the actual Pet's visits does not contain all given Visit elements.
   */
  public PetAssert hasOnlyVisits(java.util.Collection<? extends Visit> visits) {
    // check that actual Pet we want to make assertions on is not null.
    isNotNull();

    // check that given Visit collection is not null.
    if (visits == null) {
      failWithMessage("Expecting visits parameter not to be null.");
      return this; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getVisits(), visits.toArray());

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Pet's visits does not contain the given Visit elements.
   *
   * @param visits the given elements that should not be in actual Pet's visits.
   * @return this assertion object.
   * @throws AssertionError if the actual Pet's visits contains any given Visit elements.
   */
  public PetAssert doesNotHaveVisits(Visit... visits) {
    // check that actual Pet we want to make assertions on is not null.
    isNotNull();

    // check that given Visit varargs is not null.
    if (visits == null) failWithMessage("Expecting visits parameter not to be null.");

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getVisits(), visits);

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Pet's visits does not contain the given Visit elements in Collection.
   *
   * @param visits the given elements that should not be in actual Pet's visits.
   * @return this assertion object.
   * @throws AssertionError if the actual Pet's visits contains any given Visit elements.
   */
  public PetAssert doesNotHaveVisits(java.util.Collection<? extends Visit> visits) {
    // check that actual Pet we want to make assertions on is not null.
    isNotNull();

    // check that given Visit collection is not null.
    if (visits == null) {
      failWithMessage("Expecting visits parameter not to be null.");
      return this; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getVisits(), visits.toArray());

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Pet has no visits.
   * @return this assertion object.
   * @throws AssertionError if the actual Pet's visits is not empty.
   */
  public PetAssert hasNoVisits() {
    // check that actual Pet we want to make assertions on is not null.
    isNotNull();

    // we override the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting :\n  <%s>\nnot to have visits but had :\n  <%s>";

    // check
    if (actual.getVisits().iterator().hasNext()) {
      failWithMessage(assertjErrorMessage, actual, actual.getVisits());
    }

    // return the current assertion for method chaining
    return this;
  }


}