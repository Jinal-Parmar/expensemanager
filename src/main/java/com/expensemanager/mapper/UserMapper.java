package com.expensemanager.mapper;

import com.expensemanager.dto.RegisterDTO;
import com.expensemanager.dto.UserDTO;
import com.expensemanager.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User toUser(RegisterDTO registerDTO);
    UserDTO toUserDTO(User user);
}
