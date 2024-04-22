package com.d205.foorrng.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import javax.management.relation.Role;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    private long userUid;

    @NotEmpty
    @Size(min = 1, max = 20)
    private String name;

    @NotNull
    @Email
    private String email;

}
