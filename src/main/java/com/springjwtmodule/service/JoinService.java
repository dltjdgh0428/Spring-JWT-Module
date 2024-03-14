package com.springjwtmodule.service;

import com.springjwtmodule.dto.oauth.JoinDto;
import com.springjwtmodule.entity.user.User;
import com.springjwtmodule.entity.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean 회원가입(JoinDto joinDto) {
        Boolean isExist = userRepository.existsByUsername(joinDto.getUsername());

        if (isExist) {
            return Boolean.FALSE;
        }

        User newUser = joinDto.toEntity(bCryptPasswordEncoder.encode(joinDto.getPassword()), "ROLE_ADMIN");
        userRepository.save(newUser);

        return Boolean.TRUE;
    }


}
