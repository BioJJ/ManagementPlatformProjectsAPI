package io.github.biojj.modules.user.services;

import io.github.biojj.exception.UserExistingException;
import io.github.biojj.modules.project.model.Project;
import io.github.biojj.modules.user.model.User;
import io.github.biojj.modules.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User save(User usuario) {
        boolean exists = repository.existsByUsername(usuario.getUsername());
        if (exists) {
            throw new UserExistingException(usuario.getUsername());
        }
        return repository.save(usuario);
    }

    public User findById(String userName) {
        return repository
                .findByUsername(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user não encontrado"));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        io.github.biojj.modules.user.model.User user = repository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Login não encontrado."));

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

}
