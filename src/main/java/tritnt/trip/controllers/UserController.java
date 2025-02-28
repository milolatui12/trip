package tritnt.trip.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import tritnt.trip.dto.ResponseDto;
import tritnt.trip.dto.User.AuthAccessToken;
import tritnt.trip.model.User;
import tritnt.trip.security.JwtUtil;
import tritnt.trip.services.UserService;

@RestController
@RequestMapping("api/auth")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseDto<AuthAccessToken> login(@RequestBody String body) {
        JSONObject jo = new JSONObject(body);

        String username = jo.get("username").toString();
        String password = jo.get("password").toString();

        User user = userService.login(username, password);
        return new ResponseDto<>(200, "login success", new AuthAccessToken(jwtUtil.generateAccessToken(user.getUsername())));
    }

    @PostMapping("/register")
    public ResponseDto<AuthAccessToken> register(@RequestBody String body) {
        JSONObject jo = new JSONObject(body);

        String username = jo.getString("username");
        String password = jo.getString("password");
        String name = jo.getString("name");
        String role = jo.getString("role");

        User user = userService.registerUser(name, username, password, role);
        return new ResponseDto<>(200, "login success", new AuthAccessToken(jwtUtil.generateAccessToken(user.getUsername())));
    }
}
