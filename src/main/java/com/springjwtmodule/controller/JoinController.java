package com.springjwtmodule.controller;

import com.springjwtmodule.dto.CMRespDto;
import com.springjwtmodule.dto.oauth.JoinDto;
import com.springjwtmodule.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public CMRespDto<?> joinProcess(@RequestBody JoinDto joinDto) {

        boolean state = joinService.회원가입(joinDto);

        if (!state) {
            return new CMRespDto<>(HttpStatus.OK, joinDto, "회원 가입 실패");
        }
        return new CMRespDto<>(HttpStatus.OK, joinDto, "회원 가입 완료");

    }
}
