package tritnt.trip.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tritnt.trip.model.CustomUserDetails;
import tritnt.trip.model.User;
import tritnt.trip.repositories.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserId(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        return new CustomUserDetails(user.get());
    }
}
