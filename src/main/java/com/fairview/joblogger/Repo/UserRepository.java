package com.fairview.joblogger.Repo;

import com.fairview.joblogger.env.JtAuthException;
import com.fairview.joblogger.models.User;
import com.fairview.joblogger.service.UserRowMapper;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;


public class UserRepository {
    private static final String SQL_CREATE = "INSERT INTO jt_users( first_name, department, last_name, email, password) VALUES(?, ?, ?, ?, ?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM jt_users WHERE EMAIL = ?";
    private static final String SQL_FIND_BY_ID = "SELECT user_id, first_name,department,last_name, email, password " +
            "FROM jt_users WHERE USER_ID = ?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT user_id, first_name,department, last_name, email, password " +
            "FROM jt_users WHERE EMAIL = ?";
    private static final String SQL_USER_ID_QUERY = "SELECT COUNT (*)\n" +"FROM `Jobs`.`jt_users`";
    @Autowired
    JdbcTemplate jdbcTemplate;


    public Integer create(String firstName,String department, String lastName, String email, String password) throws JtAuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, firstName);
                ps.setString(2, department);
                ps.setString(3, lastName);
                ps.setString(4, email);
                ps.setString(5, hashedPassword);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("user_id");
        }catch (Exception e) {
            throw new JtAuthException("Invalid details. Failed to create account");
        }
    }

    public User findByEmailAndPassword(String email, String password) throws JtAuthException {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, new UserRowMapper());
            if(!BCrypt.checkpw(password, user.getPassword()))
                throw new JtAuthException("Invalid email/password");
            return user;
        }catch (EmptyResultDataAccessException e) {
            throw new JtAuthException("Invalid email/password");
        }
    }

    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId}, new UserRowMapper());
    }
}
