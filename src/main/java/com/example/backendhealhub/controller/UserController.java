package com.example.backendhealhub.controller;
import com.example.backendhealhub.config.CustomUserDetailsService;
import com.example.backendhealhub.config.JWTGenerator;
import com.example.backendhealhub.dto.AuthResponseDTO;
import com.example.backendhealhub.dto.UserDTO;
import com.example.backendhealhub.entity.User;
import com.example.backendhealhub.repository.UserRepository;
import com.example.backendhealhub.service.ImageStorageService;
import com.example.backendhealhub.service.UserService;
import com.example.backendhealhub.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "User Authentication", description = "APIs for user authentication and management")
public class UserController {

    private final UserService userService;

    @Autowired
    private UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    @Autowired
    private ImageStorageService imageStorageService;

    private JWTGenerator jwtGenerator;

     Authentication authentication;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JWTGenerator jwtGenerator) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

    @PostMapping
    @Operation(summary = "Create a user", description = "Registers a new user with the provided details")
    @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO newUser = userService.createUser(userDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique ID")
    @ApiResponse(responseCode = "200", description = "User retrieved successfully", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves all registered users")
    @ApiResponse(responseCode = "200", description = "All users retrieved successfully", content = @Content(schema = @Schema(implementation = List.class)))
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Updates user information for the specified ID")
    @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a user by their ID")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    @Operation(summary = "Register a user", description = "Registers a new user and stores their information")
    @ApiResponse(responseCode = "201", description = "User registered successfully", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO registeredUser = userService.registerUser(userDTO);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

//    @PostMapping("/login")
//    public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO userDTO) {
//        UserDTO loggedInUser = userService.loginUser(userDTO.getEmail(), userDTO.getPassword());
//        return ResponseEntity.ok(loggedInUser);
//    }


//    @PostMapping("/login")
//    public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO userDTO) {
//        UserDTO loggedInUser = userService.loginUser(userDTO.getEmail(), userDTO.getPassword());
//        this.authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(),userDTO.getPassword())
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String token = jwtGenerator.generateToken(authentication);
//        System.out.println(token);
//        return ResponseEntity.ok(loggedInUser);
//    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user and returns a JWT token")
    @ApiResponse(responseCode = "200", description = "User authenticated successfully", content = @Content(schema = @Schema(implementation = Map.class)))
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getEmail(),
                        userDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String token = jwtGenerator.generateToken(authentication);

        // Retrieve the authenticated user's details
        UserDTO authenticatedUser = userService.getUserByEmail(userDTO.getEmail());

        // Create the response body with the token and user ID
        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", token);
        response.put("userId", authenticatedUser.getId());

        // Return the response entity with the token and user ID in the body
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }


    @GetMapping("/profile")
    @Operation(summary = "Get user profile", description = "Returns the profile of the authenticated user")
    @ApiResponse(responseCode = "200", description = "User profile retrieved successfully", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    public ResponseEntity<?> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName().equals("anonymousUser")) {
            // Return an error response or prompt for authentication
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication is required");
        }
        String email = authentication.getName(); // The email should be the username set by Spring Security
        System.out.println("Email from Authentication: " + email);
        UserDTO userDTO = userService.getUserByEmail(email);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/upload-profile-image")
    @Operation(summary = "Upload user profile image", description = "Uploads a profile image for the authenticated user")
    @ApiResponse(responseCode = "200", description = "Profile image uploaded successfully", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    public ResponseEntity<UserDTO> uploadProfileImage(@RequestParam("image") MultipartFile file, Authentication authentication) {
        // Assuming the username is the email, fetch the user ID from the database
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDTO userDTO = userService.uploadUserImage(user.getId(), file);
        return ResponseEntity.ok(userDTO);
    }





}



