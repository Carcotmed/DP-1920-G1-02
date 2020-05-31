package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
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

class InterventionValidatorTests {
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	/*
	@ParameterizedTest
	@ValueSource(strings = { "Descripción", "aaa", "aaaaaaaaaaaaaaaaaaaaaaaaa" })
	void shouldValidateWhenDescriptionParametized(String description) {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Intervention intervention = new Intervention();
		
		intervention.setName("Name");
		intervention.setDescription(description);
		intervention.setVet(new Vet ());
		intervention.setVisit(new Visit ());
		intervention.setRequiredProducts(Arrays.asList(new Product (), new Product()));

		Validator validator = createValidator();
		Set<ConstraintViolation<Intervention>> constraintViolations = validator.validate(intervention);

		assertThat(constraintViolations.size()).isEqualTo(0);
		
	}
	
	@Test
	void shouldNotValidateWhenDescriptionBlank() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Intervention intervention = new Intervention();
		
		intervention.setName("Name");
		intervention.setDescription("    ");
		intervention.setVet(new Vet ());
		intervention.setVisit(new Visit ());
		intervention.setRequiredProducts(Arrays.asList(new Product (), new Product()));

		Validator validator = createValidator();
		Set<ConstraintViolation<Intervention>> constraintViolations = validator.validate(intervention);

		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat (constraintViolations.iterator().next().getMessage()).isEqualTo("must not be blank");
		
	}
	*/
	
	@ParameterizedTest
	@ValueSource(strings = { "Name", "....", "ASD asdasdasads", "                       a", "asd", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
	void shouldValidateWhenNameParametized(String name) {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Intervention intervention = new Intervention();
		
		intervention.setName(name);
		//intervention.setDescription("Descripcion");
		intervention.setVet(new Vet ());
		intervention.setVisit(new Visit ());
		intervention.setRequiredProducts(Arrays.asList(new Product (), new Product()));

		Validator validator = createValidator();
		Set<ConstraintViolation<Intervention>> constraintViolations = validator.validate(intervention);

		assertThat(constraintViolations.size()).isEqualTo(0);
				
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "a", "as", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
	void shouldNotValidateWhenNameParametizedByLength(String name) {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Intervention intervention = new Intervention();
		
		intervention.setName(name);
		//intervention.setDescription("Descripcion");
		intervention.setVet(new Vet ());
		intervention.setVisit(new Visit ());
		intervention.setRequiredProducts(Arrays.asList(new Product (), new Product()));

		Validator validator = createValidator();
		Set<ConstraintViolation<Intervention>> constraintViolations = validator.validate(intervention);

		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat (constraintViolations.iterator().next().getMessage()).isEqualTo("size must be between 3 and 50");
		
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "                       ", "   " })
	void shouldNotValidateWhenNameParametizedByEmpty(String name) {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Intervention intervention = new Intervention();
		
		intervention.setName(name);
		//intervention.setDescription("Descripcion");
		intervention.setVet(new Vet ());
		intervention.setVisit(new Visit ());
		intervention.setRequiredProducts(Arrays.asList(new Product (), new Product()));

		Validator validator = createValidator();
		Set<ConstraintViolation<Intervention>> constraintViolations = validator.validate(intervention);


		assertThat(constraintViolations.size()).isGreaterThanOrEqualTo(1);
		assertThat (constraintViolations.iterator().next().getMessage()).isEqualTo("must not be blank");
		
	}
	
	@Test
	void shouldValidateWhenRequiredProductsEmpty() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Intervention intervention = new Intervention();
		
		intervention.setName("Name");
		//intervention.setDescription("Description");
		intervention.setVet(new Vet ());
		intervention.setVisit(new Visit ());
		intervention.setRequiredProducts(new ArrayList <> ());

		Validator validator = createValidator();
		Set<ConstraintViolation<Intervention>> constraintViolations = validator.validate(intervention);

		assertThat(constraintViolations.size()).isEqualTo(0);
		
	}
	
	@Test
	void shouldNotValidateWhenRequiredProductsNull() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Intervention intervention = new Intervention();
		
		intervention.setName("Name");
		//intervention.setDescription("Description");
		intervention.setVet(new Vet ());
		intervention.setVisit(new Visit ());
		intervention.setRequiredProducts(null);

		Validator validator = createValidator();
		Set<ConstraintViolation<Intervention>> constraintViolations = validator.validate(intervention);

		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat (constraintViolations.iterator().next().getMessage()).isEqualTo("must not be null");

		
	}
	
	
	

}
