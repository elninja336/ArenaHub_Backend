package NyotaHub.ArenaHub.services;

import NyotaHub.ArenaHub.dto.UserDTO;
import NyotaHub.ArenaHub.models.Role;
import NyotaHub.ArenaHub.models.User;
import NyotaHub.ArenaHub.repository.RoleRepository;
import NyotaHub.ArenaHub.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Optional: use if password hashing required
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // Optional: for password hashing - add dependency spring-boot-starter-security if you want to use this
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Create or update user
    public UserDTO saveUser(UserDTO userDTO) {
        User user;

        if(userDTO.getUserID() != null) {
            user = userRepository.findById(userDTO.getUserID()).orElse(new User());
        } else {
            user = new User();
        }

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());

        // Set Role if roleID provided
        if(userDTO.getRoleID() != null) {
            Role role = roleRepository.findById(userDTO.getRoleID())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.setRole(role);
        } else {
            user.setRole(null);
        }

        // Encrypt password if new or changed
        if(userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        user.setIsActive(userDTO.getIsActive() != null ? userDTO.getIsActive() : true);

        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(this::mapToDTO);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Find by username if needed (for auth)
    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username).map(this::mapToDTO);
    }

    private UserDTO mapToDTO(User user) {
        Long roleId = user.getRole() != null ? user.getRole().getRoleID() : null;
        return new UserDTO(
                user.getUserID(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                roleId,
                null, // do not expose password in DTO output
                user.getIsActive()
        );
    }
}
