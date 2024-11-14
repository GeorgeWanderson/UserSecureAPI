package controller;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.UserService;
import util.JWTUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userService.registerUser(user)) {
            return ResponseEntity.ok("User registered successfully.");
        }
        return ResponseEntity.status(400).body("User registration failed.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) {
        String token = userService.authenticateUser(email, password);
        if (token != null) {
            return ResponseEntity.ok("Bearer " + token);
        }
        return ResponseEntity.status(401).body("Invalid credentials.");
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            String username = jwtUtil.extractUsername(token.substring(7));
            User user = userService.getUserByUsername(username);
            if (user != null) {
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.status(401).body(null);
    }
}
