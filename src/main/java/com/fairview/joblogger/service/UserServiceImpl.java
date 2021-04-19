package com.fairview.joblogger.service;

import com.fairview.joblogger.Repo.UserRepository;
import com.fairview.joblogger.env.JtAuthException;
import com.fairview.joblogger.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
@SpringBootConfiguration
@Import(UserRepository.class)
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String email, String password) throws JtAuthException {
        if (email != null) email = email.toLowerCase();
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String firstName,String department, String lastName, String email, String password) throws JtAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if (email != null) email = email.toLowerCase();
        if (!pattern.matcher(email).matches())
            throw new JtAuthException("Invalid email format");
        Integer count = userRepository.getCountByEmail(email);
        if (count > 0)
            throw new JtAuthException("Email already in use");
        System.out.println("POST REQUEST BODY:");
        System.out.println(firstName);
        System.out.println(department);
        System.out.println( lastName);
        System.out.println( email);
        Integer userId = userRepository.create(firstName, department, lastName, email, password);
        return userRepository.findById(userId);
    }
}