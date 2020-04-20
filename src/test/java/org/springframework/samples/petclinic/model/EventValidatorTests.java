
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @author Michael Isvy Simple test to make sure that Bean Validation is working (useful
 *         when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
class EventValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldValidateWhenAllCorrect() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Event event = new Event();
		event.setCapacity(20);
		event.setDate(LocalDate.now().plusDays(2));
		event.setDescription("Description");
		event.setPlace("Place");
		event.setPublished(false);
		event.setSponsor(new Provider());

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Event>> constraintViolations = validator.validate(event);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);
	}

	@Test
	void shouldValidateWhenAllEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Event event = new Event();

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Event>> constraintViolations = validator.validate(event);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);
	}

	@Test
	void shouldNotValidateWhenPastDate() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Event event = new Event();
		event.setCapacity(20);
		event.setDescription("Description");
		event.setPlace("Place");
		event.setPublished(true);
		event.setDate(LocalDate.of(2000, 1, 1));

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Event>> constraintViolations = validator.validate(event);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Event> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("date");
	}

}
