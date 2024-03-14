package com.springjwtmodule.service;

import com.springjwtmodule.dto.oauth.JoinDto;

public interface JoinService {
    boolean 회원가입(JoinDto joinDto);
}
