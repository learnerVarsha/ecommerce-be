package com.varsha.ecommerce.dto;

import com.varsha.ecommerce.enums.UserRole;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String email;
    private String name;
    private UserRole role;
}
