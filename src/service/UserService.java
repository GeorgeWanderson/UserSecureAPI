package service;

import dao.UserDAO;
import model.User;
import util.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private JWTUtil jwtUtil;

    public boolean registerUser(User user) {
        return userDAO.registerUser(user);
    }

    public String authenticateUser(String email, String password) {
        User user = userDAO.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return jwtUtil.generateToken(user.getEmail());
        }
        return null;
    }

    public User getUserByUsername(String username) {
        User user = userDAO.getUserByEmail(username);
        if (user != null) {
            return user;
        }
        return null;
    }
}
