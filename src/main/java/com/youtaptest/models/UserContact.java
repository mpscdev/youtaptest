package com.youtaptest.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserContact {
    private Long id;
    private String email;
    private String phone;
}
