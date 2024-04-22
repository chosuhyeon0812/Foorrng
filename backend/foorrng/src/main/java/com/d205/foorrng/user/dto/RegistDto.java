package com.d205.foorrng.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegistDto {

    @NotEmpty
    @Size(min = 10, max = 10)
    private String businessNumber;

}
