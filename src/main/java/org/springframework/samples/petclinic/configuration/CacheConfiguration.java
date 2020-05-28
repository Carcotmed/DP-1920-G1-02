package org.springframework.samples.petclinic.configuration;

import java.lang.reflect.Method;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.model.Intervention;

@Configuration
@EnableCaching
public class CacheConfiguration {

	@Bean("customKeyGenerator")
	public KeyGenerator keyGenerator() {
		return new CustomKeyGenerator();
	}

	public class CustomKeyGenerator implements KeyGenerator {

		public Object generate(Object target, Method method, Object... params) {
			return  ((Intervention) params [0]).getVisit().getDate();
		}
	}

}
