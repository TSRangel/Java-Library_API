package io.library.api.domain.valueObjects;

import java.util.regex.Pattern;

public record ISBN(String value) {
    public ISBN(String value) {
        String regex =  "^(?=(?:\\D*\\d){10}(?:\\D*\\d{3})?$)(?:ISBN(?:-1[03])?:?\\s*)?(?=[0-9X]{10}$|" +
                "(?=(?:[0-9]+[-\\s]){3})[-\\s0-9X]{13}$" +
                "|97[89][0-9]{10}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";
        if(!Pattern.matches(regex, value)) {
            throw new IllegalArgumentException("O ISBN informado não é válido.");
        }

        this.value = value.replaceAll("[^0-9X]", "");
    }
}
