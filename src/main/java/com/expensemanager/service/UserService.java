package com.expensemanager.service;

import com.expensemanager.dto.RegisterDTO;
import com.expensemanager.dto.UserDTO;

public interface UserService {

    UserDTO registerUser(RegisterDTO userDTO);

    UserDTO getUserDetails(Long id);
}
