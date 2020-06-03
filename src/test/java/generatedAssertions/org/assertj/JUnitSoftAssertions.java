package generatedAssertions.org.assertj;

import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.model.api.*;

import generatedAssertions.customAssertions.*;

/**
 * Like {@link SoftAssertions} but as a junit rule that takes care of calling
 * {@link SoftAssertions#assertAll() assertAll()} at the end of each test.
 * <p>
 * Example:
 * <pre><code class='java'> public class SoftlyTest {
 *
 *     &#064;Rule
 *     public final JUnitBDDSoftAssertions softly = new JUnitBDDSoftAssertions();
 *
 *     &#064;Test
 *     public void soft_bdd_assertions() throws Exception {
 *       softly.assertThat(1).isEqualTo(2);
 *       softly.assertThat(Lists.newArrayList(1, 2)).containsOnly(1, 2);
 *       // no need to call assertAll(), this is done automatically.
 *     }
 *  }</code></pre>
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class JUnitSoftAssertions extends org.assertj.core.api.JUnitSoftAssertions {

  /**
   * Creates a new "soft" instance of <code>{@link AdoptionAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public AdoptionAssert assertThat(Adoption actual) {
    return proxy(AdoptionAssert.class, Adoption.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link AuthoritiesAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public AuthoritiesAssert assertThat(Authorities actual) {
    return proxy(AuthoritiesAssert.class, Authorities.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link BaseEntityAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public BaseEntityAssert assertThat(BaseEntity actual) {
    return proxy(BaseEntityAssert.class, BaseEntity.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link DiscountAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public DiscountAssert assertThat(Discount actual) {
    return proxy(DiscountAssert.class, Discount.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link EventAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public EventAssert assertThat(Event actual) {
    return proxy(EventAssert.class, Event.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link InterventionAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public InterventionAssert assertThat(Intervention actual) {
    return proxy(InterventionAssert.class, Intervention.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link NamedEntityAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public NamedEntityAssert assertThat(NamedEntity actual) {
    return proxy(NamedEntityAssert.class, NamedEntity.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link OrderAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public OrderAssert assertThat(Order actual) {
    return proxy(OrderAssert.class, Order.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link OwnerAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public OwnerAssert assertThat(Owner actual) {
    return proxy(OwnerAssert.class, Owner.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link ParticipationAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public ParticipationAssert assertThat(Participation actual) {
    return proxy(ParticipationAssert.class, Participation.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link PersonAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public PersonAssert assertThat(Person actual) {
    return proxy(PersonAssert.class, Person.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link PetAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public PetAssert assertThat(Pet actual) {
    return proxy(PetAssert.class, Pet.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link PetTypeAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public PetTypeAssert assertThat(PetType actual) {
    return proxy(PetTypeAssert.class, PetType.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link ProductAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public ProductAssert assertThat(Product actual) {
    return proxy(ProductAssert.class, Product.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link ProviderAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public ProviderAssert assertThat(Provider actual) {
    return proxy(ProviderAssert.class, Provider.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link SpecialtyAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public SpecialtyAssert assertThat(Specialty actual) {
    return proxy(SpecialtyAssert.class, Specialty.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link UserAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public UserAssert assertThat(User actual) {
    return proxy(UserAssert.class, User.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link VetAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public VetAssert assertThat(Vet actual) {
    return proxy(VetAssert.class, Vet.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link VetsAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public VetsAssert assertThat(Vets actual) {
    return proxy(VetsAssert.class, Vets.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link VisitAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public VisitAssert assertThat(Visit actual) {
    return proxy(VisitAssert.class, Visit.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link api.DataAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public DataAssert assertThat(Data actual) {
    return proxy(DataAssert.class, Data.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link api.ImgurResponseAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public ImgurResponseAssert assertThat(ImgurResponse actual) {
    return proxy(ImgurResponseAssert.class, ImgurResponse.class, actual);
  }

}
