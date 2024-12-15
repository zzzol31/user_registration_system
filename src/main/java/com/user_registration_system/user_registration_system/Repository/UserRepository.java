package com.user_registration_system.user_registration_system.Repository;

import com.user_registration_system.user_registration_system.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Long> {
    User findUserByEmail(String email);
}
