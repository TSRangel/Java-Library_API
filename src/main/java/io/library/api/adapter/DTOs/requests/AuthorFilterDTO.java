package io.library.api.adapter.DTOs.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AuthorFilterDTO {
    @Schema(description = "Filter by author name")
    private String name;
    @Schema(description = "Filter by author nationality")
    private String nationality;
    @Schema(description = "Filter by author's book")
    private String title;
}
