package it.city.appedooserver.controller;

import it.city.appedooserver.entity.User;
import it.city.appedooserver.payload.ApiResponse;
import it.city.appedooserver.payload.JwtToken;
import it.city.appedooserver.payload.ReqLogin;
import it.city.appedooserver.payload.ReqUser;
import it.city.appedooserver.repository.UserRepository;
import it.city.appedooserver.security.JwtTokenProvider;
import it.city.appedooserver.service.AuthService;
import it.city.appedooserver.service.CheckRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    CheckRole checkRole;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/get")
    public HttpEntity<?> getUsers(){
        return ResponseEntity.ok().body(authService.getUserList());
    }

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody ReqUser reqUser){
        ApiResponse apiResponse = authService.register(reqUser);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PutMapping("/update/{id}")
    public HttpEntity<?> updateUser(@PathVariable UUID id, @RequestBody ReqUser reqUser){
        ApiResponse apiResponse = authService.updateUser(id, reqUser);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deleteUser(@PathVariable UUID id){
        ApiResponse apiResponse = authService.deleteUser(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);

    }
    @PostMapping("/login")
    public HttpEntity<?> login(@Valid @RequestBody ReqLogin reqLogIn) {
        try {
            Authentication authentication = authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(reqLogIn.getUsername(), reqLogIn.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
//            if (checkRole.isAgent(user.getRoles()) && !user.isActive()) {
//                return ResponseEntity.status(401).body(new ApiResponse("You have not yet been confirmed by admin", false));
//            }
//            if (reqLogIn.isNewUser()) {
//                return ResponseEntity.ok(checkUserNotEnabled(reqLogIn));
//            }
            String token = jwtTokenProvider.generateToken(user);
            return ResponseEntity.ok(new JwtToken(token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ApiResponse("username or password  error", false));
        }
    }
    @GetMapping("/page")
    public HttpEntity<?> getStudentPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size){
        return  ResponseEntity.ok(authService.getUserPage(page, size));
    }

}
