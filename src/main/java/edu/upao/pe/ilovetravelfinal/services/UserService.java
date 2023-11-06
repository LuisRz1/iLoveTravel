package edu.upao.pe.ilovetravelfinal.services;

import edu.upao.pe.ilovetravelfinal.models.User;
import edu.upao.pe.ilovetravelfinal.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.nio.channels.IllegalSelectorException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userid){
        return userRepository.findById(userid);
    }

    public User addUser(User user){
        List<User> existingUserByEmail = userRepository.findByEmail(user.getEmail());
        if(!existingUserByEmail.isEmpty()) {
            throw new IllegalStateException("El correo que ingresaste ya esta en uso");
        }
        System.out.println("El usuario se registro correctamente");
        return userRepository.save(user);
    }

    public void deleteUserById(Long userid){
        userRepository.deleteById(userid);
    }

    public User verifyAccount(String email, String password) {
        List<User> existingUserByCount = userRepository.findByEmail(email);
        System.out.println(email);
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
            throw new IllegalStateException("Correo y contraseña incorrectas");
        }
    }
}