package io.library.api.domain.enums;

public enum Role {
    USER(1),
    ADMIN(2);
    private final int code;
    Role(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public Role fromCode(int code) {
        for (Role role : Role.values()) {
            if (role.getCode() == code) {
                return role;
            }
        }

        throw new IllegalArgumentException("Invalid role code: " + code);
    }
}
