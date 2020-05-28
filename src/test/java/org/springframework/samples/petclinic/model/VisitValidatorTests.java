package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class VisitValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@ParameterizedTest
	@ValueSource(strings = { "Name", "....", "ASD asdasdasads", "                       a", "asd",
			"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
	void shouldValidateWhenBringerParametized(String name) {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Visit visit = new Visit();

		visit.setBringer(name);
		visit.setDate(LocalDate.now());
		visit.setDescription("Description");
		visit.setPet(new Pet());

		Validator validator = createValidator();
		Set<ConstraintViolation<Visit>> constraintViolations = validator.validate(visit);

		assertThat(constraintViolations.size()).isEqualTo(0);

	}

}
