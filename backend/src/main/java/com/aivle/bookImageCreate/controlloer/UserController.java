package com.aivle.bookImageCreate.controlloer;

import com.aivle.bookImageCreate.domain.User;
import com.aivle.bookImageCreate.dto.UserDTO;
import com.aivle.bookImageCreate.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping
    public ResponseEntity<?> register(@RequestBody UserDTO.Register dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    // 로그인 (세션에 사용자 정보 저장)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO.Login dto, HttpSession session) {
        UserDTO.Response user = userService.login(dto);
        session.setAttribute("user", user);
        return ResponseEntity.ok(user);
    }

    // 로그아웃 (세션 종료)
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("status", "success", "message", "로그아웃이 완료되었습니다."));
    }

    // 사용자 정보 조회 (세션 유효성 검증 포함)
    @GetMapping("/{id}")
    public ResponseEntity<?> getProfile(@PathVariable Long id, HttpSession session) {
        UserDTO.Response sessionUser = (UserDTO.Response) session.getAttribute("user");
        if (sessionUser == null || !sessionUser.getId().equals(id)) {
            return ResponseEntity.status(401).body(Map.of("status", "error", "message", "유효하지 않은 세션입니다."));
        }
        return ResponseEntity.ok(userService.getUser(id));
    }
}