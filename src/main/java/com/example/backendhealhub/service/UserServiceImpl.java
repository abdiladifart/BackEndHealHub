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
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private ImageStorageService imageStorageService;

    private AuthenticationManager authenticationManager;
    private JWTGenerator jwtGenerator;

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
        user.setType(userDTO.getRole());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user = userRepository.save(user);

        if ("doctor".equals(user.getType())) {
            Doctor doctor = new Doctor();
            doctor.setUser(user);
            doctor.setName(user.getUsername());

            if (userDTO.getClinicId() != null && userDTO.getSpecialtyId() != null) {
                Clinic clinic = clinicRepository.findById(userDTO.getClinicId())
                        .orElseThrow(() -> new IllegalArgumentException("Clinic not found with ID: " + userDTO.getClinicId()));
                Specialty specialty = specialtyRepository.findById(userDTO.getSpecialtyId())
                        .orElseThrow(() -> new IllegalArgumentException("Specialty not found with ID: " + userDTO.getSpecialtyId()));
                doctor.setClinic(clinic);
                doctor.setSpecialty(specialty);
            }
            doctorRepository.save(doctor);
        }

        return new UserDTO(user.getId(), user.getEmail(), user.getUsername(), null, user.getCity(), user.getRegion(), user.getType());
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String previousRole = existingUser.getType();
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setCity(userDTO.getCity());
        existingUser.setRegion(userDTO.getRegion());
        existingUser.setType(userDTO.getRole());
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());

        if ("doctor".equalsIgnoreCase(userDTO.getRole())) {
            if (!doctorExistsForUser(existingUser)) {
                Doctor doctor = new Doctor();
                doctor.setName(existingUser.getUsername());
                doctor.setUser(existingUser);
                Specialty specialty = specialtyRepository.findById(userDTO.getSpecialtyId())
                        .orElseThrow(() -> new RuntimeException("Specialty not found"));
                doctor.setSpecialty(specialty);
                Clinic clinic = clinicRepository.findById(userDTO.getClinicId())
                        .orElseThrow(() -> new RuntimeException("Clinic not found"));
                doctor.setClinic(clinic);
                doctorRepository.save(doctor);
            } else {
                // Update existing doctor record
                Doctor doctor = doctorRepository.findByUserId(existingUser.getId());
                doctor.setName(existingUser.getUsername());
                doctor.setSpecialty(specialtyRepository.findById(userDTO.getSpecialtyId())
                        .orElseThrow(() -> new RuntimeException("Specialty not found")));
                doctor.setClinic(clinicRepository.findById(userDTO.getClinicId())
                        .orElseThrow(() -> new RuntimeException("Clinic not found")));
                doctorRepository.save(doctor);
            }
        } else if ("doctor".equalsIgnoreCase(previousRole) && !"doctor".equalsIgnoreCase(userDTO.getRole())) {
            // Role changed from doctor to another role, delete doctor record
            Doctor doctor = doctorRepository.findByUserId(existingUser.getId());
            if (doctor != null) {
                doctorRepository.delete(doctor);
            }
        }

        userRepository.save(existingUser);
        return modelMapper.map(existingUser, UserDTO.class);
    }

    private boolean doctorExistsForUser(User user) {
        return doctorRepository.existsByUserId(user.getId());
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if ("doctor".equals(user.getType())) {
            Doctor doctor = doctorRepository.findByUserId(user.getId());
            if (doctor != null) {
                doctorRepository.delete(doctor);
            }
        }

        userRepository.delete(user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user.getId(), user.getEmail(), user.getUsername(), user.getPassword(), user.getCity(), user.getRegion(), user.getType(), user.getPhoneNumber(), user.getAge());
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user.getId(), user.getEmail(), user.getUsername(), user.getCity(), user.getRegion(), user.getType(), user.getImageUrl(), user.getPhoneNumber(), user.getAge());
    }

    @Override
    public UserDTO getUserByName(String name) {
        User user = userRepository.findByEmail(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user.getId(), user.getEmail(), user.getUsername(), user.getCity(), user.getRegion(), user.getType(), user.getPhoneNumber());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getEmail(), user.getUsername(), user.getCity(), user.getRegion(), user.getType(),
                        user.getImageUrl(), user.getPhoneNumber(), user.getAge()))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public UserDTO registerUser(UserDTO userDTO) {
        if (userDTO.getEmail() == null || userDTO.getUsername() == null || userDTO.getPassword() == null) {
            throw new IllegalArgumentException("Email, username, and password cannot be null");
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCity(userDTO.getCity());
        user.setRegion(userDTO.getRegion());
        user.setType(userDTO.getRole());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAge(userDTO.getAge());
        userRepository.save(user);

        if ("doctor".equals(user.getType())) {
            Doctor doctor = new Doctor();
            doctor.setUser(user);
            doctor.setName(user.getUsername());

            if (userDTO.getClinicId() == null || userDTO.getSpecialtyId() == null) {
                throw new IllegalArgumentException("Clinic and Specialty IDs must not be null for doctor creation");
            }

            Clinic clinic = clinicRepository.findById(userDTO.getClinicId())
                    .orElseThrow(() -> new IllegalArgumentException("Clinic not found with ID: " + userDTO.getClinicId()));
            Specialty specialty = specialtyRepository.findById(userDTO.getSpecialtyId())
                    .orElseThrow(() -> new IllegalArgumentException("Specialty not found with ID: " + userDTO.getSpecialtyId()));

            doctor.setClinic(clinic);
            doctor.setSpecialty(specialty);
            doctorRepository.save(doctor);
        }

        return new UserDTO(user.getId(), user.getEmail(), user.getUsername(), null, user.getCity(), user.getRegion(), user.getType());
    }

    public UserDTO loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return new UserDTO(user.getId(), user.getEmail(), user.getUsername(), user.getCity(), user.getRegion(), null); // Avoid returning the password
    }

    @Override
    public UserDTO uploadUserImage(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        String imageUrl = imageStorageService.upload(file); // Store the image and get back the URL
        user.setImageUrl(imageUrl);
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class); // Assuming you have a model mapper
    }
}


