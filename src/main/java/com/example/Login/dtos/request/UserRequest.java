package com.example.Login.dtos.request;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.Size;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    @Size(min = 3, message = "INVALID_NAME")
    String username;
    @Size(min = 8, message = "INVALID_PASSWORD" )
    String password;
    @Pattern(regexp = ".+@gmail\\.com", message = "EMAIL_INVALID")
    String email;
    @Pattern(regexp = "0[0-9]{9,}", message = "INVALID_PHONE")
    String phone;
    String roles;
}