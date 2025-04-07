package io.library.api.domain.valueObjects;

public record Price(double value) {
    public Price {
        if (value < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
    }

    @Override
    public String toString() {
        return String.format("$%.2f", value);
    }
}
