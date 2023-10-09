package com.mjc.school.service.impl.securityServices;

import com.mjc.school.repository.jparepos.UserRepository;
import com.mjc.school.repository.model.impl.User;
import com.mjc.school.service.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public void saveUser(User user){
        userRepository.save(user);
    }
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with email %s not found", username)));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getUserAuthorities(user)
        );
    }

    private static List<SimpleGrantedAuthority> getUserAuthorities(User user) {
        return user.getRoles()
                .stream()
                .map(role -> Role.valueOf(role.getName()).getAuthorities())
                .flatMap(Set::stream)
                .collect(Collectors.toList());
    }
}
