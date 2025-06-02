package com.aivle.bookImageCreate.service;

import com.aivle.bookImageCreate.domain.User;
import com.aivle.bookImageCreate.dto.UserDTO;


public interface UserService {
    UserDTO.Response register(UserDTO.Register dto);
    UserDTO.Response login(UserDTO.Login dto);
    UserDTO.Response getUser(Long id);
}
