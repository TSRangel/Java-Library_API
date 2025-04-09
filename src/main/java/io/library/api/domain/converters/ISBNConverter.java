package io.library.api.domain.converters;

import io.library.api.domain.valueObjects.ISBN;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ISBNConverter implements AttributeConverter<ISBN, String> {
    @Override
    public String convertToDatabaseColumn(ISBN attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public ISBN convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new ISBN(dbData);
    }
}
