package com.sparta.calendar.repository;

import com.sparta.calendar.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameOrNickname(String username, String nickname);

}
