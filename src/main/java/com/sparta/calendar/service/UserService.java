package com.sparta.calendar.service;

import com.sparta.calendar.dto.UserLoginRequestDto;
import com.sparta.calendar.dto.UserRequestDto;
import com.sparta.calendar.entitiy.User;
import com.sparta.calendar.entitiy.UserRoleEnum;
import com.sparta.calendar.jwt.JwtUtil;
import com.sparta.calendar.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final String ADMIN = "admin";
    private final JwtUtil jwtUtil;

    public void sign(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String nickname = userRequestDto.getNickname();
        String password = userRequestDto.getPassword();

        Optional<User> Usernicknamecheck = userRepository.findByNickname( nickname );
        if(Usernicknamecheck.isPresent()) {
            throw new IllegalArgumentException(" "+ nickname+"은 중복된 별명 입니다.");
        }

        Optional<User> Usernamecheck = userRepository.findByUsername( username );
        if(Usernamecheck.isPresent()) {
            throw new IllegalArgumentException(" "+ username+"은 중복된 아이디 입니다.");
        }

        UserRoleEnum roleEnum = UserRoleEnum.USER;
        if(userRequestDto.isAdmin()){
            if(!ADMIN.equals(userRequestDto.getAdmincheck())) {
                throw new IllegalArgumentException("관리자 암호가 아니넹?");
            }
            roleEnum = UserRoleEnum.ADMIN;
        }

        User user = new User(nickname,username,password,roleEnum);
        userRepository.save(user);
    }

    public void login(UserLoginRequestDto userLoginRequestDto, HttpServletResponse response) {
        String username = userLoginRequestDto.getUsername();
        String password = userLoginRequestDto.getPassword();

        User user = userRepository.findByUsername( username ).orElseThrow( () ->
                new IllegalArgumentException(" 회원을 찾을 수 없습니다. "));
                                        // 조건이 때문에 위아래 똑같이 했습니다.
        if(!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
        };

        String token = jwtUtil.createToken( user.getUsername(),user.getRole() );
        jwtUtil.addToken( token,response );
    }
}
