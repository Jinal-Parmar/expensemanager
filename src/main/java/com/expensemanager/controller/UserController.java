package com.expensemanager.controller;

import com.expensemanager.dto.RegisterDTO;
import com.expensemanager.dto.UserDTO;
import com.expensemanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody RegisterDTO registerDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.registerUser(registerDTO));
    }

    @GetMapping("/{id}")
    public UserDTO getDetails(@PathVariable Long id) {
        return userService.getUserDetails(id);
    }
}
