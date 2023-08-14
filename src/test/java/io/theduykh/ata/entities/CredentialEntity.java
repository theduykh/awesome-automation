package io.theduykh.ata.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CredentialEntity {
    private String username;
    private String password;
}
