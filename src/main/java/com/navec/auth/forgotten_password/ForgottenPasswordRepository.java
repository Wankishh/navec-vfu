package com.navec.auth.forgotten_password;

import com.navec.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ForgottenPasswordRepository extends CrudRepository<ForgottenPassword, Long> {
    Optional<ForgottenPassword> findByToken(String token);
    void deleteByUser(User user);
}
