package io.library.api.adapter.DTOs.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AuthorFilterDTO {
    private String name;
    private String nationality;
    private String title;
}
