package edu.upao.pe.ilovetravelfinal.services;

import org.springframework.stereotype.Service;
import edu.upao.pe.ilovetravelfinal.models.User;
import edu.upao.pe.ilovetravelfinal.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User getUserByFirstNameAndLastName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName).orElse(null);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User addUser(User user){
        return userRepository.save(user);
    }
    public User verifyAccount(String email, String password) {

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Verificar si la contraseña coincide
            if (user.getPassword().equals(password)) {
                // Las credenciales son válidas
                return user;
            }
        }
        // Si no se encontró el usuario o las credenciales no coinciden, devuelve null
        return null;
    }
}