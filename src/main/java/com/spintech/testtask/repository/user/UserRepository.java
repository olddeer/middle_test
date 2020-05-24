package com.spintech.testtask.repository.user;

import com.spintech.testtask.repository.user.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}