package com.hr.companion.api.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    private UUID id;
    @JsonProperty("userName")
    private String username;
    @JsonProperty("firstName")
    private String firstname;
    @JsonProperty("lastName")
    private String lastname;
    @JsonProperty("emailAddress")
    private String email;
    private boolean enabled;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
    private Set<String> roles;
    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
}
