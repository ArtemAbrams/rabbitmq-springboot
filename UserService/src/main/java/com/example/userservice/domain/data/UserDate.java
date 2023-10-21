package com.example.userservice.domain.data;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDate {
    @Nonnull
    private String firstname;
    @Nonnull
    private String lastname;
    @Nonnull
    private String email;
    @Nonnull
    private String password;
}
