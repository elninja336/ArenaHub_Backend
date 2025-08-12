package NyotaHub.ArenaHub.services;

import NyotaHub.ArenaHub.dto.RoleDTO;
import NyotaHub.ArenaHub.models.Role;
import NyotaHub.ArenaHub.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // Create or update role based on DTO
    public RoleDTO saveRole(RoleDTO roleDTO) {
        Optional<Role> existing = roleRepository.findByName(roleDTO.getName());

        Role role;
        if (roleDTO.getRoleID() != null) {
            role = roleRepository.findById(roleDTO.getRoleID())
                    .orElse(new Role());
        } else if (existing.isPresent()) {
            // If same description exists and no ID specified, update that role
            role = existing.get();
        } else {
            role = new Role();
        }

        role.setName(roleDTO.getName());
        role.setIsActive(roleDTO.getIsActive() != null ? roleDTO.getIsActive() : true);

        Role saved = roleRepository.save(role);
        return mapToDTO(saved);
    }

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<RoleDTO> getRoleById(Long id) {
        return roleRepository.findById(id).map(this::mapToDTO);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    // Mapping entity to DTO
    private RoleDTO mapToDTO(Role role) {
        return new RoleDTO(
                role.getRoleID(),
                role.getName(),
                role.getDescription(),
                role.getIsActive()
        );
    }
}
