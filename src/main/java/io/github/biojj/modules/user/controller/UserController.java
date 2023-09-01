package io.github.biojj.modules.user.controller;

import io.github.biojj.exception.UserExistingException;
import io.github.biojj.modules.user.model.User;
import io.github.biojj.modules.user.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<String> salvar(@RequestBody @Valid User user) {
        try {
            userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (UserExistingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("{userName}")
    public User findById(@PathVariable String userName) {
        return userService.findById(userName);
    }

}
