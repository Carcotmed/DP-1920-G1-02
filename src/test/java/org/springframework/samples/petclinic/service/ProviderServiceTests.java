package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class ProviderServiceTests {

	@Autowired
	protected ProviderService providerService;

	@Test
	@Transactional
	public void shouldInsertProviderIntoDatabaseAndGenerateId() {

		Provider provider = new Provider();
		provider.setName("Nombre de provider");
		provider.setEmail("email@email.com");
		provider.setAddress("C/ Calle Nº24");
		provider.setPhone(123456789);

		providerService.saveProvider(provider);

		Integer id = null;
		id = provider.getId();

		assertThat(id).isNotNull();
		assertThat(provider).isEqualTo(providerService.findProviderById(id));

	}

	@ParameterizedTest
	@Transactional
	@ValueSource(strings = { "aa", "", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
	public void shouldNotInsertProviderIntoDatabaseWithParametizedNames(String name) {

		Provider provider = new Provider();
		provider.setName(name);
		provider.setEmail("email@email.com");
		provider.setAddress("C/ Calle Nº24");
		provider.setPhone(123456789);

		assertThrows(ConstraintViolationException.class, () -> providerService.saveProvider(provider));
	}

	@ParameterizedTest
	@Transactional
	@ValueSource(strings = { "aaa", "aaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
	public void shouldInsertProviderIntoDatabaseWithParametizedNames(String name) {

		Provider provider = new Provider();
		provider.setName(name);
		provider.setEmail("email@email.com");
		provider.setAddress("C/ Calle Nº24");
		provider.setPhone(123456789);

		providerService.saveProvider(provider);
		
		Integer id = null;
		id = provider.getId();

		assertThat(id).isNotNull();
		assertThat(provider).isEqualTo(providerService.findProviderById(id));
	}
	
	@ParameterizedTest
	@Transactional
	@ValueSource(strings = { "email@email.com", "correo@gmail.es", "pipopipo123@outlook.co.uk" })
	public void shouldInsertProviderIntoDatabaseWithParametizedEmails(String email) {

		Provider provider = new Provider();
		provider.setName("Nombre");
		provider.setEmail(email);
		provider.setAddress("C/ Calle Nº24");
		provider.setPhone(123456789);

		providerService.saveProvider(provider);
		
		Integer id = null;
		id = provider.getId();

		assertThat(id).isNotNull();
		assertThat(provider).isEqualTo(providerService.findProviderById(id));
	}
	
	@ParameterizedTest
	@Transactional
	@ValueSource(strings = { "email", "pipooooooooooooooooooooo@", "pipo.com", "@.com", "@.", "@", ".", "123" })
	public void shouldNotInsertProviderIntoDatabaseWithParametizedEmails(String email) {

		Provider provider = new Provider();
		provider.setName("Nombre");
		provider.setEmail(email);
		provider.setAddress("C/ Calle Nº24");
		provider.setPhone(123456789);

		assertThrows(ConstraintViolationException.class, () -> providerService.saveProvider(provider));

	}
	
	@ParameterizedTest
	@Transactional
	@ValueSource(ints = { 123456789, 905246584, 123456 })
	public void shouldInsertProviderIntoDatabaseWithParametizedPhone(Integer phone) {

		Provider provider = new Provider();
		provider.setName("Nombre");
		provider.setEmail("asd@email.com");
		provider.setAddress("C/ Calle Nº24");
		provider.setPhone(123456789);

		providerService.saveProvider(provider);
		
		Integer id = null;
		id = provider.getId();

		assertThat(id).isNotNull();
		assertThat(provider).isEqualTo(providerService.findProviderById(id));
	}
	
	@ParameterizedTest
	@Transactional
	@ValueSource(ints = { -123, 1234567890, 000000000 })
	public void shouldNotInsertProviderIntoDatabaseWithParametizedPhones(Integer phone) {

		Provider provider = new Provider();
		provider.setName("Nombre");
		provider.setEmail("email@email.com");
		provider.setAddress("C/ Calle Nº24");
		provider.setPhone(phone);

		assertThrows(ConstraintViolationException.class, () -> providerService.saveProvider(provider));

	}
	
}
