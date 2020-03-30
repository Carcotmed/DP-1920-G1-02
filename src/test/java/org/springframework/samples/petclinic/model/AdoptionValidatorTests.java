
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @author Michael Isvy Simple test to make sure that Bean Validation is working (useful
 *         when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
class AdoptionValidatorTests {

	private static Owner		owner1;

	private static final int	TEST_OWNER_ID1	= 1;

	private static Pet			pet1;

	private static final int	TEST_PET_ID1	= 1;

	@MockBean
	private OwnerService		ownerService;


	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@BeforeAll
	static void setup() {

		AdoptionValidatorTests.owner1 = new Owner();
		AdoptionValidatorTests.owner1.setId(AdoptionValidatorTests.TEST_OWNER_ID1);
		AdoptionValidatorTests.owner1.setFirstName("George");
		AdoptionValidatorTests.owner1.setLastName("Franklin");
		AdoptionValidatorTests.owner1.setAddress("110 W. Liberty St.");
		AdoptionValidatorTests.owner1.setCity("Madison");
		AdoptionValidatorTests.owner1.setTelephone("6085551023");

		AdoptionValidatorTests.pet1 = new Pet();
		AdoptionValidatorTests.pet1.setBirthDate(LocalDate.now().minusMonths(4));
		AdoptionValidatorTests.pet1.setId(AdoptionValidatorTests.TEST_PET_ID1);
		AdoptionValidatorTests.pet1.setName("name");
		PetType type = new PetType();
		type.setName("dog");
		type.setId(1);
		AdoptionValidatorTests.pet1.setType(type);

	}
	@Test
	void shouldValidateWhenAllCorrect() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Adoption adoption = new Adoption();
		adoption.setDate(LocalDate.now().plusDays(3));
		adoption.setEnd(LocalDate.now().plusDays(12));
		adoption.setOwner(AdoptionValidatorTests.owner1);
		adoption.setPet(AdoptionValidatorTests.pet1);
		Validator validator = this.createValidator();
		Set<ConstraintViolation<Adoption>> constraintViolations = validator.validate(adoption);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);
	}

	@Test
	void shouldNotValidateWhenAllEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Adoption adoption = new Adoption();
		adoption.setDate(LocalDate.now().plusDays(3));
		adoption.setEnd(LocalDate.now().plusDays(12));
		adoption.setPet(AdoptionValidatorTests.pet1);
		Validator validator = this.createValidator();
		Set<ConstraintViolation<Adoption>> constraintViolations = validator.validate(adoption);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@Test
	void shouldNotValidateWhenPastDate() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Adoption adoption = new Adoption();
		adoption.setDate(LocalDate.now().minusDays(3));
		adoption.setEnd(LocalDate.now().plusDays(12));
		adoption.setOwner(AdoptionValidatorTests.owner1);
		adoption.setPet(AdoptionValidatorTests.pet1);
		Validator validator = this.createValidator();
		Set<ConstraintViolation<Adoption>> constraintViolations = validator.validate(adoption);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Adoption> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("date");
	}

	@Test
	void shouldNotValidateWhenPastEnd() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Adoption adoption = new Adoption();
		adoption.setDate(LocalDate.now().plusDays(3));
		adoption.setEnd(LocalDate.now().minusDays(12));
		adoption.setOwner(AdoptionValidatorTests.owner1);
		adoption.setPet(AdoptionValidatorTests.pet1);
		Validator validator = this.createValidator();
		Set<ConstraintViolation<Adoption>> constraintViolations = validator.validate(adoption);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Adoption> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("end");
	}

}
