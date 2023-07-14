package zw.co.zimttech.abn.resource;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zw.co.zimttech.abn.entity.User;
import zw.co.zimttech.abn.requests.LoginRequest;
import zw.co.zimttech.abn.requests.SignupRequest;
import zw.co.zimttech.abn.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class AuthResource {

    private final UserService userService;

    public AuthResource(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Get a list of items", notes = "This endpoint does not require authentication.")
    @PostMapping("/register-user")
    public ResponseEntity<?> createUser(@RequestBody @Validated SignupRequest signupRequest) throws MessagingException, IOException {
        return userService.registerUser(signupRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws  IOException {
        return userService.authenticateUser(loginRequest);
    }



    @GetMapping("/")
    public ResponseEntity<Page<User>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestHeader(value = "token") final String token) {
        return userService.findAll(page, size, token);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteById(@PathVariable("id") String id, @RequestHeader(value = "token") final String token) {
        userService.deleteById(id, token);
        return null;
    }
}
