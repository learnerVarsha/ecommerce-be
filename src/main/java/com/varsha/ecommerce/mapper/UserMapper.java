package com.varsha.ecommerce.mapper;

import com.varsha.ecommerce.dto.SignUpDTO;
import com.varsha.ecommerce.dto.UserDTO;
import com.varsha.ecommerce.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    //SignUpDTO to User
    @Mapping(target="role", constant="CUSTOMER")
    User toUserEntity(SignUpDTO dto);

    //User to UserDTO
    UserDTO toUserDTO(User user);

}
