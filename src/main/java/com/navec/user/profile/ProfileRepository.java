package com.navec.user.profile;

import com.navec.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
    Optional<Profile> findByUser(User user);
}
