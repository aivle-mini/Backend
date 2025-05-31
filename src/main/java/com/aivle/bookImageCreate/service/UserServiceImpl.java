package com.aivle.bookImageCreate.service;

import com.aivle.bookImageCreate.domain.User;
import com.aivle.bookImageCreate.dto.UserDTO;
import com.aivle.bookImageCreate.repository.BookRepository;
import com.aivle.bookImageCreate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO.Response register(UserDTO.Register dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPasswd(dto.getPasswd()); // 실 서비스에서는 BCrypt 해싱 적용 필요
        user.setName(dto.getName());
        return toResponse(userRepository.save(user));
    }

    @Override
    public UserDTO.Response login(UserDTO.Login dto) {
        User user = userRepository.findByEmail(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
        if (!user.getPasswd().equals(dto.getPasswd())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return toResponse(user);
    }

    @Override
    public UserDTO.Response getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));
        return toResponse(user);
    }

    private UserDTO.Response toResponse(User user) {
        return new UserDTO.Response(user.getId(), user.getEmail(), user.getName());
    }
}
