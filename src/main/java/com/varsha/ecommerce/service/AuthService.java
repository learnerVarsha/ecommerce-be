package com.varsha.ecommerce.service;

import com.varsha.ecommerce.dto.SignUpDTO;
import com.varsha.ecommerce.dto.UserDTO;

public interface AuthService {

    UserDTO createUser(SignUpDTO signUpDTO);

    Boolean hasUserWithEmail(String email);
}
