package com.navec.user.activations;

import com.navec.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ActivationRepository extends CrudRepository<Activation, Long> {
    Optional<Activation> findByTokenAndUser(String token, User user);
    List<Activation> findAllByUser(User user);
}
