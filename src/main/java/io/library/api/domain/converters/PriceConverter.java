package io.library.api.domain.converters;

import io.library.api.domain.valueObjects.Price;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PriceConverter implements AttributeConverter<Price, Double> {
    @Override
    public Double convertToDatabaseColumn(Price attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public Price convertToEntityAttribute(Double dbData) {
        return dbData == null ? null : new Price(dbData);
    }
}
