package com.example.aurigraph.farmers.Controller;


import com.example.aurigraph.farmers.ExceptionHandler.AuthenticationFailedException;
import com.example.aurigraph.farmers.ExceptionHandler.UserAlreadyExistsException;
import com.example.aurigraph.farmers.Response.ApiResponse;
import com.example.aurigraph.farmers.Response.LoginResponse;
import com.example.aurigraph.farmers.DTO.LoginUserDTO;
import com.example.aurigraph.farmers.DTO.RegisterUserDTO;
import com.example.aurigraph.farmers.Domain.User;
import com.example.aurigraph.farmers.Service.AuthenticationService;
import com.example.aurigraph.farmers.Service.JwtService;
import com.example.aurigraph.farmers.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    private final OtpService otpService;

    @Autowired
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, OtpService otpService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.otpService = otpService;
    }
    @RestController
    @RequestMapping("/auth")
    public class AuthController {

        private final OtpService otpService;
        private final AuthenticationService authenticationService;
        private final JwtService jwtService;

        public AuthController(OtpService otpService, AuthenticationService authenticationService, JwtService jwtService) {
            this.otpService = otpService;
            this.authenticationService = authenticationService;
            this.jwtService = jwtService;
        }

        @PostMapping("/verify-and-signup")
        public ResponseEntity<?> register(@RequestBody RegisterUserDTO registerUserDto) {
            try {
                // Check if OTP is valid
                if (!otpService.validateOtp(registerUserDto.getPhoneNumber(), registerUserDto.getOtp())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ApiResponse(false, "Invalid or expired OTP"));
                }

                // Register the user
                User registeredUser = authenticationService.signup(registerUserDto);
                return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);

            } catch (UserAlreadyExistsException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ApiResponse(false, "User already exists with this phone number"));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse(false, "Something went wrong: " + e.getMessage()));
            }
        }

        @PostMapping("/login")
        public ResponseEntity<?> authenticate(@RequestBody LoginUserDTO loginUserDto) {
            try {
                // Authenticate the user
                User authenticatedUser = authenticationService.authenticate(loginUserDto);

                if (authenticatedUser == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(new ApiResponse(false, "Invalid credentials"));
                }

                // Generate JWT Token
                String jwtToken = jwtService.generateToken(authenticatedUser);
                LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());

                return ResponseEntity.ok(loginResponse);

            } catch (AuthenticationFailedException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(false, "Authentication failed: " + e.getMessage()));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse(false, "Something went wrong: " + e.getMessage()));
            }
        }
    }

}