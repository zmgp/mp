package com.shu.ming.mp.modules.login.service;

import com.shu.ming.mp.modules.login.bean.UserInfo;
import com.shu.ming.mp.modules.login.dto.LoginDTO;
import com.shu.ming.mp.modules.login.dto.RegisterDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RegisterService {

    UserInfo findUserByName(String username);

    void insertOneUser(RegisterDTO registerDTO);

    boolean existEmailAddress(String email);
}
