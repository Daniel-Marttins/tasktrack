package com.codelab.tasktrack.services;

import com.codelab.tasktrack.entities.User;
import com.codelab.tasktrack.exceptions.UserException;
import com.codelab.tasktrack.repositories.UserRepository;
import com.codelab.tasktrack.usecases.SystemUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    @Transactional
    public User saveUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) throw new UserException
                .UserExistsException("User already exists");

        int quantityUser = userQuantity().size();
        user.setUid(quantityUser > 0 ? quantityUser + 1L : 1L);
        user.setPassword(SystemUtils.getInstance().encryptPassword(user.getPassword()));
        user.setCreateAt(LocalDateTime.now());
        user.setUpdateAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Transactional
    public User getUser(Long id) {
        if (userRepository.findById(id).isEmpty()) throw new UserException
                .UserNotFoundException("User not found");
        return userRepository.findById(id).get();
    }

    @Transactional
    public String getUserByLogin(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) throw new UserException
                .UserUnauthorizedException("User not found with the email provided.");
        if (!SystemUtils.passwordEncoder.matches(password, user.getPassword())) throw new UserException
                .UserUnauthorizedException("Incorrect password.");

        return generateToken(user);
    }

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    updatedUser.setUpdateAt(LocalDateTime.now());
                    SystemUtils.modelMapper.map(updatedUser, existingUser);
                    return userRepository.save(existingUser);
                }).orElseThrow(() -> new UserException
                        .UserNotFoundException("User ID " + id + " not found!")
                );
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException
                .UserNotFoundException("User ID " + id + " not found!")
        );
        userRepository.delete(user);
    }

    @Transactional
    private List<User> userQuantity() {
        return userRepository.findAll();
    }

    private String generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());

        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationTime);

        String base64SecretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, base64SecretKey)
                .compact()
        ;
    }

}
