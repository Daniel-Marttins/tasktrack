package com.codelab.tasktrack.services;

import com.codelab.tasktrack.entities.User;
import com.codelab.tasktrack.exceptions.UserException;
import com.codelab.tasktrack.repositories.UserRepository;
import com.codelab.tasktrack.usecases.SystemUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User saveUser(User user) {
        if(userRepository.findByEmail(user.getEmail()) != null) throw new UserException
                .UserExistsException("User already exists");
        user.setPassword(SystemUtils.getInstance().encryptPassword(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public User getUser(Long id) {
        if(userRepository.findById(id).isEmpty()) throw new UserException
                .UserNotFoundException("User not found");
        return userRepository.findById(id).get();
    }

    @Transactional
    public User getUserByLogin(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) throw new UserException
                .UserUnauthorizedException("User not found with the email provided.");
        if (!SystemUtils.passwordEncoder.matches(password, user.getPassword())) throw new UserException
                .UserUnauthorizedException("Incorrect password.");

        return user;
    }

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
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

}
