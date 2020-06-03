package generatedAssertions.org.assertj;

import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.model.api.*;

import generatedAssertions.customAssertions.*;

/**
 * Entry point for assertions of different data types. Each method in this class is a static factory for the
 * type-specific assertion objects.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class Assertions {

  /**
   * Creates a new instance of <code>{@link AdoptionAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static AdoptionAssert assertThat(Adoption actual) {
    return new AdoptionAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link AuthoritiesAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static AuthoritiesAssert assertThat(Authorities actual) {
    return new AuthoritiesAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link BaseEntityAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static BaseEntityAssert assertThat(BaseEntity actual) {
    return new BaseEntityAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link DiscountAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static DiscountAssert assertThat(Discount actual) {
    return new DiscountAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link EventAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static EventAssert assertThat(Event actual) {
    return new EventAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link InterventionAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static InterventionAssert assertThat(Intervention actual) {
    return new InterventionAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link NamedEntityAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static NamedEntityAssert assertThat(NamedEntity actual) {
    return new NamedEntityAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link OrderAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static OrderAssert assertThat(Order actual) {
    return new OrderAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link OwnerAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static OwnerAssert assertThat(Owner actual) {
    return new OwnerAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link ParticipationAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static ParticipationAssert assertThat(Participation actual) {
    return new ParticipationAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link PersonAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static PersonAssert assertThat(Person actual) {
    return new PersonAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link PetAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static PetAssert assertThat(Pet actual) {
    return new PetAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link PetTypeAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static PetTypeAssert assertThat(PetType actual) {
    return new PetTypeAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link ProductAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static ProductAssert assertThat(Product actual) {
    return new ProductAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link ProviderAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static ProviderAssert assertThat(Provider actual) {
    return new ProviderAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link SpecialtyAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static SpecialtyAssert assertThat(Specialty actual) {
    return new SpecialtyAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link UserAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static UserAssert assertThat(User actual) {
    return new UserAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link VetAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static VetAssert assertThat(Vet actual) {
    return new VetAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link VetsAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static VetsAssert assertThat(Vets actual) {
    return new VetsAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link VisitAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static VisitAssert assertThat(Visit actual) {
    return new VisitAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link api.DataAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static DataAssert assertThat(Data actual) {
    return new DataAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link api.ImgurResponseAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static ImgurResponseAssert assertThat(ImgurResponse actual) {
    return new ImgurResponseAssert(actual);
  }

  /**
   * Creates a new <code>{@link Assertions}</code>.
   */
  protected Assertions() {
    // empty
  }
}
