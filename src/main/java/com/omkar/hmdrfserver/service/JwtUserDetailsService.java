package com.omkar.hmdrfserver.service;

import com.omkar.hmdrfserver.exchanges.SignUpUser;
import com.omkar.hmdrfserver.model.User;
import com.omkar.hmdrfserver.repository.UserRepository;
import com.omkar.hmdrfserver.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Provides fetching and saving to and fro database.
 */
@Slf4j
@Service
public class JwtUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    /**
     * Validates username, if present return user details.
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new UserPrincipal(user);
    }

    /**
     * Saves user to DB
     * @param user
     * @return
     */
    public User save(SignUpUser user) {
        User newUser = new User(
                user.getUsername(),
                user.getEmail(),
                bcryptEncoder.encode(user.getPassword()),
                user.getRoles()
        );
        return userRepository.save(newUser);
    }
}
