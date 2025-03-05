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

public class UserController {

}
