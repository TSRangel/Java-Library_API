package io.library.api.domain.valueObjects;

import java.util.regex.Pattern;

public record ISBN(String isbn) {
    public ISBN {
        String regex =  "^(?:ISBN(?:-13)?:? )?(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]$"
                + "|^(?:ISBN(?:-10)?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$)[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";
        if(Pattern.matches(regex, isbn)) {
            throw new IllegalArgumentException("O ISBN informado não é válido.");
        }

    }
}
