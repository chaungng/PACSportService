package com.pacsport.service.daos.mysql;

import com.pacsport.service.daos.IUserDAO;
import com.pacsport.service.daos.data.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class MySQLUserDAO implements IUserDAO {
    private JdbcTemplate jdbcTemp;

    public MySQLUserDAO(DataSource dataSource) {
        jdbcTemp = new JdbcTemplate(dataSource);
    }

    @Override
    public void set(User user) {
        String query =
                "INSERT INTO users (" +

                        " id, " +

                        " email, " +

                        " password, " +

                        " first_name, " +

                        " last_name, " +

                        " birthday) " +

                        "VALUES (?, ?, ?, ?, ?, ?)";

        // define query arguments
        Object[] params = new Object[]{
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthday()};

        // define SQL types of the arguments
        int[] types = new int[]{
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR};

        jdbcTemp.update(query, params, types);
    }

    @Override
    public User get(String id) {
        String query = "SELECT * FROM users WHERE id = ?";
        try {
            return jdbcTemp.queryForObject(query, new Object[]{id}, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User getByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try {
            return jdbcTemp.queryForObject(query, new Object[]{email}, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {

            return User.builder()
                    .id(rs.getString("id"))
                    .email(rs.getString("email"))
                    .password(rs.getString("password"))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .birthday(rs.getString("birthday"))
                    .build();

        }
    }
}
