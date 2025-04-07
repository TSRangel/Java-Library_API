package io.library.api.domain.enums;

public enum Genre {
    FICTION(0),
    NON_FICTION(1),
    SCIENCE(2),
    HISTORY(3),
    FANTASY(4),
    MYSTERY(5),
    BIOGRAPHY(6),
    ROMANCE(7),
    THRILLER(8),
    CHILDREN(9);
    private final int code;

    Genre(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static Genre fromCode(int code) {
        for (Genre genre : Genre.values()) {
            if (genre.getCode() == code) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Código inválido: " + code);
    }
}
