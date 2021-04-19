package com.fairview.joblogger.service;

import com.fairview.joblogger.env.JtAuthException;
import com.fairview.joblogger.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User validateUser(String email, String password) throws JtAuthException;

    User registerUser(String firstName, String lastName, String email, String password, String department) throws JtAuthException;

}
