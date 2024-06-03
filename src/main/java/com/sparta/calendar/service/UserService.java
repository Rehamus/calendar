package com.sparta.calendar.service;

import com.sparta.calendar.dto.UserLoginRequestDto;
import com.sparta.calendar.dto.UserSignRequestDto;
import com.sparta.calendar.entitiy.User;
import com.sparta.calendar.entitiy.UserRoleEnum;
import com.sparta.calendar.jwt.JwtConfig;
import com.sparta.calendar.jwt.JwtUtil;
import com.sparta.calendar.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtConfig jwtConfig;
    private final JwtUtil jwtUtil;

    //회원가입
    public String sign(UserSignRequestDto usersignRequestDto, HttpServletResponse response) {
        String username = usersignRequestDto.getUsername();
        String nickname = usersignRequestDto.getNickname();
        String password = usersignRequestDto.getPassword();

        Optional<User> findUsernameornickname = userRepository.findByUsernameOrNickname(username,nickname);
        if (findUsernameornickname.isPresent()) {
            User userfind = findUsernameornickname.get();
            if (userfind.getUsername().equals(username)) {
                throw new IllegalArgumentException(" " + username + "은 중복된 아이디 입니다.");
            }else if (userfind.getNickname().equals(nickname)) {
                throw new IllegalArgumentException(" " + nickname + "은 중복된 별명 입니다.");
            }
        }

        UserRoleEnum roleEnum = UserRoleEnum.USER;
        if (usersignRequestDto.isAdmin()) {
            if (!jwtConfig.getADMIN().equals(usersignRequestDto.getAdmincheck())) {
                throw new IllegalArgumentException("관리자 암호가 아니넹?");
            }
            roleEnum = UserRoleEnum.ADMIN;
        }

        User user = new User(nickname, username, password, roleEnum);

        String Access_token = jwtUtil.createAccessToken(user.getUsername(), user.getRole());
        String Refresh_token = jwtUtil.createRefreshToken(user.getUsername(), user.getRole());
        jwtUtil.addToken(Access_token, response);
        user.setRefreshToken(Refresh_token);
        userRepository.save(user);

        return "회원 가입 완료";
    }

    // 로그인
    public String login(UserLoginRequestDto userLoginRequestDto, HttpServletResponse response) {
        String username = userLoginRequestDto.getUsername();
        String password = userLoginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(" 회원을 찾을 수 없습니다. "));
        // 조건이 때문에 위아래 똑같이 했습니다.
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
        }
        ;

        String Access_token = jwtUtil.createAccessToken(user.getUsername(), user.getRole());
        jwtUtil.addToken(Access_token, response);

        String Refresh_token = jwtUtil.createRefreshToken(user.getUsername(), user.getRole());
        user.setRefreshToken(Refresh_token);
        userRepository.save(user);

        return "로그인 완료";
    }


    //회원 탈퇴
    @Transactional
    public String delete(UserLoginRequestDto userLoginRequestDto, HttpServletRequest request) {
        User user = jwtUtil.gettokenUser(request);
        if (!user.getPassword().equals(userLoginRequestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀립니다.");
        }
        userRepository.delete(user);
        return "삭제 완료";
    }

}
