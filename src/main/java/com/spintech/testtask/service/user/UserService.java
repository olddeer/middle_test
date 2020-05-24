package com.spintech.testtask.service.user;

import com.spintech.testtask.repository.user.entity.User;

public interface UserService {
    User registerUser(String email, String password);
    User findUser(String email, String password);
}

