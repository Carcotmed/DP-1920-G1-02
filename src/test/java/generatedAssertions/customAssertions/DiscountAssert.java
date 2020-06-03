package generatedAssertions.customAssertions;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Objects;
import org.springframework.samples.petclinic.model.Discount;

/**
 * {@link Discount} specific assertions - Generated by CustomAssertionGenerator.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class DiscountAssert extends AbstractObjectAssert<DiscountAssert, Discount> {

  /**
   * Creates a new <code>{@link DiscountAssert}</code> to make assertions on actual Discount.
   * @param actual the Discount we want to make assertions on.
   */
  public DiscountAssert(Discount actual) {
    super(actual, DiscountAssert.class);
  }

  /**
   * An entry point for DiscountAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myDiscount)</code> and get specific assertion with code completion.
   * @param actual the Discount we want to make assertions on.
   * @return a new <code>{@link DiscountAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static DiscountAssert assertThat(Discount actual) {
    return new DiscountAssert(actual);
  }

  /**
   * Verifies that the actual Discount's enabled is equal to the given one.
   * @param enabled the given enabled to compare the actual Discount's enabled to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Discount's enabled is not equal to the given one.
   */
  public DiscountAssert hasEnabled(Boolean enabled) {
    // check that actual Discount we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting enabled of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Boolean actualEnabled = actual.getEnabled();
    if (!Objects.areEqual(actualEnabled, enabled)) {
      failWithMessage(assertjErrorMessage, actual, enabled, actualEnabled);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Discount's id is equal to the given one.
   * @param id the given id to compare the actual Discount's id to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Discount's id is not equal to the given one.
   */
  public DiscountAssert hasId(Integer id) {
    // check that actual Discount we want to make assertions on is not null.
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
   * Verifies that the actual Discount is new.
   * @return this assertion object.
   * @throws AssertionError - if the actual Discount is not new.
   */
  public DiscountAssert isNew() {
    // check that actual Discount we want to make assertions on is not null.
    isNotNull();

    // check that property call/field access is true
    if (!actual.isNew()) {
      failWithMessage("\nExpecting that actual Discount is new but is not.");
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Discount is not new.
   * @return this assertion object.
   * @throws AssertionError - if the actual Discount is new.
   */
  public DiscountAssert isNotNew() {
    // check that actual Discount we want to make assertions on is not null.
    isNotNull();

    // check that property call/field access is false
    if (actual.isNew()) {
      failWithMessage("\nExpecting that actual Discount is not new but is.");
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Discount's percentage is equal to the given one.
   * @param percentage the given percentage to compare the actual Discount's percentage to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Discount's percentage is not equal to the given one.
   */
  public DiscountAssert hasPercentage(Double percentage) {
    // check that actual Discount we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting percentage of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Double actualPercentage = actual.getPercentage();
    if (!Objects.areEqual(actualPercentage, percentage)) {
      failWithMessage(assertjErrorMessage, actual, percentage, actualPercentage);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Discount's percentage is close to the given value by less than the given offset.
   * <p>
   * If difference is equal to the offset value, assertion is considered successful.
   * @param percentage the value to compare the actual Discount's percentage to.
   * @param assertjOffset the given offset.
   * @return this assertion object.
   * @throws AssertionError - if the actual Discount's percentage is not close enough to the given value.
   */
  public DiscountAssert hasPercentageCloseTo(Double percentage, Double assertjOffset) {
    // check that actual Discount we want to make assertions on is not null.
    isNotNull();

    Double actualPercentage = actual.getPercentage();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = String.format("\nExpecting percentage:\n  <%s>\nto be close to:\n  <%s>\nby less than <%s> but difference was <%s>",
                                               actualPercentage, percentage, assertjOffset, Math.abs(percentage - actualPercentage));

    // check
    Assertions.assertThat(actualPercentage).overridingErrorMessage(assertjErrorMessage).isCloseTo(percentage, Assertions.within(assertjOffset));

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Discount's product is equal to the given one.
   * @param product the given product to compare the actual Discount's product to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Discount's product is not equal to the given one.
   */
  public DiscountAssert hasProduct(org.springframework.samples.petclinic.model.Product product) {
    // check that actual Discount we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting product of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.springframework.samples.petclinic.model.Product actualProduct = actual.getProduct();
    if (!Objects.areEqual(actualProduct, product)) {
      failWithMessage(assertjErrorMessage, actual, product, actualProduct);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Discount's provider is equal to the given one.
   * @param provider the given provider to compare the actual Discount's provider to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Discount's provider is not equal to the given one.
   */
  public DiscountAssert hasProvider(org.springframework.samples.petclinic.model.Provider provider) {
    // check that actual Discount we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting provider of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.springframework.samples.petclinic.model.Provider actualProvider = actual.getProvider();
    if (!Objects.areEqual(actualProvider, provider)) {
      failWithMessage(assertjErrorMessage, actual, provider, actualProvider);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Discount's quantity is equal to the given one.
   * @param quantity the given quantity to compare the actual Discount's quantity to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Discount's quantity is not equal to the given one.
   */
  public DiscountAssert hasQuantity(Integer quantity) {
    // check that actual Discount we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting quantity of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Integer actualQuantity = actual.getQuantity();
    if (!Objects.areEqual(actualQuantity, quantity)) {
      failWithMessage(assertjErrorMessage, actual, quantity, actualQuantity);
    }

    // return the current assertion for method chaining
    return this;
  }

}
