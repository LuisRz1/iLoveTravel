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
    private boolean isEmptyOrWhitespace(String value) {
        return value == null || value.trim().isEmpty();
    }
    public User addUser(User user){
        return userRepository.save(user);
    }
    public User verifyAccount(String email, String password) {
        if (isEmptyOrWhitespace(email) || isEmptyOrWhitespace(password)) {
            throw new IllegalStateException("Correo y contraseña son campos requeridos");
        }
        List<User> existingUserByCount = userRepository.findByEmail(email);
        if (!existingUserByCount.isEmpty()) {
            User useremail = existingUserByCount.get(0);
            // Verificar si la contraseña coincide
            if (useremail.getPassword().equals(password)) {
                // Las credenciales son válidas
                return useremail;
            }else{
                throw new IllegalStateException("contraseña incorrecta");
            }
        }else{
            throw new IllegalStateException("Correo o contraseña es incorrecta");
        }
    }
}