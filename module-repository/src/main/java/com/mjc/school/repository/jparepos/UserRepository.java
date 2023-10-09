package com.mjc.school.repository.jparepos;

import com.mjc.school.repository.model.impl.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
