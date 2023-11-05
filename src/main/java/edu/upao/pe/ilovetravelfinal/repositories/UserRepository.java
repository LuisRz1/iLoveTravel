package edu.upao.pe.ilovetravelfinal.repositories;

import edu.upao.pe.ilovetravelfinal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}