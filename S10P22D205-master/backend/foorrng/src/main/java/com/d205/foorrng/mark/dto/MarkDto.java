package com.d205.foorrng.mark.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MarkDto {

    @NotEmpty
    @Size(max = 50)
    private String address;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}
