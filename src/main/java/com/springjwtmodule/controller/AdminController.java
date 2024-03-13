package com.springjwtmodule.controller;

import com.springjwtmodule.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/admin")
    public CMRespDto<?> adminPage() {

        return new CMRespDto<>(HttpStatus.OK, null, "어드민 페이지");
    }
}
