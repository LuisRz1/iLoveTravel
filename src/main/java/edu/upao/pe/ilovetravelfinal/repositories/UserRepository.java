package edu.upao.pe.ilovetravelfinal.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import edu.upao.pe.ilovetravelfinal.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);
    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);
}