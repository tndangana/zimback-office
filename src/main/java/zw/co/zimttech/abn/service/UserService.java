package zw.co.zimttech.abn.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.lang.NonNull;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zw.co.zimttech.abn.entity.User;
import zw.co.zimttech.abn.repository.UserRepository;
import zw.co.zimttech.abn.repository.generic.GenericRepository;
import zw.co.zimttech.abn.requests.LoginRequest;
import zw.co.zimttech.abn.requests.SignupRequest;
import zw.co.zimttech.abn.response.*;
import zw.co.zimttech.abn.security.JwtTokenProvider;
import zw.co.zimttech.abn.service.generics.GenericService;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.Optional;

@Service
public class UserService extends GenericService<User,String> {
    private JwtTokenProvider tokenProvider;
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ObjectMapper objectMapper;

    @Value("${app.jwtSecret}")
    private String jwtSecret;



    public UserService(GenericRepository<User> repository, UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager,
                       ObjectMapper objectMapper) {
        super(repository);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;

    }

    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) throws IOException, MessagingException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Email Address already in use!"));
        }

        User user = new User();
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        user.setPassword(encodedPassword);
        user.setEmail(signUpRequest.getEmail());
        user.setFirstName(signUpRequest.getFirst_name());
        user.setLastName(signUpRequest.getLast_name());
        user.setUsername(signUpRequest.getUsername());
        user.setCreatedDate(Instant.now());
        user.setUpdatedDate(Instant.now());
        User createdUser = userRepository.save(user);

        return ResponseEntity.ok(createdUser);
    }

    public ResponseEntity<?> authenticateUser(@NonNull LoginRequest loginRequest) throws IOException {
        Optional<User> user = userRepository.findUserByUsername(loginRequest.getUsername());
        if (!user.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Invalid username or password."));
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Invalid username or password."));
        }

        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        authenticatedUser.setId(user.get().getId());
        authenticatedUser.setUsername(user.get().getUsername());

        String subject = objectMapper.writeValueAsString(authenticatedUser);
        TokenResponse tokenResponse = tokenProvider.generateToken(subject, user.get());

        if (tokenResponse == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Failed to generate token."));
        }

        return ResponseEntity.ok(tokenResponse);
    }

    public Boolean validate(final String token)  {
        if (StringUtils.isBlank(token)) {
            return false;
        }

        if (StringUtils.isBlank(jwtSecret)) {
            return false;
        }

        Response jwtResponse = tokenProvider.parseJWT(token);
        if (!jwtResponse.isSuccess()) {
            return false;
        }

        return true;
    }

}

