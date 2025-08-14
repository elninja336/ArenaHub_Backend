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

    // Create new Role
    public RoleDTO createRole(RoleDTO roleDTO) {
        // (Optional) Check if a Role with the same name exists to avoid duplicates
        roleRepository.findByName(roleDTO.getName()).ifPresent(r -> {
            throw new IllegalArgumentException("Role with the same name already exists");
        });

        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());
        role.setIsActive(roleDTO.getIsActive() != null ? roleDTO.getIsActive() : true);

        Role savedRole = roleRepository.save(role);
        return mapToDTO(savedRole);
    }

    // Update existing Role
    public RoleDTO updateRole(Long roleID, RoleDTO roleDTO) {
        Role existingRole = roleRepository.findById(roleID)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + roleID));

        // You may want to check if updating the name causes a duplicate
        if (!existingRole.getName().equals(roleDTO.getName())) {
            roleRepository.findByName(roleDTO.getName()).ifPresent(r -> {
                throw new IllegalArgumentException("Role with the same name already exists");
            });
        }

        existingRole.setName(roleDTO.getName());
        existingRole.setDescription(roleDTO.getDescription());
        existingRole.setIsActive(roleDTO.getIsActive() != null ? roleDTO.getIsActive() : existingRole.getIsActive());

        Role updated = roleRepository.save(existingRole);
        return mapToDTO(updated);
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

    private RoleDTO mapToDTO(Role role) {
        return new RoleDTO(
                role.getRoleID(),
                role.getName(),
                role.getDescription(),
                role.getIsActive()
        );
    }
}
