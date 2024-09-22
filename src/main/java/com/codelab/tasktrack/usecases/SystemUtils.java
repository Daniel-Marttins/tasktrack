package com.codelab.tasktrack.usecases;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class SystemUtils {

    @Getter
    private static final SystemUtils instance = new SystemUtils();

    private SystemUtils() {}

    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public static final ModelMapper modelMapper = new ModelMapper();

    public String encryptPassword(String profilePassword) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(profilePassword);
    }
}
