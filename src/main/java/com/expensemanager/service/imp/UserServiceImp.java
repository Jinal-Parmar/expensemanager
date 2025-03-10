package com.expensemanager.service.imp;

import com.expensemanager.dto.RegisterDTO;
import com.expensemanager.dto.UserDTO;
import com.expensemanager.entity.User;
import com.expensemanager.exception.UserAlreadyExistsException;
import com.expensemanager.exception.UserNotFoundException;
import com.expensemanager.mapper.UserMapper;
import com.expensemanager.repository.UserRepository;
import com.expensemanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO registerUser(RegisterDTO registerDTO) {
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + registerDTO.getEmail() + " already exists!");
        }
        User user = UserMapper.INSTANCE.toUser(registerDTO);
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toUserDTO(savedUser);
    }

    @Override
    public UserDTO getUserDetails(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found!"));
        return UserMapper.INSTANCE.toUserDTO(user);
    }
}
