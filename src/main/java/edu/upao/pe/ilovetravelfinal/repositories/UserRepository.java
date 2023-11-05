package edu.upao.pe.ilovetravelfinal.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import edu.upao.pe.ilovetravelfinal.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);
}