package com.springjwtmodule.controller;

import com.springjwtmodule.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public CMRespDto<?> mainPage() {

        return new CMRespDto<>(HttpStatus.OK, null, "메인 페이지");
    }
}
