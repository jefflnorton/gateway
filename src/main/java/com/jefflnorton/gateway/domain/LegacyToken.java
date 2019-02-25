package com.jefflnorton.gateway.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class LegacyToken {
    private @NonNull String token;
}
