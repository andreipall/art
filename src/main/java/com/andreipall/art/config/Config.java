package com.andreipall.art.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.slugify.Slugify;

@Configuration
public class Config {
	@Bean
	public Slugify getSlugify() {
		return new Slugify();
	}
}
