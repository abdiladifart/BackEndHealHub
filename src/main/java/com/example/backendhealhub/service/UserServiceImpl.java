package com.example.backendhealhub.service;
import com.example.backendhealhub.config.JWTGenerator;
import com.example.backendhealhub.dto.UserDTO;
import com.example.backendhealhub.entity.Clinic;
import com.example.backendhealhub.entity.Doctor;
import com.example.backendhealhub.entity.Specialty;
import com.example.backendhealhub.entity.User;
import com.example.backendhealhub.repository.ClinicRepository;
import com.example.backendhealhub.repository.DoctorRepository;
import com.example.backendhealhub.repository.SpecialtyRepository;
import com.example.backendhealhub.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClinicRepository clinicRepository;

    private AuthenticationManager authenticationManager;

    //    private RoleRepository roleRepository;
    private JWTGenerator jwtGenerator;

//    @Autowired
//    public UserServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
    public UserServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCity(userDTO.getCity());
        user.setRegion(userDTO.getRegion());
        user = userRepository.save(user);
        return new UserDTO(user.getId(), user.getEmail(),
                user.getUsername(), userDTO.getPassword(),
                user.getCity(),user.getRegion()

        );

    }

//    @Override
//    @Transactional
//    public UserDTO updateUser(Long id, UserDTO userDTO) {
//        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
//        user.setEmail(userDTO.getEmail());
//        user.setUsername(userDTO.getUsername());
//        user = userRepository.save(user);
//        return new  UserDTO(user.getId(), user.getEmail(),
//                user.getUsername(), userDTO.getPassword(),
//                user.getCity(),user.getRegion());
//    }

    @Override
    @Transactional
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setEmail(userDTO.getEmail());
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setCity(userDTO.getCity());
        existingUser.setRegion(userDTO.getRegion());
        existingUser.setType(userDTO.getRole());  // Assuming setType sets the user role

        if ("doctor".equalsIgnoreCase(userDTO.getRole()) && !doctorExistsForUser(existingUser)) {
            Doctor doctor = new Doctor();
            doctor.setName(existingUser.getUsername());
            doctor.setUser(existingUser);
            Specialty specialty = specialtyRepository.findById(userDTO.getSpecialtyId())
                    .orElseThrow(() -> new RuntimeException("Specialty not found"));
            doctor.setSpecialty(specialty);
            doctorRepository.save(doctor);
        }

        userRepository.save(existingUser);
        return modelMapper.map(existingUser, UserDTO.class);
    }

    private boolean doctorExistsForUser(User user) {
        return doctorRepository.existsByUserId(user.getId());
    }


    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user.getId(), user.getEmail(), user.getUsername() ,
                       user.getPassword(), user.getCity(),user.getRegion()
        );
    }

    // Inside UserServiceImpl.java

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user.getId(), user.getEmail(), user.getUsername(),user.getCity(),user.getCity(), user.getRegion()); // Password is not included for security
    }

    @Override
    public UserDTO getUserByName(String name) {
        User user = userRepository.findByEmail(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user.getId(), user.getEmail(), user.getUsername(),user.getCity(),user.getRegion() ,null);

    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(user -> new UserDTO(user.getId(), user.getEmail(), user.getUsername() ,user.getCity(),user.getRegion(), user.getPassword())).collect(Collectors.toList());
    }

//    @Override
//    @Transactional
//    public UserDTO registerUser(UserDTO userDTO) {
//        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
//            throw new RuntimeException("Email already in use");
//        }
//
//        User user = new User();
//        user.setEmail(userDTO.getEmail());
//        user.setUsername(userDTO.getUsername());
//        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//        user.setCity(userDTO.getCity());
//        user.setRegion(userDTO.getRegion());
//        user = userRepository.save(user);
//        return new UserDTO(user.getId(), user.getEmail(), user.getUsername(),
//                userDTO.getPassword(),user.getCity(),user.getRegion()
//
//
//        );
//    }

    @Override
    @Transactional
    public UserDTO registerUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCity(userDTO.getCity());
        user.setRegion(userDTO.getRegion());
        user.setType(userDTO.getRole());

        user = userRepository.save(user);

        if ("doctor".equalsIgnoreCase(userDTO.getRole())) {
            Doctor doctor = new Doctor();
            doctor.setName(user.getUsername());
            Specialty specialty = specialtyRepository.findById(userDTO.getSpecialtyId())
                    .orElseThrow(() -> new RuntimeException("Specialty not found"));
            doctor.setSpecialty(specialty);

            Clinic clinic = clinicRepository.findById(userDTO.getClinicId())
                    .orElseThrow(() -> new RuntimeException("Clinic not found"));
            doctor.setClinic(clinic);

            doctor.setUser(user);
            doctorRepository.save(doctor);
        }

        return new UserDTO(user.getId(), user.getEmail(), user.getUsername(),
                userDTO.getPassword(), user.getCity(), user.getRegion(), userDTO.getRole(), userDTO.getSpecialtyId());
    }


    // Inside UserServiceImpl.java

    public UserDTO loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }



        return new UserDTO(user.getId(), user.getEmail(), user.getUsername(), user.getCity(),user.getRegion(),null); // Avoid returning the password
    }


//    @PostMapping("login")
//    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto loginDto){
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginDto.getUsername(),
//                        loginDto.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String token = jwtGenerator.generateToken(authentication);
//        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
//    }

}
