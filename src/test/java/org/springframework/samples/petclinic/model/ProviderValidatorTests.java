package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ProviderValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	// --------------------- Name ---------------------

	@ParameterizedTest
	@ValueSource(strings = { "Nombre", "asd", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
	void shouldValidateWhenNameParametized(String name) {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Provider provider = new Provider();

		provider.setName(name);
		provider.setAddress("Address");
		provider.setEmail("email@email.com");
		provider.setPhone("987654321");

		Validator validator = createValidator();
		Set<ConstraintViolation<Provider>> constraintViolations = validator.validate(provider);

		assertThat(constraintViolations.size()).isEqualTo(0);

	}

	@ParameterizedTest
	@ValueSource(strings = { "         ", "    " })
	void shouldNotValidateWhenNameEmptyParametized(String name) {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Provider provider = new Provider();

		provider.setName(name);
		provider.setAddress("Address");
		provider.setEmail("email@email.com");
		provider.setPhone("987654321");

		Validator validator = createValidator();
		Set<ConstraintViolation<Provider>> constraintViolations = validator.validate(provider);

		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("must not be blank");

	}

	@ParameterizedTest
	@ValueSource(strings = { "a", "as", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
	void shouldNotValidateWhenNameLengthWrongParametized(String name) {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Provider provider = new Provider();

		provider.setName(name);
		provider.setAddress("Address");
		provider.setEmail("email@email.com");
		provider.setPhone("987654321");

		Validator validator = createValidator();
		Set<ConstraintViolation<Provider>> constraintViolations = validator.validate(provider);

		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("size must be between 3 and 50");

	}

	// --------------------- Phone --------------------

	@ParameterizedTest
	@Transactional
	@ValueSource(strings = { "123456789", "905246584", "000000000", "999999999" })
	public void shouldValidateWithParametizedPhone(String phone) {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Provider provider = new Provider();

		provider.setName("Name");
		provider.setAddress("Address");
		provider.setEmail("email@email.com");

		provider.setPhone(phone);

		Validator validator = createValidator();
		Set<ConstraintViolation<Provider>> constraintViolations = validator.validate(provider);

		assertThat(constraintViolations.size()).isEqualTo(0);
	}

	@ParameterizedTest
	@Transactional
	@ValueSource(strings = { "-123", "0,123456789", "-12,52", "12,5" })
	public void shouldNotValidateWithParametizedPhones(String phone) {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Provider provider = new Provider();

		provider.setName("Name");
		provider.setAddress("Address");
		provider.setEmail("email@email.com");

		provider.setPhone(phone);

		Validator validator = createValidator();
		Set<ConstraintViolation<Provider>> constraintViolations = validator.validate(provider);

		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage())
				.isEqualTo("must match \"^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$\"");

	}

	// --------------------- Email --------------------
	
	@ParameterizedTest
	@Transactional
	@ValueSource(strings = { "email@email.com", "correo@gmail.es", "pipopipo123@outlook.co.uk" })
	public void shouldValidateWithParametizedEmails(String email) {

		Provider provider = new Provider();
		provider.setName("Nombre");
		provider.setEmail(email);
		provider.setAddress("C/ Calle Nº24");
		provider.setPhone("123456789");

		Validator validator = createValidator();
		Set<ConstraintViolation<Provider>> constraintViolations = validator.validate(provider);

		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@Test
	@Transactional
	public void shouldNotValidateWithEmptyEmail() {

		Provider provider = new Provider();
		provider.setName("Nombre");
		provider.setEmail("");
		provider.setAddress("C/ Calle Nº24");
		provider.setPhone("123456789");

		Validator validator = createValidator();
		Set<ConstraintViolation<Provider>> constraintViolations = validator.validate(provider);

		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage())
				.isEqualTo("must not be empty");
	}
	
	@ParameterizedTest
	@Transactional
	@ValueSource(strings = { " ", "email", "pipooooooooooooooooooooo@", "pipo.com", "@.com", "@.", "@", ".", "123" })
	public void shouldNotValidateWithParametizedBadEmails(String email) {

		Provider provider = new Provider();
		provider.setName("Nombre");
		provider.setEmail(email);
		provider.setAddress("C/ Calle Nº24");
		provider.setPhone("123456789");

		Validator validator = createValidator();
		Set<ConstraintViolation<Provider>> constraintViolations = validator.validate(provider);

		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage())
				.isEqualTo("must be a well-formed email address");
	}

}
