package tritnt.trip.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tritnt.trip.model.User;
import tritnt.trip.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String name, String username, String password, String role) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        return userRepository.save(user);
    }

    public boolean checkPassword(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return passwordEncoder.matches(password, user.getPassword());
        }

        return false;
    }

    public User login(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Wrong credentials");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong credentials");
        }

        return user;
    }
}
