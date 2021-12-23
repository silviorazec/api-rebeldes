package br.com.letsgo.rebeldes.utils;

import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.stereotype.Component;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.Schema;

@Component
public class CustomHidde implements PropertyCustomizer {

	@Override
	public Schema customize(Schema property, AnnotatedType type) {
		if ("position".equals(type.getPropertyName()))
			return null;
		return property;
	}


}