package io.library.api.domain.valueObjects;

import java.math.BigDecimal;

public record Price(BigDecimal value) {
    public Price {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
    }

    @Override
    public String toString() {
        return String.format("$%.2f", value);
    }
}
