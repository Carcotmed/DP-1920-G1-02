package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

class ProviderServiceTests {

	@Autowired
	protected ProviderService providerService;

	Provider provider;

	@BeforeEach
	void instantiateProvider() {

		provider = new Provider();
		provider.setName("Nombre de provider");
		provider.setEmail("email@email.com");
		provider.setAddress("C/ Calle Nº24");
		provider.setPhone("123456789");

	}

	@AfterEach
	void recreateProvider() {

		providerService.deleteProvider(provider);

		provider = null;

	}

	// ----------------- Find Providers -----------------------
	
	@Test
	void shouldFindAllProviders() {
		
		providerService.saveProvider(provider);
		
		Collection<Provider> providers = this.providerService.findProviders();
		
		assertThat(providers.size()).isGreaterThan(1);
		assertThat (providers).contains(provider);
		
	}

	// ----------------- Save Provider -------------------------

	@Test
	@Transactional
	public void shouldInsertProviderIntoDatabaseAndGenerateId() {

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

		provider.setName(name);

		assertThrows(ConstraintViolationException.class, () -> providerService.saveProvider(provider));
	}

	@ParameterizedTest
	@Transactional
	@ValueSource(strings = { "aaa", "aaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
	public void shouldInsertProviderIntoDatabaseWithParametizedNames(String name) {

		provider.setName(name);

		providerService.saveProvider(provider);

		Integer id = null;
		id = provider.getId();

		assertThat(id).isNotNull();
		assertThat(provider).isEqualTo(providerService.findProviderById(id));
	}

	// ----------------- Find Provider By Id --------------------
	
	@Test
	void shouldFindProviderWithCorrectId() {
		
		providerService.saveProvider(provider);

		Integer id = null;
		id = provider.getId();
		
		assertThat(id).isNotNull();
		Provider providerFound = this.providerService.findProviderById(id);
		assertThat (providerFound).isNotNull();
		assertThat(providerFound).isEqualTo(provider);

	}
	
	@Test
	void shouldNotFindProviderWithWrongId() {
		
		providerService.saveProvider(provider);

		Integer id = null;
		id = provider.getId();
		
		assertThat(id).isNotNull();
		Provider providerFound = this.providerService.findProviderById(999);
		assertThat (providerFound).isNull();
		assertThat(providerFound).isNotEqualTo(provider);

	}

	// ----------------- Delete Provider -----------------------
	
	@Test
	void shouldDeleteProvider() {
		
		providerService.saveProvider(provider);

		Integer id = null;
		id = provider.getId();

		Provider providerFound = this.providerService.findProviderById(id);
		assertThat (providerFound).isNotNull();
		
		providerService.deleteProvider(providerFound);

		assertThat(providerService.findProviderById(id)).isNull();
	}

}
