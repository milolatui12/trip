package tritnt.trip.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import tritnt.trip.dto.ResponseDto;
import tritnt.trip.dto.User.AuthAccessToken;
import tritnt.trip.model.RefreshToken;
import tritnt.trip.model.User;
import tritnt.trip.security.JwtUtil;
import tritnt.trip.services.RefreshTokenService;
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

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseDto<AuthAccessToken> login(@RequestBody String body, HttpServletRequest request) {
        JSONObject jo = new JSONObject(body);

        String username = jo.get("username").toString();
        String password = jo.get("password").toString();

        User user = userService.login(username, password);
        RefreshToken refreshTokenOptional = refreshTokenService.createRefreshToken(user.getUserId());

        String accessToken = jwtUtil.generateAccessToken(user.getUserId());
        String refreshToken = jwtUtil.generateRefreshToken(refreshTokenOptional.getToken());
        return new ResponseDto<>(200, "login success", new AuthAccessToken(accessToken, refreshToken));
    }

    @PostMapping("/register")
    public ResponseDto<AuthAccessToken> register(@RequestBody String body) {
        JSONObject jo = new JSONObject(body);

        String username = jo.getString("username");
        String password = jo.getString("password");
        String name = jo.getString("name");
        String role = jo.getString("role");

        User user = userService.registerUser(name, username, password, role);

        if (user == null) {
            return new ResponseDto<>(400, "register failed", null);
        }

        RefreshToken refreshTokenOptional = refreshTokenService.createRefreshToken(user.getUserId());

        String accessToken = jwtUtil.generateAccessToken(user.getUserId());
        String refreshToken = jwtUtil.generateRefreshToken(refreshTokenOptional.getToken());

        return new ResponseDto<>(200, "register success", new AuthAccessToken(accessToken, refreshToken));
    }

    @PostMapping("/refresh-token")
    public ResponseDto<AuthAccessToken> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        if (!jwtUtil.validateRefreshToken(refreshToken)) {
            return new ResponseDto<>(400, "refresh token invalid", null);
        }

        String token = jwtUtil.extractRefreshToken(refreshToken);

        String accessToken = refreshTokenService.refreshAccessToken(token);

        return new ResponseDto<>(200, "refresh token success", new AuthAccessToken(accessToken));
    }
}
